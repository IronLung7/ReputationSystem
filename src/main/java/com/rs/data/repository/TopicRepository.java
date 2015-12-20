package com.rs.data.repository;

import com.rs.data.entity.Item;
import com.rs.data.entity.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by leo on 15/3/13.
 */
public interface TopicRepository extends CrudRepository<Topic, Long> {
    List<Topic> findAll();

    Topic findById(Long id);
    Topic findByName(String name);
}
