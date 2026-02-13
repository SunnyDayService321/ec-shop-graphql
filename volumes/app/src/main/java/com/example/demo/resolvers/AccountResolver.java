package com.example.demo.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.example.demo.dto.Payload;
import com.example.demo.models.UserEntity;
import com.example.demo.services.AccountService;


@Controller
public class AccountResolver {
	
	@Autowired
    private AccountService accountService;

    /**
     * GQL-AUTH-01: login Mutation の実装
     */
    @MutationMapping
    public Payload login(@Argument String email, @Argument String password) {
        // サービスを呼び出して認証
        UserEntity user = accountService.authenticate(email, password);
        
        if (user == null) {
            // 認証失敗時はエラーを投げるか null を返す
            throw new RuntimeException("UNAUTHENTICATED");
        }

        // 本来はここでJWTトークンを生成
        String token = "sample-jwt-token-for-" + user.getEmail();
        
        Payload payload = new Payload();
        payload.setToken(token);
        payload.setUser(user);
        return payload;
    }

}
