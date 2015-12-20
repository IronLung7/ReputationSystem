package com.rs.server;

import com.rs.algorithm.BasicAlgorithm;
import com.rs.data.entity.*;
import com.rs.data.repository.Repository;

import java.util.*;

/**
 * Created by leo on 15/3/14.
 */
public class test {
    private float test;

    private List<User> allUsers;
    private List<Item> allItems;
    private List<Topic> allTopics;

    Float lamda;
    int iteration = 10;
    int nowiteration = 0;

    private BasicAlgorithm algorithm;


    public test() {
        allUsers = Repository.userRepository.findAll();
        allItems = Repository.itemRepository.findAll();
        allTopics = Repository.topicRepository.findAll();
    }

    public void run(BasicAlgorithm algorithm) {
        this.algorithm = algorithm;
        initUserReputation();
        start();
    }

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

    public void runKNN(BasicAlgorithm algorithm) {
        this.algorithm = algorithm;
        initUserReputation();
        nowiteration = 0;
        for (int i = 0; i < iteration; i++) {
            long startTime = System.nanoTime();
            System.out.println("start ");
            estimatedScoreCalculation();
            System.out.println("time0 "+(System.nanoTime()-startTime));

            if(i!=iteration-1){
                userReputationCalculation();
                System.out.println("time1"+(System.nanoTime()-startTime));
                coldBootReputationKNN2();
                System.out.println("time2"+(System.nanoTime()-startTime));
            }

            testMAE(i);
            nowiteration++;
        }
    }

    public void start() {
        nowiteration = 0;
        for (int i = 0; i < iteration; i++) {
            estimatedScoreCalculation();
            userReputationCalculation();
            testMAE(i);
            nowiteration++;
        }
    }

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


    public void coldBootReputationKNN(){
        List<User> users = Repository.userRepository.findAll();
        for (User user : users) {
            List<Rating> ratings = Repository.ratingRepository.findByUser(user);
            if(ratings.size()<=2) continue;
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (Rating rating : ratings) {
                Topic topic = Repository.itemTopicRepository.findByItem(rating.getItem()).get(0).getTopic();
                if (map.containsKey(topic.getName()))
                    map.put(topic.getName(), map.get(topic.getName()) + 1);
                else
                    map.put(topic.getName(), 1);
            }
            if(!checkMap(map)) continue;
            for(String key :map.keySet()){
                if(map.get(key)==1){
                    List<User> neighbors= getNeighbors(key,map);
                    User nearestNeighbor = getNearestNeighbor(user,neighbors,map);
                    if(nearestNeighbor!=null){
                        Topic topic = Repository.topicRepository.findByName(key);
                        UserTopic userTopic = Repository.userTopicRepository.findByUserAndTopic(user,topic).get(0);
                        UserTopic neighborTopic = Repository.userTopicRepository.findByUserAndTopic(nearestNeighbor,topic).get(0);
                        userTopic.setReputation(neighborTopic.getReputation());
                        Repository.userTopicRepository.save(userTopic);
                    }

                }
            }


        }
    }

    public void coldBootReputationKNN2(){
        List<User> users = Repository.userRepository.findAll();
        int count =0;
        int sum = users.size();
        for (User user : users) {
            System.out.println(count+++"/"+sum);
            List<Rating> ratings = Repository.ratingRepository.findByUser(user);
            if(ratings.size()<=2) continue;
            Map<String,Rating> ratingMap = new HashMap<String, Rating>();
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (Rating rating : ratings) {
                Topic topic = Repository.itemTopicRepository.findByItem(rating.getItem()).get(0).getTopic();
                if (map.containsKey(topic.getName())){
                    map.put(topic.getName(), map.get(topic.getName()) + 1);
                    ratingMap.remove(topic.getName());
                }

                else{
                    map.put(topic.getName(), 1);
                    ratingMap.put(topic.getName(), rating);
                }

            }
            if(!checkMap(map)) continue;
            for(String key :map.keySet()){
                if(map.get(key)==1){
                    User nearestNeighbor= getNeighbors2(key, map,user,ratingMap.get(key));
                    if(nearestNeighbor!=null){
                        Topic topic = Repository.topicRepository.findByName(key);
                        UserTopic userTopic = Repository.userTopicRepository.findByUserAndTopic(user,topic).get(0);
                        UserTopic neighborTopic = Repository.userTopicRepository.findByUserAndTopic(nearestNeighbor,topic).get(0);
                        userTopic.setReputation(neighborTopic.getReputation());
                        Repository.userTopicRepository.save(userTopic);
                    }

                }
            }


        }
    }

    public boolean checkMap(Map<String,Integer> map){
        boolean hasColdBootTopic = false;
        boolean hasNonColdBootTopic = false;
        for (String key:map.keySet()){
            if(map.get(key)==1)
                hasColdBootTopic=true;
            else if (map.get(key)>1)
                hasNonColdBootTopic=true;
        }
        return hasColdBootTopic&&hasNonColdBootTopic;
    }

