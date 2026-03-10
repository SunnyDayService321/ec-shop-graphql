package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.models.UserEntity;
import com.example.demo.repositries.UserRepository;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class AccountService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder; // BCrypt用

    /**
     * ログイン認証ロジック (U-AUTH-02)
     */
	public UserEntity login(String email, String password) {
	    System.out.println("--- ログインデバッグ開始 ---");
	    System.out.println("入力されたEmail: [" + email + "]");
	    
	    Optional<UserEntity> userOpt = userRepository.findByEmail(email);
	    
	    if (userOpt.isPresent()) {
	        UserEntity user = userOpt.get();
	        System.out.println("DBから見つかったユーザーID: " + user.getId());
	        System.out.println("DB内のハッシュ化パスワード: " + user.getPassword());
	        
	        // 照合テスト
	        try {
	            boolean isMatch = passwordEncoder.matches(password, user.getPassword());
	            System.out.println("パスワード照合結果: " + isMatch);
	            
	            if (isMatch) {
	                return user;
	            } else {
	                System.out.println("★判定: パスワードが一致しません");
	            }
	        } catch (Exception e) {
	            System.out.println("★エラー: 照合中に例外発生: " + e.getMessage());
	        }
	    } else {
	        System.out.println("★判定: このEmailのユーザーはDBに存在しません");
	    }
	    
	    return null;
	}
//    public UserEntity login(String email, String password) {
//        // 1. emailでユーザーを検索
//        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
//        
//        if (userOpt.isPresent()) {
//            UserEntity user = userOpt.get();
//            
//            // 1. 論理削除チェック (deletedAt が NULL でない = 削除済み)
//            if (user.getDeletedAt() != null) {
//                return null; // 退会済みユーザーは認証失敗
//            }
//            // 2. パスワードの照合 (本来はBCrypt等を使用)
////          if (user.getPassword().equals(password)) {
//            if (passwordEncoder.matches(password, user.getPassword())) {
//                return user;
//            }
//        }
//        return null; // 認証失敗
//    }
    /**
     * 新規会員登録ロジック (GQL-AUTH-04)
     */
    @Transactional
    public UserEntity register(String email, String password, String passwordConfirm) {
        // 1. 入力チェック：パスワード一致確認 
        if (!password.equals(passwordConfirm)) {
            throw new RuntimeException("PASSWORD_MISMATCH");
        }

        // 2. メールアドレスの重複チェック 
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("EMAIL_ALREADY_EXISTS");
        }

        // 3. パスワードをBCryptでハッシュ化 
        String hashedPassword = passwordEncoder.encode(password);

        // 4. ユーザー情報の構築と保存 
        UserEntity newUser = new UserEntity();
        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);// ハッシュ化したものをセット
        
        // 最後に一度だけ保存して、登録したユーザー情報を返す
        return userRepository.save(newUser);
    }
	public UserEntity findUserById(Long userId) {
		// リポジトリを使ってIDで検索し、見つからない場合は例外を投げるかnullを返す
	    return userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
	}

}
