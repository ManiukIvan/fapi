package fapi.service;

import fapi.entity.User;
import fapi.entity.UserFront;

public interface UserService {

    UserFront registerUser(User user);
    UserFront loginUser(User user);
    UserFront getUserByUserName(String userName);

    User getUserByLogin(String login);
}
