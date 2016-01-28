package com.onlinebank.user.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.onlinebank.Utils;
import com.onlinebank.user.User;
import com.onlinebank.user.UserService;
import com.onlinebank.user.exceptions.BadRequestException;
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
    public ResponseEntity<List<User>> listAllUsers() {

        // retrieve user list
        List<User> users = userService.findAll();

        return new ResponseEntity<List<User>>(users, HttpStatus.OK);

    }

    @RequestMapping(path = {"/{id}", "/{id}/"}, method = RequestMethod.GET)
    public ResponseEntity<ObjectNode> listUserInfos(@PathVariable("id") long id) {

        // constructing response node
        ObjectMapper objectNode = new ObjectMapper();

        ObjectNode responseNode = objectNode.createObjectNode();

        JsonNode userNode = null;

        User user = null;

        try {
            // retrieve user info
            user = userService.find(id);
            userNode = objectNode.valueToTree(user);
            responseNode.putPOJO("user", userNode);
        } catch (UserNotFoundException e) {
            responseNode.putPOJO("errors", new String[]{"User not found"});
            return new ResponseEntity<ObjectNode>(responseNode, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ObjectNode>(responseNode, HttpStatus.OK);

    }

    @RequestMapping(path = "/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> registerUser(User user) {

        // constructing response node
        ObjectMapper objectNode = new ObjectMapper();

        ObjectNode responseNode = objectNode.createObjectNode();

        JsonNode userNode = null;

        try {
            // register user
            user = userService.register(user);
            userNode = objectNode.valueToTree(user);
            responseNode.putPOJO("user", userNode);
        } catch (BadRequestException e) {
            responseNode.putPOJO("errors", Utils.getModelNullFields(user));
            return new ResponseEntity<ObjectNode>(responseNode, HttpStatus.BAD_REQUEST);
        } catch (UserRegistrationFailedException e) {
            responseNode.putPOJO("errors", new String[]{"User registration failed"});
            return new ResponseEntity<ObjectNode>(responseNode, HttpStatus.PRECONDITION_FAILED);
        }
        // if success
        return new ResponseEntity<ObjectNode>(responseNode, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/edit",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> editUser(@PathVariable("id") long id, User user) {

        // constructing response node
        ObjectMapper objectNode = new ObjectMapper();

        ObjectNode responseNode = objectNode.createObjectNode();

        JsonNode userNode = null;

        // edit user
        try {
            user = userService.edit(id, user);
            userNode = objectNode.valueToTree(user);
            responseNode.putPOJO("user", userNode);
        } catch (UserNotFoundException e) {
            responseNode.putPOJO("errors", new String[]{"User not found"});
            return new ResponseEntity<ObjectNode>(responseNode, HttpStatus.NOT_FOUND);
        } catch (UserEditingFailedException e) {
            responseNode.putPOJO("errors", new String[]{"User editing failed"});
            return new ResponseEntity<ObjectNode>(responseNode, HttpStatus.PRECONDITION_FAILED);
        }
        // if success
        return new ResponseEntity<ObjectNode>(responseNode, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/remove",
            method = RequestMethod.GET)
    public ResponseEntity removeUser(@PathVariable("id") long id) {

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
