package com.rs.server;

import com.rs.algorithm.BasicAlgorithm;
import com.rs.data.entity.*;
import com.rs.data.repository.Repository;

import java.util.*;

/**
 * Created by leo on 15/3/14.
 */
public class ReputationCalculation {
    private float test;

    private List<User> allUsers;
    private List<Item> allItems;
    private List<Topic> allTopics;

    Float lamda;
    public int iteration = 3;
    int nowiteration = 0;

    private BasicAlgorithm algorithm;


    public ReputationCalculation() {
        allUsers = Repository.userRepository.findAll();
        allItems = Repository.itemRepository.findAll();
        allTopics = Repository.topicRepository.findAll();
    }

    //运行算法
    public void run(BasicAlgorithm algorithm) {
        this.algorithm = algorithm;
        initUserReputation();
        start();
    }

    //运行非主题分析的算法
    public void runNotTB(BasicAlgorithm algorithm) {
        this.algorithm = algorithm;
        initUserReputation();
        nowiteration = 0;
        for (int i = 0; i < iteration; i++) {
            estimatedScoreCalculationNotTB();
            userReputationCalculation();
            testMAE(i);
            nowiteration++;
        }
    }

    //运行近邻优化的算法
    public void runKNN(BasicAlgorithm algorithm) {
        this.algorithm = algorithm;
        initUserReputation();
        nowiteration = 0;
        for (int i = 0; i < iteration; i++) {
            long startTime = System.nanoTime();
            estimatedScoreCalculation();
            if (i != iteration - 1) {
                userReputationCalculation();
                coldBootReputationKNN2();
            }
            testMAE(i);
            nowiteration++;
        }
    }

    //运行近邻优化的算法
    public void runKNN_3near(BasicAlgorithm algorithm) {
        this.algorithm = algorithm;
        initUserReputation();
        nowiteration = 0;
        for (int i = 0; i < iteration; i++) {
            estimatedScoreCalculation();
            if (i != iteration - 1) {
                userReputationCalculation();
                coldBootReputationKNN3();
            }
            testMAE(i);
            nowiteration++;
        }
    }

    public void runKNN_5near(BasicAlgorithm algorithm) {
        this.algorithm = algorithm;
        initUserReputation();
        nowiteration = 0;
        for (int i = 0; i < iteration; i++) {
            estimatedScoreCalculation();
            if (i != iteration - 1) {
                userReputationCalculation();
                coldBootReputationKNN5();
            }
            testMAE(i);
            nowiteration++;
        }
    }

    public void runKNN_5near2(BasicAlgorithm algorithm) {
        this.algorithm = algorithm;
        initUserReputation();
        nowiteration = 0;
        for (int i = 0; i < iteration; i++) {
            long startTime = System.nanoTime();
            estimatedScoreCalculation();
            if (i != iteration - 1) {
                userReputationCalculation();
                coldBootReputationKNN5_2();
            }
            testMAE(i);
            nowiteration++;
        }
    }

    //主入口
    public void start() {
        //控制迭代次数
        nowiteration = 0;
        for (int i = 0; i < iteration; i++) {
            estimatedScoreCalculation();
            userReputationCalculation();
            testMAE(i);
            nowiteration++;
        }
    }

    //初始化所有用户的可信度
    public void initUserReputation() {
        List<UserTopic> userTopics = Repository.userTopicRepository.findAll();
        if (userTopics.size() == 0) {
            List<Topic> topics = Repository.topicRepository.findAll();
            List<User> users = Repository.userRepository.findAll();
            for (Topic topic : topics) {
                for (User user : users) {
                    UserTopic userTopic = new UserTopic();
                    userTopic.setReputation(1f);
                    userTopic.setTopic(topic);
                    userTopic.setUser(user);
                    Repository.userTopicRepository.save(userTopic);
                }
            }
        } else {
            for (UserTopic userTopic : userTopics) {
                userTopic.setReputation(1f);
                Repository.userTopicRepository.save(userTopic);
            }
        }
    }

