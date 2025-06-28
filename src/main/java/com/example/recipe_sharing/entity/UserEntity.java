package com.example.recipe_sharing.entity;

import com.example.recipe_sharing.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String username;
    String password;
    String name;
    String email;

    @Enumerated(EnumType.STRING)
    Role role;

    LocalDateTime createdAt;
    LocalDateTime lastPasswordChangeAt;
}
