package com.onlinebank.user;

import com.onlinebank.user.exceptions.UserEditingFailedException;
import com.onlinebank.user.exceptions.UserNotFoundException;
import com.onlinebank.user.exceptions.UserRegistrationFailedException;
import com.onlinebank.utils.Utils;
import com.onlinebank.utils.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p0wontnx on 1/21/16.
 */
@Service
class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        Assert.notNull(repository);
        this.repository = repository;
    }

    @Override
    public List<User> findAll() {
        // retrieve all users
        List<User> users = repository.findAll();
        return users;
    }

    @Override
    public User find(long id) throws UserNotFoundException {
        // retrieve user by id
        User user = repository.findOne(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User findOneByUsername(String username) throws UserNotFoundException {
        User user = repository.findOneByMail(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    public User authenticatedUser() throws UserNotFoundException {
        User user = null;

        org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // retrieve user info
        user = findOneByUsername(authUser.getUsername());

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = null;

        try {
            user = findOneByUsername(username);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        if (user != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(), authorities);
        }
        throw new UsernameNotFoundException("User '" + username + "' not found.");

    }

    @Override
    public User register(User user) throws BadRequestException,
            UserRegistrationFailedException {

        // verify not null attributes
        if (Utils.isModelFieldNull(user)) {
            throw new BadRequestException(user);
        }
        // set user id to null
        user.setUserId(null);
        // hash user password
        user.setPassword(Utils.encryptPassword(user.getPassword()));

        try {
            // save user
            user = repository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserRegistrationFailedException();
        }
        // verify if user was added successfully
        if (user.getUserId() != null) {
            return user;
        }
        throw new UserRegistrationFailedException();
    }

    @Override
    public User edit(long id, User user) throws UserNotFoundException, UserEditingFailedException {

        User oldUser = repository.findOne(id);

        if (oldUser == null) {
            throw new UserNotFoundException();
        }

        if (user.getFirstName() != null) {
            oldUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            oldUser.setLastName(user.getLastName());
        }
        if (user.getMail() != null) {
            oldUser.setMail(user.getMail());
        }
        if (user.getAddress() != null) {
            oldUser.setAddress(user.getAddress());
        }
        if (user.getCountry() != null) {
            oldUser.setCountry(user.getCountry());
        }
        if (user.getState() != null) {
            oldUser.setState(user.getState());
        }
        if (user.getCin() != null) {
            oldUser.setCin(user.getCin());
        }
        if (user.getAvatar() != null) {
            oldUser.setAvatar(user.getAvatar());
        }
        if (user.getZip() != null) {
            oldUser.setZip(user.getZip());
        }

        try {
            // edit user
            oldUser = repository.save(oldUser);
        } catch (DataIntegrityViolationException e) {
            throw new UserEditingFailedException();
        }

        return oldUser;
    }

    @Override
    public void remove(long id) throws UserNotFoundException {
        // retrieve user by id
        User user = repository.findOne(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        repository.delete(user);
    }
}