    //计算商品综合得分
    public void estimatedScoreCalculation() {
        float estimatedScore = 0f;
        for (Item item : allItems) {
            float scoreSum = 0f;
            List<Rating> ratings = Repository.ratingRepository.findByItem(item);
            for (Rating rating : ratings) {
                float userOverallReputation = 0f;
                List<ItemTopic> itemTopics = Repository.itemTopicRepository
                        .findByItem(item);
                for (ItemTopic itemTopic : itemTopics) {
                    List<UserTopic> userTopics = Repository.userTopicRepository
                            .findByUserAndTopic(rating.getUser(), itemTopic.getTopic());
                    float userReputation = userTopics.size() == 0 ?
                            1f : userTopics.get(0).getReputation();
                    userOverallReputation += itemTopic.getLevel() * userReputation;
                }
                scoreSum += rating.getScore() * userOverallReputation;
            }
            if (ratings.size() != 0) {
                estimatedScore = scoreSum / ratings.size();
                item.setE_score(estimatedScore);
                Repository.itemRepository.save(item);
            } else {
                item.setE_score(estimatedScore);
                Repository.itemRepository.save(item);
            }
        }
    }

    //计算非基于主题分析的商品综合得分
    public void estimatedScoreCalculationNotTB() {
        float estimatedScore = 0f;
        for (Item item : allItems) {
            float scoreSum = 0f;
            List<Rating> ratings = Repository.ratingRepository.findByItem(item);
            for (Rating rating : ratings) {
                float userOverallReputation = 0f;
                UserTopic userTopics = Repository.userTopicRepository
                        .findByUser(rating.getUser()).get(0);
                userOverallReputation = userTopics.getReputation();
                scoreSum += rating.getScore() * userOverallReputation;
            }
            if (ratings.size() != 0) {
                estimatedScore = scoreSum / ratings.size();
                item.setE_score(estimatedScore);
                Repository.itemRepository.save(item);
            } else {
                item.setE_score(estimatedScore);
                Repository.itemRepository.save(item);
            }
        }
    }

    //近邻优化算法，中的近邻优化步骤
    public void coldBootReputationKNN() {
        List<User> users = Repository.userRepository.findAll();
        for (User user : users) {
            List<Rating> ratings = Repository.ratingRepository.findByUser(user);
            if (ratings.size() <= 2) continue;
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (Rating rating : ratings) {
                Topic topic = Repository.itemTopicRepository.findByItem(rating.getItem()).get(0).getTopic();
                if (map.containsKey(topic.getName()))
                    map.put(topic.getName(), map.get(topic.getName()) + 1);
                else
                    map.put(topic.getName(), 1);
            }
            if (!checkMap(map)) continue;
            for (String key : map.keySet()) {
                if (map.get(key) == 1) {
                    List<User> neighbors = getNeighbors(key, map);
                    //System.out.println("neighbors size:" + neighbors.size());
                    User nearestNeighbor = getNearestNeighbor(user, neighbors, map);
                    if (nearestNeighbor != null) {
                        Topic topic = Repository.topicRepository.findByName(key);
                        UserTopic userTopic = Repository.userTopicRepository.findByUserAndTopic(user, topic).get(0);
                        UserTopic neighborTopic = Repository.userTopicRepository.findByUserAndTopic(nearestNeighbor, topic).get(0);
                        //System.out.println("neighbors size:" + neighbors.size()+" a:"+userTopic.getReputation()+" b:"+neighborTopic.getReputation());
                        userTopic.setReputation(neighborTopic.getReputation());
                        Repository.userTopicRepository.save(userTopic);
                    }

                }
            }
        }
    }

