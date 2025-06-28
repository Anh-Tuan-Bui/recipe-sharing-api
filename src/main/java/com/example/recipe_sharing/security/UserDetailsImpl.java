//package com.example.recipe_sharing.security;
//
//import com.example.recipe_sharing.entity.UserEntity;
//import lombok.Getter;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.List;
//
//@Getter
//public class UserDetailsImpl extends User implements UserDetails {
//    private UserEntity user;
//
//    public UserDetailsImpl(UserEntity user) {
//        super(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().name())));
//        this.user = user;
//    }
//}
