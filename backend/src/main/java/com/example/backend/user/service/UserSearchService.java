package com.example.backend.user.service;

import com.example.backend.user.dto.SearchResponseDTO;
import com.example.backend.user.repository.MyUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSearchService {
    private final MyUserRepository userRepository;

    public UserSearchService(MyUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<SearchResponseDTO> searchUsers(String query) {
        System.out.println("Searching for users with query: " + query);
        return userRepository.findTop5ByNicknameIgnoreCaseContaining(query)
                .stream()
                .map(user -> new SearchResponseDTO(user.getId(), user.getNickname()))
                .toList();
    }


}