    public void coldBootReputationKNN2() {
        List<User> users = Repository.userRepository.findAll();
        int count = 0;
        int sum = users.size();
        for (User user : users) {
            //System.out.println(count+++"/"+sum);
            List<Rating> ratings = Repository.ratingRepository.findByUser(user);
            if (ratings.size() <= 2) continue;
            Map<String, Rating> ratingMap = new HashMap<String, Rating>();
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (Rating rating : ratings) {
                Topic topic = Repository.itemTopicRepository.findByItem(rating.getItem()).get(0).getTopic();
                if (map.containsKey(topic.getName())) {
                    map.put(topic.getName(), map.get(topic.getName()) + 1);
                    ratingMap.remove(topic.getName());
                } else {
                    map.put(topic.getName(), 1);
                    ratingMap.put(topic.getName(), rating);
                }
            }
            if (!checkMap(map)) continue;
            for (String key : map.keySet()) {
                if (map.get(key) == 1) {
                    User nearestNeighbor = getNeighbors2(key, map, user, ratingMap.get(key));
                    if (nearestNeighbor != null) {
                        Topic topic = Repository.topicRepository.findByName(key);
                        UserTopic userTopic = Repository.userTopicRepository.findByUserAndTopic(user, topic).get(0);
                        UserTopic neighborTopic = Repository.userTopicRepository.findByUserAndTopic(nearestNeighbor, topic).get(0);
                        System.out.println("A:" + userTopic.getReputation() + " B:" + neighborTopic.getReputation());
                        userTopic.setReputation((userTopic.getReputation() + neighborTopic.getReputation()) / 2);
                        Repository.userTopicRepository.save(userTopic);
                    }

                }
            }
        }
    }

    public void coldBootReputationKNN3() {
        List<User> users = Repository.userRepository.findAll();
        int count = 0;
        int sum = users.size();
        for (User user : users) {
            //System.out.println(count+++"/"+sum);
            List<Rating> ratings = Repository.ratingRepository.findByUser(user);
            if (ratings.size() <= 2) continue;
            Map<String, Rating> ratingMap = new HashMap<String, Rating>();
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (Rating rating : ratings) {
                Topic topic = Repository.itemTopicRepository.findByItem(rating.getItem()).get(0).getTopic();
                if (map.containsKey(topic.getName())) {
                    map.put(topic.getName(), map.get(topic.getName()) + 1);
                    ratingMap.remove(topic.getName());
                } else {
                    map.put(topic.getName(), 1);
                    ratingMap.put(topic.getName(), rating);
                }
            }
            if (!checkMap(map)) continue;
            for (String key : map.keySet()) {
                if (map.get(key) == 1) {
                    List<User> nearestNeighbors = getNeighbors3(key, map, user, ratingMap.get(key));
                    if (nearestNeighbors.size() != 0) {
                        float reputationByNeighbors = 0f;
                        Topic topic = Repository.topicRepository.findByName(key);
                        for (User neighbor : nearestNeighbors) {
                            UserTopic neighborTopic = Repository.userTopicRepository.findByUserAndTopic(neighbor, topic).get(0);
                            reputationByNeighbors += neighborTopic.getReputation();
                        }
                        UserTopic userTopic = Repository.userTopicRepository.findByUserAndTopic(user, topic).get(0);
                        System.out.println("A:" + userTopic.getReputation() + " B:" + reputationByNeighbors / (nearestNeighbors.size()));
                        userTopic.setReputation(reputationByNeighbors / nearestNeighbors.size());
                        Repository.userTopicRepository.save(userTopic);
                    }

                }
            }
        }
    }

