package fapi.service;

import fapi.constants.Address;
import fapi.constants.URL;
import fapi.entity.*;
import fapi.exception.ResponseErrorHandlerIml;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.HashSet;
import java.util.Set;


@Service
public class UserServiceImp implements UserDetailsService, UserService{

    private final RestTemplate restTemplate;

    public UserServiceImp() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        restTemplate = builder.errorHandler(new ResponseErrorHandlerIml()).build();
    }

    @Override
    public UserFront registerUser(User user) {
        //set Default userRole and status
        user.setUserRole(new UserRole(2,"user"));
        user.setUserStatus(new UserStatus(1,"active"));
        ResponseEntity<User> response =
                restTemplate.postForEntity(
                        URL.REGISTER,
                        user,
                        User.class);
        //there is no errors user was register
        //create new user dir
        User registerUser = response.getBody();
        createUserDir(registerUser.getId());
        return  new UserFront(registerUser);
    }

    @Override
    public UserFront loginUser(User user) {
        ResponseEntity<User> response =
                restTemplate.postForEntity(
                        URL.LOGIN,
                        user,
                        User.class);
        User loginUser = response.getBody();
        return new UserFront(loginUser);
    }

    @Override
    public UserFront getUserByUserName(String userName) {
        ResponseEntity<User> response =
                restTemplate.getForEntity(
                        URL.USERS+userName,
                        User.class);
        User userFromBackEnd = response.getBody();
        return new UserFront(userFromBackEnd);
    }

    @Override
    public User getUserByLogin(String login) {
        ResponseEntity<User> response =
            restTemplate.getForEntity(
                    URL.USERS+"login/"+login,
                    User.class);
        return response.getBody();
    }

    public void createUserDir(Long userId){
        File theDir = new File(Address.IMAGES+userId);
        theDir.mkdir();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserRole()));
        return authorities;
    }
}
