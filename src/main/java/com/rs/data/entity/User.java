package com.rs.data.entity;

import javax.persistence.*;

/**
 * Created by leo on 15/3/13.
 */
@Entity
@Table(name = "user", schema = "public")
public class User implements java.io.Serializable {
    private Long id;
    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
