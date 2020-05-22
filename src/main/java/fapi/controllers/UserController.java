package fapi.controllers;

import fapi.entity.User;
import fapi.entity.UserFront;
import fapi.service.UserServiceImp;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@RequestMapping("/api")
@RestController
public class UserController {
    @Autowired
    private UserServiceImp userServiceImp;

    @PostMapping("/register")
    public UserFront register(@RequestBody User user) {
        return userServiceImp.registerUser(user);
    }

    @PostMapping("/login")
    public UserFront login(@RequestBody User user) {
        return userServiceImp.loginUser(user);
    }

}

