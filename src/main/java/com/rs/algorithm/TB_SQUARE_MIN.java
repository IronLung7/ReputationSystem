package com.rs.algorithm;

import com.rs.data.entity.*;
import com.rs.data.repository.Repository;

import java.util.List;

/**
 * Created by leo on 15/3/27.
 */
public class TB_SQUARE_MIN implements BasicAlgorithm {

    @Override
    public void estimatedScoreCalculation() {

    }

    @Override
    public void userReputationCalculation(List<User> allUsers, Float lamda) {
        float reputation = 1f;

        for (User user : allUsers) {
            List<UserTopic> userTopics = Repository.userTopicRepository.findByUser(user);
            List<Rating> ratings = Repository.ratingRepository.findByUser(user);
            for (UserTopic userTopic : userTopics) {
                Topic topic = userTopic.getTopic();
                float min = 100f;
                for (Rating rating : ratings) {
                    List<ItemTopic> itemTopics = Repository.itemTopicRepository.findByItemAndTopic(rating.getItem(), topic);
                    if (itemTopics.size() != 0) {
                        float temp = itemTopics.get(0).getLevel() * Math.abs(rating.getScore() - rating.getItem().getE_score())* Math.abs(rating.getScore() - rating.getItem().getE_score());
                        if (temp <min) {
                            min = temp;
                        }
                    }
                }

                if(min != 100f)
                    reputation = 1 - lamda/2 * min;
                if(reputation<=0) reputation=0;
                userTopic.setReputation(reputation);
                Repository.userTopicRepository.save(userTopic);
            }

        }
    }

}
