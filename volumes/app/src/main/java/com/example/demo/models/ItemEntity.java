package com.example.demo.models;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "item")
@Data
public class ItemEntity implements Serializable {
	private static final long serialVersionUID = -6647247658748349084L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    private String name;
    private String price;
    private String content;
    private boolean favorite;
	
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
	
    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

	
    
//    public ItemEntity findById(Long id) {
//        // 1行でRepositoryから取得して、いなければエラーを投げる
//        return itemRepository.findById(id).orElseThrow(() -> new RuntimeException("見つかりません"));
//    }

}
