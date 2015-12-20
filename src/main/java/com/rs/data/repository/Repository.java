package com.rs.data.repository;

import com.rs.data.entity.*;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by leo on 15/3/13.
 */
public class Repository {
    public static ItemRepository itemRepository;
    public static ItemTopicRepository itemTopicRepository;
    public static RatingRepository ratingRepository;
    public static TopicRepository topicRepository;
    public static UserRepository userRepository;
    public static UserTopicRepository userTopicRepository;

    public Repository(ConfigurableApplicationContext context) {
        this.itemRepository = context.getBean(ItemRepository.class);
        this.itemTopicRepository = context.getBean(ItemTopicRepository.class);
        this.ratingRepository = context.getBean(RatingRepository.class);
        this.topicRepository = context.getBean(TopicRepository.class);
        this.userRepository = context.getBean(UserRepository.class);
        this.userTopicRepository = context.getBean(UserTopicRepository.class);
    }

    public static void eraseAllData() {
        userTopicRepository.deleteAll();
        itemTopicRepository.deleteAll();
        ratingRepository.deleteAll();
        topicRepository.deleteAll();
        userRepository.deleteAll();
        itemRepository.deleteAll();
    }



    int commentCount = 3386;
    int userCount = 988;
    int shopCount = 414;
    int topicCount = 10;

    Topic topic0 = null;
    Topic topic1 = null;
    Topic topic2 = null;
    Topic topic3 = null;
    Topic topic4 = null;
    Topic topic5 = null;
    Topic topic6 = null;
    Topic topic7 = null;
    Topic topic8 = null;
    Topic topic9 = null;

    List<Item> itemList =null;

    Random random = new Random();
    int userIdCount = 0;

    public void testData() {
        //add test topic
        topic0 = new Topic("topic0");
        topic1 = new Topic("topic1");
        topic2 = new Topic("topic2");
        topic3 = new Topic("topic3");
        topic4 = new Topic("topic4");
        topic5 = new Topic("topic5");
        topic6 = new Topic("topic6");
        topic7 = new Topic("topic7");
        topic8 = new Topic("topic8");
        topic9 = new Topic("topic9");

        Repository.topicRepository.save(topic0);
        Repository.topicRepository.save(topic1);
        Repository.topicRepository.save(topic2);
        Repository.topicRepository.save(topic3);
        Repository.topicRepository.save(topic4);
        Repository.topicRepository.save(topic5);
        Repository.topicRepository.save(topic6);
        Repository.topicRepository.save(topic7);
        Repository.topicRepository.save(topic8);
        Repository.topicRepository.save(topic9);

        itemList = new ArrayList<Item>();

        for (int i = 0; i < shopCount; i++) {
            Item item = new Item("item" + i);
            int probability = getRandom(0, 100);
            if (probability < 5)
                item.setScore(2f);
            else if (probability < 10)
                item.setScore(3f);
            else if (probability < 17)
                item.setScore(4f);
            else if (probability < 25)
                item.setScore(5f);
            else if (probability < 34)
                item.setScore(6f);
            else if (probability < 43)
                item.setScore(7f);
            else if (probability < 64)
                item.setScore(8f);
            else if (probability < 87)
                item.setScore(9f);
            else
                item.setScore(10f);
            ItemTopic itemTopic = new ItemTopic(1f, item, getTopic(getRandom(0, 9)));
            Repository.itemRepository.save(item);
            itemList.add(item);
            Repository.itemTopicRepository.save(itemTopic);
        }

        addTestUsers(399,1,3,3);
        addTestUsers(177,2,3,3);
        addTestUsers(102,3,3,3);
        addTestUsers(66,4,3,3);
        addTestUsers(47,5,3,3);
        addTestUsers(34,6,3,3);
        addTestUsers(26,7,3,4);
        addTestUsers(21,8,2,4);
        addTestUsers(17,9,3,4);
        addTestUsers(14,10,2,5);
        addTestUsers(11,11,2,4);
        addTestUsers(10,12,3,4);
        addTestUsers(8,13,3,3);
        addTestUsers(7,14,2,3);
        addTestUsers(6,15,3,3);
        addTestUsers(5,16,3,4);
        addTestUsers(4,17,2,3);
        addTestUsers(4,18,1,4);
        addTestUsers(4,19,2,3);
        addTestUsers(3,20,1,3);
        addTestUsers(3,21,3,3);
        addTestUsers(3,22,2,3);
        addTestUsers(3,23,2,3);
        addTestUsers(2,24,1,3);
        addTestUsers(2,25,2,3);
        addTestUsers(2,26,3,3);
        addTestUsers(1,27,2,3);
        addTestUsers(1,28,2,3);
        addTestUsers(1,29,1,4);
        addTestUsers(1,30,2,4);
        addTestUsers(1,31,1,4);
        addTestUsers(1,32,1,3);
        addTestUsers(1,33,2,3);
        addTestUsers(1,34,1,4);




    }

