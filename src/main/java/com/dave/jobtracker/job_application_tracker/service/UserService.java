package com.dave.jobtracker.job_application_tracker.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.dave.jobtracker.job_application_tracker.models.User;
import com.dave.jobtracker.job_application_tracker.repository.UserRepository;
import com.dave.jobtracker.job_application_tracker.WebSecurityConfig;

public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public User findUser(String userEmail) {
        return repository.findbyUserEmail(userEmail);
    }

    public Boolean userExists(String userEmail) {
        return repository.existsbyUserEmail(userEmail);
    }


    /*create a user account, must check if username already exists and if so throw exception */ 
    public void registerUser(String userEmail, String password) throws Exception {
        if (this.userExists(userEmail)) {
            throw new Exception("User Exists already");
        } else {
            String encoded = encoder.encode(password);
            User newUser = new User(userEmail, encoded);
            repository.save(newUser);
        }
    }

    /* log in and validate credentials  */
    public User login(String userEmail, String password) throws Exception {

        if (!userExists(userEmail)) {
            throw new Exception("No user associated with that email");
        } else {
            User user = findUser(userEmail); // find user associated with email
            if (!encoder.matches(password, user.getPassword())) {
                throw new Exception("Incorrect Password");
            }
            return user;
        }
    }


    
    
}
