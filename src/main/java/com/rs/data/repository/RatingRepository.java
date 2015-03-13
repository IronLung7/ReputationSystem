package com.rs.data.repository;

import com.rs.data.entity.Item;
import com.rs.data.entity.Rating;
import com.rs.data.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by leo on 15/3/13.
 */
public interface RatingRepository extends CrudRepository<Rating, Long> {
    List<Rating> findAll();

    Rating findById(Long id);

    List<Rating> findByItem(Item item);

    List<Rating> findByUser(User user);
}