    public void addTestUsers(int userNum, int commentNum , int bad,int good){
        for(int i = 0 ;i<userNum;i++) {
            User user = new User("user"+userIdCount);
            int reputation[] = new int[10];
            if(getRandom(0,9)<5){
                for(int l =0;i<bad;i++){
                    reputation[getRandom(0,9)]=-1;
                }
                for(int l =0;i<good;i++){
                    reputation[getRandom(0,9)]=1;
                }
            }

            userIdCount++;
            Repository.userRepository.save(user);

            for(int j =0 ;j<commentNum;j++){
                Rating rating = new Rating();
                rating.setUser(user);
                Item ratingItem = itemList.get(getRandom(0,shopCount-1));
                rating.setItem(ratingItem);
                ItemTopic itemTopic = Repository.itemTopicRepository.findByItem(ratingItem).get(0);
                int topicNum = Integer.valueOf(itemTopic.getTopic().getName().charAt(5)+"");
                float score = 0f;
                if(reputation[topicNum]==0){

                    if(getRandom(0,100)<3){
                        score=0;
                    }else
                    if(getRandom(0,9)<7){
                        if(getRandom(0,9)<5)
                            score = ratingItem.getScore()+getRandom(7,35)/10f;
                        else
                            score = ratingItem.getScore()-getRandom(7,35)/10f;
                    }else{
                        if(getRandom(0,9)<6)
                            score = ratingItem.getScore()+getRandom(10,50)/10f;
                        else
                            score = ratingItem.getScore()-getRandom(10,50)/10f;
                    }
                }
                if(reputation[topicNum]==1){
                    if(getRandom(0,9)<6)
                        score = ratingItem.getScore()+getRandom(0,15)/10f;
                    else
                        score = ratingItem.getScore()-getRandom(0,15)/10f;

                }
                if(reputation[topicNum]==-1){
                    int a = getRandom(0,9);
                    if(getRandom(0,100)<4){
                        score=0;
                    }
                    if(a<3){
                        if(getRandom(0,9)<3)
                            score = ratingItem.getScore()+getRandom(20,40)/10f;
                        else
                            score = ratingItem.getScore()-getRandom(20,40)/10f;
                    }else if(a<7){
                        if(getRandom(0,9)<3)
                            score = ratingItem.getScore()+getRandom(20,50)/10f;
                        else
                            score = ratingItem.getScore()-getRandom(20,50)/10f;
                    }else{
                        if(getRandom(0,9)<3)
                            score = ratingItem.getScore()+getRandom(30,70)/10f;
                        else
                            score = ratingItem.getScore()-getRandom(30,70)/10f;
                    }
                }

                if(commentNum==1){
                    if(getRandom(0,9)<7)
                        score = ratingItem.getScore()+getRandom(5,20)/10f;
                    else
                        score = ratingItem.getScore()-getRandom(5,15)/10f;
                }

                if(score<=2)
                    score=2;
                else if(score<=4)
                    score=4;
                else if(score<=6)
                    score=6;
                else if(score<=8)
                    score=8;
                else
                    score=10;
                rating.setScore(Float.valueOf(score));
                Repository.ratingRepository.save(rating);
            }

        }
    }


    public Topic getTopic(int num) {
        switch (num) {
            case 0:
                return topic0;
            case 1:
                return topic1;
            case 2:
                return topic2;
            case 3:
                return topic3;
            case 4:
                return topic4;
            case 5:
                return topic5;
            case 6:
                return topic6;
            case 7:
                return topic7;
            case 8:
                return topic8;
            case 9:
                return topic9;
            default:
                return topic0;
        }
    }

    public int getRandom(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }


