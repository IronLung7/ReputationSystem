package com.rs.algorithm;

import com.rs.data.entity.*;
import com.rs.data.repository.Repository;

import java.util.List;

/**
 * Created by leo on 15/3/27.
 */
public class TB_L1_AVG implements BasicAlgorithm {

    @Override
    public void estimatedScoreCalculation() {

    }

    @Override
    public void userReputationCalculation(List<User> allUsers,Float lamda) {
        float reputation = 1f;

        for (User user : allUsers) {
            List<UserTopic> userTopics = Repository.userTopicRepository
                    .findByUser(user);
            List<Rating> ratings = Repository.ratingRepository
                    .findByUser(user);
            for (UserTopic userTopic : userTopics) {
                Topic topic = userTopic.getTopic();
                float formula0 = 0f;//dividend
                float formula1 = 0f;//divisor
                for (Rating rating : ratings) {
                    List<ItemTopic> itemTopics = Repository.itemTopicRepository
                            .findByItemAndTopic(rating.getItem(), topic);
                    if (itemTopics.size() != 0) {
                        formula0 += itemTopics.get(0).getLevel()
                                * Math.abs(rating.getScore()
                                -  rating.getItem().getE_score());
                        formula1 += itemTopics.get(0).getLevel();
                    }
                }
                if (formula1 != 0) {
                    reputation = 1 - lamda * formula0 / formula1;
                }
                if(reputation<=0) reputation=0;
                userTopic.setReputation(reputation);
                Repository.userTopicRepository.save(userTopic);
            }
        }
    }
}
