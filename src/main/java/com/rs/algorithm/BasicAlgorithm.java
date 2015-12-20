package com.rs.algorithm;

import com.rs.data.entity.User;

import java.util.List;

/**
 * Created by leo on 15/3/14.
 */
public interface BasicAlgorithm {

    //计算商品平均得分
    public void estimatedScoreCalculation();

    //计算用户可信度
    public void userReputationCalculation(List<User> users, Float lamda);
}