    public static void insertTestData() {
        Item item0 = new Item("item0");
        Item item1 = new Item("item1");
        Item item2 = new Item("item2");
        Item item3 = new Item("item3");
        Item item4 = new Item("item4");
        Item item5 = new Item("item5");

        item0.setScore(8.8f);
        item1.setScore(5.6f);
        item2.setScore(8.7f);
        item3.setScore(8.3f);
        item4.setScore(3.9f);
        item5.setScore(5.6f);

        User user0 = new User("user0");
        User user1 = new User("user1");
        User user2 = new User("user2");
        User user3 = new User("user3");
        User user4 = new User("user4");

        Topic topic0 = new Topic("topic0");
        Topic topic1 = new Topic("topic1");

        ItemTopic itemTopic0 = new ItemTopic(1F, item0, topic0);
        ItemTopic itemTopic1 = new ItemTopic(1F, item1, topic0);
        ItemTopic itemTopic2 = new ItemTopic(1F, item2, topic0);
        ItemTopic itemTopic3 = new ItemTopic(1F, item3, topic1);
        ItemTopic itemTopic4 = new ItemTopic(1F, item4, topic1);
        ItemTopic itemTopic5 = new ItemTopic(1F, item5, topic1);

        Rating rating00 = new Rating(user0, item0, 9F);
        Rating rating01 = new Rating(user0, item1, 6F);
        Rating rating02 = new Rating(user0, item2, 7F);
        Rating rating03 = new Rating(user0, item3, 4F);
        Rating rating04 = new Rating(user0, item4, 8F);
        Rating rating05 = new Rating(user0, item5, 10F);

        Rating rating10 = new Rating(user1, item0, 4F);
        Rating rating11 = new Rating(user1, item1, 9F);
        Rating rating12 = new Rating(user1, item2, 4F);
        Rating rating13 = new Rating(user1, item3, 8F);
        Rating rating14 = new Rating(user1, item4, 4F);
        Rating rating15 = new Rating(user1, item5, 6F);

        Rating rating20 = new Rating(user2, item0, 8F);
        Rating rating21 = new Rating(user2, item1, 5F);
        Rating rating22 = new Rating(user2, item2, 8F);
        Rating rating23 = new Rating(user2, item3, 9F);
        Rating rating24 = new Rating(user2, item4, 4F);
        Rating rating25 = new Rating(user2, item5, 6F);

        Rating rating30 = new Rating(user3, item0, 9F);
        Rating rating31 = new Rating(user3, item1, 5F);
        Rating rating32 = new Rating(user3, item2, 9F);
        Rating rating33 = new Rating(user3, item3, 8F);
        Rating rating34 = new Rating(user3, item4, 4F);
        Rating rating35 = new Rating(user3, item5, 6F);

        Rating rating40 = new Rating(user4, item0, 8F);
        Rating rating41 = new Rating(user4, item1, 6F);
        Rating rating42 = new Rating(user4, item2, 9F);
        Rating rating43 = new Rating(user4, item3, 8F);
        Rating rating44 = new Rating(user4, item4, 4F);
        Rating rating45 = new Rating(user4, item5, 6F);

        itemRepository.save(item0);
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        itemRepository.save(item4);
        itemRepository.save(item5);

        userRepository.save(user0);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        topicRepository.save(topic0);
        topicRepository.save(topic1);

        itemTopicRepository.save(itemTopic0);
        itemTopicRepository.save(itemTopic1);
        itemTopicRepository.save(itemTopic2);
        itemTopicRepository.save(itemTopic3);
        itemTopicRepository.save(itemTopic4);
        itemTopicRepository.save(itemTopic5);

        ratingRepository.save(rating00);
        ratingRepository.save(rating01);
        ratingRepository.save(rating02);
        ratingRepository.save(rating03);
        ratingRepository.save(rating04);
        ratingRepository.save(rating05);
        ratingRepository.save(rating10);
        ratingRepository.save(rating11);
        ratingRepository.save(rating12);
        ratingRepository.save(rating13);
        ratingRepository.save(rating14);
        ratingRepository.save(rating15);
        ratingRepository.save(rating20);
        ratingRepository.save(rating21);
        ratingRepository.save(rating22);
        ratingRepository.save(rating23);
        ratingRepository.save(rating24);
        ratingRepository.save(rating25);
        ratingRepository.save(rating30);
        ratingRepository.save(rating31);
        ratingRepository.save(rating32);
        ratingRepository.save(rating33);
        ratingRepository.save(rating34);
        ratingRepository.save(rating35);
        ratingRepository.save(rating40);
        ratingRepository.save(rating41);
        ratingRepository.save(rating42);
        ratingRepository.save(rating43);
        ratingRepository.save(rating44);
        ratingRepository.save(rating45);


    }
}