    public void coldBootReputationKNN5_2() {
        List<User> users = Repository.userRepository.findAll();
        int count = 0;
        int sum = users.size();
        for (User user : users) {
            //System.out.println(count+++"/"+sum);
            List<Rating> ratings = Repository.ratingRepository.findByUser(user);
            if (ratings.size() <= 2) continue;
            Map<String, Rating> ratingMap = new HashMap<String, Rating>();
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (Rating rating : ratings) {
                Topic topic = Repository.itemTopicRepository.findByItem(rating.getItem()).get(0).getTopic();
                if (map.containsKey(topic.getName())) {
                    map.put(topic.getName(), map.get(topic.getName()) + 1);
                    ratingMap.remove(topic.getName());
                } else {
                    map.put(topic.getName(), 1);
                    ratingMap.put(topic.getName(), rating);
                }
            }
            if (!checkMap(map)) continue;
            for (String key : map.keySet()) {
                if (map.get(key) == 1) {
                    List<User> nearestNeighbors = getNeighbors5(key, map, user, ratingMap.get(key));
                    if (nearestNeighbors.size() != 0) {
                        float reputationByNeighbors = 0f;
                        Topic topic = Repository.topicRepository.findByName(key);
                        for (User neighbor : nearestNeighbors) {
                            UserTopic neighborTopic = Repository.userTopicRepository.findByUserAndTopic(neighbor, topic).get(0);
                            reputationByNeighbors += neighborTopic.getReputation();
                        }
                        UserTopic userTopic = Repository.userTopicRepository.findByUserAndTopic(user, topic).get(0);
                        System.out.println("A:" + userTopic.getReputation() + " B:" + reputationByNeighbors / (nearestNeighbors.size()));
                        userTopic.setReputation((userTopic.getReputation() + reputationByNeighbors / nearestNeighbors.size()) / 2);
                        Repository.userTopicRepository.save(userTopic);
                    }

                }
            }
        }
    }

    public void coldBootReputationKNN5() {
        List<User> users = Repository.userRepository.findAll();
        int count = 0;
        int sum = users.size();
        for (User user : users) {
            //System.out.println(count+++"/"+sum);
            List<Rating> ratings = Repository.ratingRepository.findByUser(user);
            if (ratings.size() <= 2) continue;
            Map<String, Rating> ratingMap = new HashMap<String, Rating>();
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (Rating rating : ratings) {
                Topic topic = Repository.itemTopicRepository.findByItem(rating.getItem()).get(0).getTopic();
                if (map.containsKey(topic.getName())) {
                    map.put(topic.getName(), map.get(topic.getName()) + 1);
                    ratingMap.remove(topic.getName());
                } else {
                    map.put(topic.getName(), 1);
                    ratingMap.put(topic.getName(), rating);
                }
            }
            if (!checkMap(map)) continue;
            for (String key : map.keySet()) {
                if (map.get(key) == 1) {
                    List<User> nearestNeighbors = getNeighbors5(key, map, user, ratingMap.get(key));
                    if (nearestNeighbors.size() != 0) {
                        float reputationByNeighbors = 0f;
                        Topic topic = Repository.topicRepository.findByName(key);
                        for (User neighbor : nearestNeighbors) {
                            UserTopic neighborTopic = Repository.userTopicRepository.findByUserAndTopic(neighbor, topic).get(0);
                            reputationByNeighbors += neighborTopic.getReputation();
                        }
                        UserTopic userTopic = Repository.userTopicRepository.findByUserAndTopic(user, topic).get(0);
                        System.out.println("A:" + userTopic.getReputation() + " B:" + reputationByNeighbors / (nearestNeighbors.size()));
                        userTopic.setReputation(reputationByNeighbors / nearestNeighbors.size());
                        Repository.userTopicRepository.save(userTopic);
                    }

                }
            }
        }
    }

    public boolean checkMap(Map<String, Integer> map) {
        boolean hasColdBootTopic = false;
        boolean hasNonColdBootTopic = false;
        for (String key : map.keySet()) {
            if (map.get(key) == 1)
                hasColdBootTopic = true;
            else if (map.get(key) > 1)
                hasNonColdBootTopic = true;
        }
        return hasColdBootTopic && hasNonColdBootTopic;
    }


