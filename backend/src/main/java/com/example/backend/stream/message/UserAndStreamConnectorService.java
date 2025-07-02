package com.example.backend.stream.message;

import com.example.backend.stream.exception.NoUserFound;
import com.example.backend.user.model.MyUser;
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

    public static void  updateUserPedroCoinValueByStreamid(Long userId,int value){
        MyUser myUser = myUserRepository.findById(userId).orElse(null);
        myUser.setPedroCoin(myUser.getPedroCoin()+value);
        myUserRepository.save(myUser);
    }

    public static void updateUserStatus(Long userid,boolean status){
        MyUser myUser = myUserRepository.findById(userid).orElse(null);
        if(myUser==null){
            throw  new NoUserFound(userid);
        }
        myUser.setStatus(status);
        myUserRepository.save(myUser);
    }



}
