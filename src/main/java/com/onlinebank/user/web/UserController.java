package com.onlinebank.user.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.onlinebank.utils.ResponseBuilder;
import com.onlinebank.utils.Utils;
import com.onlinebank.user.User;
import com.onlinebank.user.UserService;
import com.onlinebank.utils.exceptions.BadRequestException;
import com.onlinebank.user.exceptions.UserEditingFailedException;
import com.onlinebank.user.exceptions.UserNotFoundException;
import com.onlinebank.user.exceptions.UserRegistrationFailedException;
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
    public ResponseEntity<ObjectNode> listUserInfos(@PathVariable("userId") long id) {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        User user = null;

        try {
            // retrieve user info
            user = userService.find(id);
            responseBuilder.setResponseResult(user);
        } catch (UserNotFoundException e) {
            responseBuilder.setResponseErrors(new String[]{"User not found"});
            return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.NOT_FOUND.getReasonPhrase()).build(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> registerUser(User user) {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        try {
            // register user
            user = userService.register(user);
            responseBuilder.setResponseResult(user);
        } catch (BadRequestException e) {
            responseBuilder.setResponseErrors(Utils.getModelNullFields(user));
            return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.BAD_REQUEST.getReasonPhrase()).build(), HttpStatus.BAD_REQUEST);
        } catch (UserRegistrationFailedException e) {
            responseBuilder.setResponseErrors(new String[]{"User registration failed"});
            return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.PRECONDITION_FAILED.getReasonPhrase()).build(), HttpStatus.PRECONDITION_FAILED);
        }
        // if success
        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);
    }

    @RequestMapping(path = "/{userId}/edit",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> editUser(@PathVariable("userId") long id, User user) {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        // edit user
        try {
            user = userService.edit(id, user);
            responseBuilder.setResponseResult(user);
        } catch (UserNotFoundException e) {
            responseBuilder.setResponseErrors(new String[]{"User not found"});
            return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.NOT_FOUND.getReasonPhrase()).build(), HttpStatus.NOT_FOUND);
        } catch (UserEditingFailedException e) {
            responseBuilder.setResponseErrors(new String[]{"User editing failed"});
            return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.PRECONDITION_FAILED.getReasonPhrase()).build(), HttpStatus.PRECONDITION_FAILED);
        }
        // if success
        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);
    }

    @RequestMapping(path = "/{userId}/remove",
            method = RequestMethod.GET)
    public ResponseEntity removeUser(@PathVariable("userId") long id) {

        // remove user
        try {
            userService.remove(id);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        // if success
        return new ResponseEntity(HttpStatus.OK);
    }

}
