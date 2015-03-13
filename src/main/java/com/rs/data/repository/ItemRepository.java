package com.rs.data.repository;

import com.rs.data.entity.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by leo on 15/3/13.
 */
public interface ItemRepository extends CrudRepository<Item, Long> {
    List<Item> findAll();

    Item findById(Long id);
}
