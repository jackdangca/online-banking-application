package com.onlinebank.user;

import com.onlinebank.user.exceptions.UserEditingFailedException;
import com.onlinebank.user.exceptions.UserNotFoundException;
import com.onlinebank.user.exceptions.UserRegistrationFailedException;
import com.onlinebank.utils.exceptions.BadRequestException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Created by p0wontnx on 1/21/16.
 */
public interface UserService extends UserDetailsService {

    List<User> findAll();

    User find(long id) throws UserNotFoundException;

    User findOneByUsername(String username) throws UserNotFoundException;

    User authenticatedUser() throws UserNotFoundException;

    User register(User user) throws BadRequestException,
            UserRegistrationFailedException;

    User edit(long id, User user) throws UserNotFoundException, UserEditingFailedException;

    void remove(long id) throws UserNotFoundException;

}
