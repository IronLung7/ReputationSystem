package com.rs.data.entity;

import javax.persistence.*;

/**
 * Created by leo on 15/3/13.
 */
@Entity
@Table(name = "user_topic", schema = "public")
public class UserTopic implements java.io.Serializable {
    private Long id;
    private Float reputation;
    private User user;
    private Topic topic;

    public UserTopic() {
    }

    public UserTopic(Topic topic, User user, Float reputation) {
        this.topic = topic;
        this.user = user;
        this.reputation = reputation;
    }

    @Id

    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_topic_id_seq")
    @SequenceGenerator(name = "user_topic_id_seq", sequenceName = "user_topic_id_seq", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "reputation")
    public Float getReputation() {
        return reputation;
    }

    public void setReputation(Float reputation) {
        this.reputation = reputation;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "topic_id", referencedColumnName = "id")
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
