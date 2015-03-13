package com.rs.data.entity;

import javax.persistence.*;

/**
 * Created by leo on 15/3/13.
 */
@Entity
@Table(name = "item_topic", schema = "public")
public class ItemTopic implements java.io.Serializable  {
    private Long id;
    private Float level;
    private Item item;
    private Topic topic;

    public ItemTopic() {
    }

    public ItemTopic(Float level, Item item, Topic topic) {
        this.level = level;
        this.item = item;
        this.topic = topic;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "item_topic_id_seq")
    @SequenceGenerator(name = "item_topic_id_seq", sequenceName = "item_topic_id_seq", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "level")
    public Float getLevel() {
        return level;
    }

    public void setLevel(Float level) {
        this.level = level;
    }

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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
