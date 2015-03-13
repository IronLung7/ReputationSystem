package com.rs.data.entity;

import javax.persistence.*;

/**
 * Created by leo on 15/3/13.
 */
@Entity
@Table(name = "rating", schema = "public")
public class Rating implements java.io.Serializable {
    private Long id;
    private Float score;
    private User user;
    private Item item;

    public Rating() {
    }
    
    public Rating(User user, Item item, Float score) {
        this.user = user;
        this.item = item;
        this.score = score;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "rating_id_seq")
    @SequenceGenerator(name = "rating_id_seq", sequenceName = "rating_id_seq", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "score")
    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
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
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
