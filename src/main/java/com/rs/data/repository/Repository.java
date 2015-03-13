package com.rs.data.repository;

import com.rs.data.entity.*;
import org.springframework.context.ConfigurableApplicationContext;

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

    public static void eraseAllData(){
        userTopicRepository.deleteAll();
        itemTopicRepository.deleteAll();
        ratingRepository.deleteAll();
        topicRepository.deleteAll();
        userRepository.deleteAll();
        itemRepository.deleteAll();
    }

    public static void insertTestData() {
        Item item0 = new Item("item0");
        Item item1 = new Item("item1");
        Item item2 = new Item("item2");
        Item item3 = new Item("item3");
        Item item4 = new Item("item4");
        Item item5 = new Item("item5");

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
