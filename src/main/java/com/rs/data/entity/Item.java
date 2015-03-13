package com.rs.data.entity;

import javax.persistence.*;

/**
 * Created by leo on 15/3/13.
 */
@Entity
@Table(name = "item", schema = "public")
public class Item implements java.io.Serializable {

    private static final long serialVersionUID = -961140091096883208L;

    private Long id;
    private Float score;
    private Float e_score;
    private String name;

    public Item() {
    }

    public Item(String name) {
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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "item_id_seq")
    @SequenceGenerator(name = "item_id_seq", sequenceName = "item_id_seq", allocationSize = 1)
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

    @Column(name = "e_score")
    public Float getE_score() {
        return e_score;
    }

    public void setE_score(Float e_score) {
        this.e_score = e_score;
    }

}