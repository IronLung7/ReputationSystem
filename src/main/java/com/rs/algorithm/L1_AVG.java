package com.rs.algorithm;

import com.rs.data.entity.*;
import com.rs.data.repository.Repository;

import java.util.List;

/**
 * Created by leo on 15/3/27.
 */
public class L1_AVG implements BasicAlgorithm {

    @Override
    public void estimatedScoreCalculation() {

    }

    @Override
    public void userReputationCalculation(List<User> allUsers, Float lamda) {
        float reputation = 1f;

        for (User user : allUsers) {
            List<UserTopic> userTopics = Repository.userTopicRepository
                    .findByUser(user);
            List<Rating> ratings = Repository.ratingRepository
                    .findByUser(user);

            float formula0 = 0f;
            for (Rating rating : ratings) {
                formula0 += Math.abs(rating.getScore()
                        - rating.getItem().getE_score());
            }

            //System.out.println(formula0+" "+ratings.size());
            if(ratings.size()!=0){
                reputation = 1f - lamda * formula0 /ratings.size();
            }
            if(reputation<=0) reputation=0;
            for(UserTopic userTopic:userTopics){
                userTopic.setReputation(reputation);
                Repository.userTopicRepository.save(userTopic);
            }
        }
    }
}
