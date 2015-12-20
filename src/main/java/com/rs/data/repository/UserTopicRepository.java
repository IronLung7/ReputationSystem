package com.rs.data.repository;

import com.rs.data.entity.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by leo on 15/3/13.
 */
public interface UserTopicRepository extends CrudRepository<UserTopic, Long> {
    List<UserTopic> findAll();

    UserTopic findById(Long id);

    List<UserTopic> findByUser(User user);

    List<UserTopic> findByUserAndTopic(User user,Topic topic);
}
