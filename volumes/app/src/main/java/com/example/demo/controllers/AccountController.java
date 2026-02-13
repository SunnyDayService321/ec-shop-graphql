package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {
	/**
     * U-AUTH-01: ログイン画面を表示
     * 対応ファイル: templates/account/login.html
     */
    @GetMapping("/account/login")
    public String loginPage() {
        return "account/login";
    }

    /**
     * U-AUTH-04: プロフィール画面を表示
     * 対応ファイル: templates/account/profile.html
     */
    @GetMapping("/Account/profile")
    public String profilePage() {
        return "account/profile";
    }
	

}