    public User getNeighbors2(String topicStr, Map<String, Integer> map, User boss, Rating userRating) {
        User returnUser = null;
        float distance = 100f;
        List<User> users = Repository.userRepository.findAll();
        List<Rating> ratingItemList = Repository.ratingRepository.findByItemAndScore(userRating.getItem(), userRating.getScore());

        for (Rating ratingItem : ratingItemList) {
            User user = ratingItem.getUser();
            List<Rating> ratings = Repository.ratingRepository.findByUser(user);
            if (ratings.size() <= 2) continue;
            Map<String, Integer> userMap = new HashMap<String, Integer>();
            for (Rating rating : ratings) {
                Topic topic = Repository.itemTopicRepository.findByItem(rating.getItem()).get(0).getTopic();
                if (userMap.containsKey(topic.getName()))
                    userMap.put(topic.getName(), userMap.get(topic.getName()) + 1);
                else
                    userMap.put(topic.getName(), 1);
            }
            if (!userMap.containsKey(topicStr) || userMap.get(topicStr) == 1)//若用户在topic下为冷启动
                continue;
            boolean isbreak = true;
            boolean isbreak2 = false;
            for (String keyInMap : map.keySet()) {
                if (map.get(keyInMap) <= 1) continue;
                if (keyInMap.equals(topicStr)) continue;
                if (userMap.containsKey(keyInMap))
                    isbreak = false;
                if (userMap.containsKey(keyInMap) && userMap.get(keyInMap) == 1)//若用户在其他匹配topic下为冷启动
                    isbreak2 = true;
            }
            if (isbreak || isbreak2)
                continue;
            //System.out.println("1:"+map.toString()+" 2:"+userMap.toString());
            int count = 0;
            float sum = 0f;
            for (String key : map.keySet()) {
                if (map.get(key) <= 1)
                    continue;
                Topic topic = Repository.topicRepository.findByName(key);
                UserTopic userTopic = Repository.userTopicRepository.findByUserAndTopic(boss, topic).get(0);
                UserTopic neighborTopic = Repository.userTopicRepository.findByUserAndTopic(user, topic).get(0);
                sum += Math.abs(userTopic.getReputation() - neighborTopic.getReputation());
                count++;

            }
            if (count != 0 && sum / count < distance) {
                distance = sum / count;
                returnUser = user;
                //System.out.printf("a:"+map.toString()+" b:"+neighborMap.toString());
            }

        }
        return returnUser;
    }

    public List<User> getNearestNeighbors(User user, List<User> neighbors, Map<String, Integer> map) {
        if (neighbors.size() == 0)
            return null;
        List<User> returnUsers = new ArrayList<User>();
        float distance0 = 100f;
        float distance1 = 100f;
        float distance2 = 100f;
        User user0 = null;
        User user1 = null;
        User user2 = null;
        for (User neighbor : neighbors) {
            Map<String, Integer> neighborMap = gernerateTopicMap(neighbor);
            int count = 0;
            float sum = 0f;
            for (String key : map.keySet()) {
                if (map.get(key) <= 1)
                    continue;
                Topic topic = Repository.topicRepository.findByName(key);
                UserTopic userTopic = Repository.userTopicRepository.findByUserAndTopic(user, topic).get(0);
                UserTopic neighborTopic = Repository.userTopicRepository.findByUserAndTopic(neighbor, topic).get(0);
                sum += Math.pow(userTopic.getReputation() - neighborTopic.getReputation(), 2);
                count++;
            }
            float userDistance = 100f;
            if (count != 0) {
                userDistance = sum / count;
                if (user0 == null || userDistance < distance0)
                    user0 = neighbor;
                else if (user1 == null || userDistance < distance1)
                    user1 = neighbor;
                else if (user2 == null || userDistance < distance2)
                    user2 = neighbor;
            }
        }
        if (user0 != null)
            returnUsers.add(user0);
        if (user1 != null)
            returnUsers.add(user1);
        if (user2 != null)
            returnUsers.add(user2);
        return returnUsers;
    }

