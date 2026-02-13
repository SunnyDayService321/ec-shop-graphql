package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.models.UserEntity;
import com.example.demo.repositries.UserRepository;
import java.util.Optional;

@Service
public class AccountService {
	
	@Autowired
    private UserRepository userRepository;

    /**
     * ログイン認証ロジック (U-AUTH-02)
     */
    public UserEntity authenticate(String email, String password) {
        // 1. emailでユーザーを検索
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            // 2. パスワードの照合 (本来はBCrypt等を使用)
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // 認証失敗
    }

}
