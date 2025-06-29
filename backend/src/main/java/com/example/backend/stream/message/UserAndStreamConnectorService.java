package com.example.backend.stream.message;

import com.example.backend.stream.exception.NoUserFound;
import com.example.backend.user.repository.MyUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserAndStreamConnectorService {
    private static MyUserRepository myUserRepository;

    UserAndStreamConnectorService(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }
    public static void isUserInDatabase(Long userid){
        if( myUserRepository.findById(userid).isEmpty()){
            throw  new NoUserFound(userid);
        }
    }



}
