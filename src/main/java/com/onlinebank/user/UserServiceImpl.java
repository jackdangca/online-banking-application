package com.onlinebank.user;

import com.onlinebank.Utils;
import com.onlinebank.user.exceptions.BadRequestException;
import com.onlinebank.user.exceptions.UserRegistrationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    public User register(User user) throws BadRequestException,
            UserRegistrationFailedException {

        // verify not null attributes
        if (Utils.isModelFieldNull(user)) {
            throw new BadRequestException();
        }
        // hash user password
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] encryptedPassword = md5.digest(user.getPassword().getBytes("UTF-8"));
            user.setPassword((new HexBinaryAdapter()).marshal(encryptedPassword));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }

        try {
            // save user
            user = repository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserRegistrationFailedException();
        }
        // verify if user was added successfully
        if (user.getUser_id() != null) {
            return user;
        }
        throw new UserRegistrationFailedException();

    }
}