    public List<User> getNeighbors3(String topicStr, Map<String, Integer> map, User boss, Rating userRating) {
        User returnUser = null;
        float distance = 100f;
        List<User> users = Repository.userRepository.findAll();
        List<Rating> ratingItemList = Repository.ratingRepository.findByItemAndScore(userRating.getItem(), userRating.getScore());


        List<User> returnUsers = new ArrayList<User>();
        float distance0 = 100f;
        float distance1 = 100f;
        float distance2 = 100f;

        User user0 = null;
        User user1 = null;
        User user2 = null;

        for (Rating ratingItem : ratingItemList) {
            User user = ratingItem.getUser();
            List<Rating> ratings = Repository.ratingRepository.findByUser(user);
            if (ratings.size() <= 2) continue;
            Map<String, Integer> userMap = new HashMap<String, Integer>();
            for (Rating rating : ratings) {
                Topic topic = Repository.itemTopicRepository.findByItem(rating.getItem()).get(0).getTopic();
                if (userMap.containsKey(topic.getName()))
                    userMap.put(topic.getName(), userMap.get(topic.getName()) + 1);
                else
                    userMap.put(topic.getName(), 1);
            }
            if (!userMap.containsKey(topicStr) || userMap.get(topicStr) == 1)//若用户在topic下为冷启动
                continue;
            boolean isbreak = true;
            boolean isbreak2 = false;
            for (String keyInMap : map.keySet()) {
                if (map.get(keyInMap) <= 1) continue;
                if (keyInMap.equals(topicStr)) continue;
                if (userMap.containsKey(keyInMap))
                    isbreak = false;
                if (userMap.containsKey(keyInMap) && userMap.get(keyInMap) == 1)//若用户在其他匹配topic下为冷启动
                    isbreak2 = true;
            }
            if (isbreak || isbreak2)
                continue;
            //System.out.println("1:"+map.toString()+" 2:"+userMap.toString());
            int count = 0;
            float sum = 0f;
            for (String key : map.keySet()) {
                if (map.get(key) <= 1)
                    continue;
                Topic topic = Repository.topicRepository.findByName(key);
                UserTopic userTopic = Repository.userTopicRepository.findByUserAndTopic(boss, topic).get(0);
                UserTopic neighborTopic = Repository.userTopicRepository.findByUserAndTopic(user, topic).get(0);
                sum += Math.pow(userTopic.getReputation() - neighborTopic.getReputation(), 2);
                count++;

            }
            float userDistance = sum / count;
            if (count != 0) {
                if (user0 == null) {
                    user0 = user;
                } else if (userDistance < distance0) {
                    user0 = user;
                } else if (user1 == null) {
                    user1 = user;
                } else if (userDistance < distance1) {
                    user1 = user;
                } else if (user2 == null) {
                    user2 = user;
                } else if (userDistance < distance2) {
                    user2 = user;
                }
            }

        }
        if (user0 != null)
            returnUsers.add(user0);
        if (user1 != null)
            returnUsers.add(user1);
        if (user2 != null)
            returnUsers.add(user2);
        return returnUsers;
    }

