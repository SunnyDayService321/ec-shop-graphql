package com.example.demo.models;

import javax.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users") // MySQLのテーブル名と一致させる
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK: ユーザーID

    @Column(unique = true, nullable = false)
    private String email; // UK: ログインID

    @Column(nullable = false)
    private String password; // ハッシュ化済みパスワード

    private LocalDateTime createdAt; // 登録日時
    
    private LocalDateTime updatedAt; // 更新日時
    
    public String getPassword() {
        return this.password;
    }
    
    public String getEmail() {
        return this.email;
    }

    // 必要に応じて論理削除用フラグを追加
    // private LocalDateTime deletedAt;
}
