package com.rs.data.repository;

import com.rs.data.entity.Item;
import com.rs.data.entity.ItemTopic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by leo on 15/3/13.
 */
public interface ItemTopicRepository extends CrudRepository<ItemTopic, Long> {
    List<ItemTopic> findAll();

    ItemTopic findById(Long id);

    List<ItemTopic> findByItem(Item item);
}
