package com.noirix;

import com.noirix.domain.User;
import com.noirix.repository.UserRepository;
import com.noirix.repository.impl.UserRepositoryImpl;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryImpl();
// Find all users
        for (User user : userRepository.findAll()) {
            System.out.println(user);
        }

        //Find one
//        try {
//            System.out.println(userRepository.findOne(100L));
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }

        //update
        User user = userRepository.findOne(2L);
        user.setName("USHER");
        System.out.println(userRepository.update(user).getName());
        userRepository.delete(2L);

        //Check function call
        //System.out.println(userRepository.getUserExpensiveCarPrice(100));
    }
}
