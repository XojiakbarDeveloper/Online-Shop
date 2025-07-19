package uz.onlineshop.mapper;

import uz.onlineshop.dtoes.req.LoginResponse;
import uz.onlineshop.entity.User;

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
