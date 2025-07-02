package com.example.backend.user.repository;

import com.example.backend.user.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByEmail(String email);
    Optional<MyUser> findByNickname(String nickname);
    List<MyUser> findTop5ByNicknameIgnoreCaseContaining(String nickname);
}
