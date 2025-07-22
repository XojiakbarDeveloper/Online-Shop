package uz.onlineshop.authservice.mapper;


import uz.onlineshop.authservice.entity.User;
import uz.onlineshop.authservice.res.LoginResponse;

import java.util.Collections;

public interface UserMapper {

    static LoginResponse toLoginResponse(User user, String token) {
        return LoginResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .roles(Collections.singleton(user.getRole()))
                .token(token)
                .build();
    }


}
