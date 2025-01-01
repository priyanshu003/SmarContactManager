package com.scm.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.models.User;
import com.scm.repository.UserRepository;
import com.scm.helper.ResourceNotFound;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User registerUser(User user) {   
        logger.info(user.toString() + "entering function registerUser of type void");
        User usr = null;
        if (!(userRepository.findUserByEmail(user.getEmail()).isEmpty())) {
            logger.info("not save user already exist"+ userRepository.findUserByEmail(user.getEmail()).toString());
        } else {
            logger.info(user.toString() + "user saved to db");
            usr = userRepository.save(user);
        }
        return usr;
    }

    public Optional<User> updateUser(User user){
        User user2 = userRepository.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFound("User not exists"));
        user2.setName(user.getName());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setEmail(user.getEmail());
        user2.setAbout(user.getAbout());
        user2.setPassword(user.getPassword());
        user2.setProfilePic(user.getProfilePic());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());
        user.setEnabled(user.isEnabled());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setEmailVerified(user.isEmailVerified());
        User save = userRepository.save(user2);

        return Optional.ofNullable(save);

    }

    public Optional<User> findUserByEmail(String email) {
        System.err.println(userRepository.findUserByEmail(email));
        return userRepository.findUserByEmail(email);
    }

    public boolean isUserExist(String id) {
        User user2 = userRepository.findById(id).orElse(null);
        return user2 != null ? true:false;
    }

    public void deleteUserById(String id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFound("User not exists"));
        logger.info(user.toString() + "user deleted");
        userRepository.delete(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