    public List<User> getNeighbors5(String topicStr, Map<String, Integer> map, User boss, Rating userRating) {
        User returnUser = null;
        float distance = 100f;
        List<User> users = Repository.userRepository.findAll();
        List<Rating> ratingItemList = Repository.ratingRepository.findByItemAndScore(userRating.getItem(), userRating.getScore());


        List<User> returnUsers = new ArrayList<User>();
        float distance0 = 100f;
        float distance1 = 100f;
        float distance2 = 100f;
        float distance3 = 100f;
        float distance4 = 100f;


        User user0 = null;
        User user1 = null;
        User user2 = null;
        User user3 = null;
        User user4 = null;


        for (Rating ratingItem : ratingItemList) {
            User user = ratingItem.getUser();
            List<Rating> ratings = Repository.ratingRepository.findByUser(user);
            if (ratings.size() <= 2) continue;
            Map<String, Integer> userMap = new HashMap<String, Integer>();
            for (Rating rating : ratings) {
                Topic topic = Repository.itemTopicRepository.findByItem(rating.getItem()).get(0).getTopic();
                if (userMap.containsKey(topic.getName()))
                    userMap.put(topic.getName(), userMap.get(topic.getName()) + 1);
                else
                    userMap.put(topic.getName(), 1);
            }
            if (!userMap.containsKey(topicStr) || userMap.get(topicStr) == 1)//若用户在topic下为冷启动
                continue;
            boolean isbreak = true;
            boolean isbreak2 = false;
            for (String keyInMap : map.keySet()) {
                if (map.get(keyInMap) <= 1) continue;
                if (keyInMap.equals(topicStr)) continue;
                if (userMap.containsKey(keyInMap))
                    isbreak = false;
                if (userMap.containsKey(keyInMap) && userMap.get(keyInMap) == 1)//若用户在其他匹配topic下为冷启动
                    isbreak2 = true;
            }
            if (isbreak || isbreak2)
                continue;
            //System.out.println("1:"+map.toString()+" 2:"+userMap.toString());
            int count = 0;
            float sum = 0f;
            for (String key : map.keySet()) {
                if (map.get(key) <= 1)
                    continue;
                Topic topic = Repository.topicRepository.findByName(key);
                UserTopic userTopic = Repository.userTopicRepository.findByUserAndTopic(boss, topic).get(0);
                UserTopic neighborTopic = Repository.userTopicRepository.findByUserAndTopic(user, topic).get(0);
                sum += Math.pow(userTopic.getReputation() - neighborTopic.getReputation(), 2);
                count++;

            }
            float userDistance = sum / count;
            if (count != 0) {
                if (user0 == null) {
                    user0 = user;
                } else if (userDistance < distance0) {
                    user0 = user;
                } else if (user1 == null) {
                    user1 = user;
                } else if (userDistance < distance1) {
                    user1 = user;
                } else if (user2 == null) {
                    user2 = user;
                } else if (userDistance < distance2) {
                    user2 = user;
                } else if (user3 == null || userDistance < distance3) {
                    user3 = user;
                } else if (user4 == null || userDistance < distance4) {
                    user4 = user;
                }
            }

        }
        if (user0 != null)
            returnUsers.add(user0);
        if (user1 != null)
            returnUsers.add(user1);
        if (user2 != null)
            returnUsers.add(user2);
        if (user3 != null)
            returnUsers.add(user3);
        if (user4 != null)
            returnUsers.add(user4);
        return returnUsers;
    }

    public List<User> getNeighbors(String topicStr, Map<String, Integer> map) {
        List<User> returnUsers = new LinkedList<User>();
        List<User> users = Repository.userRepository.findAll();
        for (User user : users) {
            List<Rating> ratings = Repository.ratingRepository.findByUser(user);
            if (ratings.size() <= 2) continue;
            Map<String, Integer> userMap = new HashMap<String, Integer>();
            for (Rating rating : ratings) {
                Topic topic = Repository.itemTopicRepository.findByItem(rating.getItem()).get(0).getTopic();
                if (userMap.containsKey(topic.getName()))
                    userMap.put(topic.getName(), userMap.get(topic.getName()) + 1);
                else
                    userMap.put(topic.getName(), 1);
            }
            if (!userMap.containsKey(topicStr) || userMap.get(topicStr) == 1)//若用户在topic下为冷启动
                continue;
            boolean isbreak = true;
            boolean isbreak2 = false;
            for (String keyInMap : map.keySet()) {
                if (map.get(keyInMap) <= 1) continue;
                if (keyInMap.equals(topicStr)) continue;
                if (userMap.containsKey(keyInMap))
                    isbreak = false;
                if (userMap.containsKey(keyInMap) && userMap.get(keyInMap) == 1)//若用户在其他匹配topic下为冷启动
                    isbreak2 = true;
            }
            if (isbreak || isbreak2)
                continue;
            //System.out.println("1:"+map.toString()+" 2:"+userMap.toString());
            returnUsers.add(user);
        }
        return returnUsers;
    }

    public User getNearestNeighbor(User user, List<User> neighbors, Map<String, Integer> map) {
        if (neighbors.size() == 0)
            return null;
        User nearestNeighbor = null;
        float distance = 100f;
        for (User neighbor : neighbors) {
            Map<String, Integer> neighborMap = gernerateTopicMap(neighbor);
            int count = 0;
            float sum = 0f;
            for (String key : map.keySet()) {
                if (map.get(key) <= 1)
                    continue;
                Topic topic = Repository.topicRepository.findByName(key);
                UserTopic userTopic = Repository.userTopicRepository.findByUserAndTopic(user, topic).get(0);
                UserTopic neighborTopic = Repository.userTopicRepository.findByUserAndTopic(neighbor, topic).get(0);
                sum += Math.abs(userTopic.getReputation() - neighborTopic.getReputation());
                count++;

            }
            if (count != 0 && sum / count < distance) {
                distance = sum / count;
                nearestNeighbor = neighbor;
                //System.out.printf("a:"+map.toString()+" b:"+neighborMap.toString());
            }
        }
        return nearestNeighbor;
    }


