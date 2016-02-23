package com.onlinebank.user.web;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by p0wontnx on 2/19/16.
 */
@Controller
public class UserWebController implements ErrorController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String welcome() {
        return "index_view";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/error")
    public String error() {
        return "404";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
