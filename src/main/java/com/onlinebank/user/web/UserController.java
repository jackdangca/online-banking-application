package com.onlinebank.user.web;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.onlinebank.user.User;
import com.onlinebank.user.UserService;
import com.onlinebank.user.exceptions.UserEditingFailedException;
import com.onlinebank.user.exceptions.UserNotFoundException;
import com.onlinebank.user.exceptions.UserRegistrationFailedException;
import com.onlinebank.utils.ResponseBuilder;
import com.onlinebank.utils.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by p0wontnx on 1/21/16.
 */
@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        Assert.notNull(userService);
        this.userService = userService;
    }

    @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
    public ResponseEntity<ObjectNode> listAllUsers() {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        // retrieve user list
        List<User> users = userService.findAll();

        responseBuilder.setResponseResult(users);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = {"/{userId}", "/{userId}/"}, method = RequestMethod.GET)
    public ResponseEntity<ObjectNode> listUserInfos(@PathVariable("userId") long id) throws UserNotFoundException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        User user = null;

        // retrieve user info
        user = userService.find(id);
        responseBuilder.setResponseResult(user);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> registerUser(User user) throws BadRequestException, UserRegistrationFailedException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        // register user
        user = userService.register(user);
        responseBuilder.setResponseResult(user);

        // if success
        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/{userId}/edit",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> editUser(@PathVariable("userId") long id, User user) throws UserNotFoundException, UserEditingFailedException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        // edit user
        user = userService.edit(id, user);
        responseBuilder.setResponseResult(user);

        // if success
        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/{userId}/remove",
            method = RequestMethod.GET)
    public ResponseEntity removeUser(@PathVariable("userId") long id) throws UserNotFoundException {

        // remove user
        userService.remove(id);

        // if success
        return new ResponseEntity(HttpStatus.OK);
        
    }

}
