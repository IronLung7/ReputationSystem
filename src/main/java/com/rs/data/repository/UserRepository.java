package com.rs.data.repository;

import com.rs.data.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by leo on 15/3/13.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();

    User findById(Long id);
}