    public Map<String, Integer> gernerateTopicMap(User user) {
        List<Rating> ratings = Repository.ratingRepository.findByUser(user);
        Map<String, Integer> userMap = new HashMap<String, Integer>();
        for (Rating rating : ratings) {
            Topic topic = Repository.itemTopicRepository.findByItem(rating.getItem()).get(0).getTopic();
            if (userMap.containsKey(topic.getName()))
                userMap.put(topic.getName(), userMap.get(topic.getName()) + 1);
            else
                userMap.put(topic.getName(), 1);
        }
        return userMap;
    }

    //使用平均分数来当做商品综合评分
    public void getAvgAsEscore() {
        float estimatedScore = 0f;
        for (Item item : allItems) {
            float scoreSum = 0f;
            List<Rating> ratings = Repository.ratingRepository.findByItem(item);
            for (Rating rating : ratings) {
                scoreSum += rating.getScore();
            }
            if (ratings.size() != 0) {
                estimatedScore = scoreSum / ratings.size();
                item.setE_score(estimatedScore);
                Repository.itemRepository.save(item);
            } else {
                item.setE_score(estimatedScore);
                Repository.itemRepository.save(item);
            }
        }
        //testMAE(-1);
    }

    //调用算法，实现用户可信度计算
    public void userReputationCalculation() {
        algorithm.userReputationCalculation(allUsers, lamda);
    }

    public void setLamda(Float lamda) {
        this.lamda = lamda;
    }

    public void meanDifferenceTest(int iteration) {
        float sum = 0;
        float result;
        for (Item item : allItems) {
            sum += item.getE_score();
        }
        if (iteration == 0) {
            float sum2 = 0;
            for (Item item : allItems) {
                sum2 += item.getScore();
            }
            test = sum2;
        }
        System.out.println("sum:" + sum);
        result = (sum - test) / allItems.size();
        System.out.println(iteration + ": " + result);

    }

    public void testDifference(int iteration) {
        List<Item> allItems = Repository.itemRepository.findAll();
        float sum = 0;
        int size = allItems.size();

        for (Item item : allItems) {
            sum += Math.abs(item.getE_score() - item.getScore());
        }

//        for (int i = 0; i < allItems.size(); i++) {
//            a[i] = Double.valueOf(allItems.get(i).getScore()+"");
//            b[i] =  Double.valueOf(allItems.get(i).getE_score()+"");
//        }
//        KendallsCorrelation kendallsCorrelation = new KendallsCorrelation();
        double result = sum / size;


        System.out.println(iteration + ": " + result + "  ");


    }

    //计算商品评分与真实商品评分之间的MAE
    public void testMAE(int iteration) {
        List<Item> allItems = Repository.itemRepository.findAll();
        float sum = 0;
        int size = allItems.size();
//        double[] a = new double[size];
//        double[] b = new double[size];

        for (Item item : allItems) {
            sum += Math.abs(item.getE_score() - item.getScore());
        }

//        for (int i = 0; i < allItems.size(); i++) {
//            a[i] = Double.valueOf(allItems.get(i).getScore()+"");
//            b[i] =  Double.valueOf(allItems.get(i).getE_score()+"");
//        }
//        KendallsCorrelation kendallsCorrelation = new KendallsCorrelation();
        double result = sum / size;

        System.out.println();

        System.out.println("lamda:" + lamda + "  iteration:" + iteration + "  result:" + result + "   " + new Date().toGMTString());
        ReputationSystemTest.pw.println("lamda:" + lamda + "  iteration:" + iteration + "  result:" + result + "   " + new Date().toGMTString());
        ReputationSystemTest.pw.flush();

    }

}
