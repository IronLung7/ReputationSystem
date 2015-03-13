package com.rs.data.entity;

import javax.persistence.*;

/**
 * Created by leo on 15/3/13.
 */
@Entity
@Table(name = "topic", schema = "public")
public class Topic implements java.io.Serializable  {
    private Long id;
    private String name;

    public Topic() {
    }

    public Topic(String name) {
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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "topic_id_seq")
    @SequenceGenerator(name = "topic_id_seq", sequenceName = "topic_id_seq", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
