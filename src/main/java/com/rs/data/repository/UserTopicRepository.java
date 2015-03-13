package com.rs.data.repository;

import com.rs.data.entity.Item;
import com.rs.data.entity.ItemTopic;
import com.rs.data.entity.User;
import com.rs.data.entity.UserTopic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by leo on 15/3/13.
 */
public interface UserTopicRepository extends CrudRepository<UserTopic, Long> {
    List<UserTopic> findAll();

    UserTopic findById(Long id);

    List<UserTopic> findByUser(User user);
}