    public User getNeighbors2(String topicStr, Map<String,Integer> map,User boss, Rating userRating){
        User returnUser = null;
        float distance =100f;
        List<User> users = Repository.userRepository.findAll();
        List<Rating> ratingItemList= Repository.ratingRepository.findByItemAndScore(userRating.getItem(), userRating.getScore());

        for(Rating ratingItem:ratingItemList){
            User user = ratingItem.getUser();
            List<Rating> ratings = Repository.ratingRepository.findByUser(user);
            if(ratings.size()<=2) continue;
            Map<String, Integer> userMap = new HashMap<String, Integer>();
            for (Rating rating : ratings) {
                Topic topic = Repository.itemTopicRepository.findByItem(rating.getItem()).get(0).getTopic();
                if (userMap.containsKey(topic.getName()))
                    userMap.put(topic.getName(), userMap.get(topic.getName()) + 1);
                else
                    userMap.put(topic.getName(), 1);
            }
            if(!userMap.containsKey(topicStr)||userMap.get(topicStr)==1)//若用户在topic下为冷启动
                continue;
            boolean isbreak=true;
            boolean isbreak2=false;
            for(String keyInMap:map.keySet()){
                if(map.get(keyInMap)<=1) continue;
                if(keyInMap.equals(topicStr)) continue;
                if(userMap.containsKey(keyInMap))
                    isbreak=false;
                if(userMap.containsKey(keyInMap)&&userMap.get(keyInMap)==1)//若用户在其他匹配topic下为冷启动
                    isbreak2=true;
            }
            if(isbreak||isbreak2)
                continue;
            System.out.println("1:"+map.toString()+" 2:"+userMap.toString());
            int count=0;
            float sum=0f;
            for(String key:map.keySet()){
                if(map.get(key)<=1)
                    continue;
                Topic topic= Repository.topicRepository.findByName(key);
                UserTopic userTopic = Repository.userTopicRepository.findByUserAndTopic(boss,topic).get(0);
                UserTopic neighborTopic = Repository.userTopicRepository.findByUserAndTopic(user,topic).get(0);
                sum += Math.abs(userTopic.getReputation() - neighborTopic.getReputation());
                count++;

            }
            if(count!=0 && sum/count<distance){
                distance=sum/count;
                returnUser=user;
                //System.out.printf("a:"+map.toString()+" b:"+neighborMap.toString());
            }

        }
        return returnUser;
    }

    public List<User> getNeighbors(String topicStr, Map<String,Integer> map){
        List<User> returnUsers = new LinkedList<User>();
        List<User> users = Repository.userRepository.findAll();
        for(User user:users){
            List<Rating> ratings = Repository.ratingRepository.findByUser(user);
            if(ratings.size()<=2) continue;
            Map<String, Integer> userMap = new HashMap<String, Integer>();
            for (Rating rating : ratings) {
                Topic topic = Repository.itemTopicRepository.findByItem(rating.getItem()).get(0).getTopic();
                if (userMap.containsKey(topic.getName()))
                    userMap.put(topic.getName(), userMap.get(topic.getName()) + 1);
                else
                    userMap.put(topic.getName(), 1);
            }
            if(!userMap.containsKey(topicStr)||userMap.get(topicStr)==1)//若用户在topic下为冷启动
                continue;
            boolean isbreak=true;
            boolean isbreak2=false;
            for(String keyInMap:map.keySet()){
                if(map.get(keyInMap)<=1) continue;
                if(keyInMap.equals(topicStr)) continue;
                if(userMap.containsKey(keyInMap))
                    isbreak=false;
                if(userMap.containsKey(keyInMap)&&userMap.get(keyInMap)==1)//若用户在其他匹配topic下为冷启动
                    isbreak2=true;
            }
            if(isbreak||isbreak2)
                continue;
            returnUsers.add(user);
        }
        return returnUsers;
    }

    public User getNearestNeighbor(User user, List<User> neighbors, Map<String,Integer> map){
        if(neighbors.size()==0)
            return null;
        User nearestNeighbor =null;
        float distance =100f;
        for(User neighbor:neighbors){
            int count=0;
            float sum=0f;
            for(String key:map.keySet()){
                if(map.get(key)<=1)
                    continue;
                Topic topic= Repository.topicRepository.findByName(key);
                UserTopic userTopic = Repository.userTopicRepository.findByUserAndTopic(user,topic).get(0);
                UserTopic neighborTopic = Repository.userTopicRepository.findByUserAndTopic(neighbor,topic).get(0);
                sum += Math.pow(userTopic.getReputation() - neighborTopic.getReputation(),2);
                count++;

            }
            if(count!=0 && sum/count<distance){
                distance=sum/count;
                nearestNeighbor=neighbor;
            }
        }
        return nearestNeighbor;
    }

    public Map<String ,Integer> gernerateTopicMap(User user){
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
        testMAE(-1);
    }

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
