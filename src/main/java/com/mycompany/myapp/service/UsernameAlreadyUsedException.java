package com.mycompany.myapp.service;

public class UsernameAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UsernameAlreadyUsedException() {
        super("Tên đăng nhập đã tồn tại!!");
    }
}
