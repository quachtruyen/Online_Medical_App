package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "post_category")
public class PostCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idPost;

    private Long idCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdPost(Long postId) {
        this.idPost = postId;
    }

    public Long getIdPost() {
        return idPost;
    }

    public void setIdCategory(Long categoryId) {
        this.idCategory = categoryId;
    }

    public Long getIdCategory() {
        return idCategory;
    }
}
