package ru.ecom4u.web.domain.db.entities;

import javax.persistence.*;

/**
 * Created by Evgeny on 20.04.14.
 */
@Entity
@Table(name = "slider_pictures")
public class SliderPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "picture_id")
    private Picture picture;

    private String href;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
