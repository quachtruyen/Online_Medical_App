package com.mycompany.myapp.service;

public class EmailAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException() {
        super("Email này đã được đăng ký cho tài khoản khác!!");
    }
}
