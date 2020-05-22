package fapi.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String login;
    private String password;
    private String userName;
    private String fullName;
    private UserRole userRole;
    private UserStatus userStatus;

    public User(){

    }
    public User(Long id){
        this.id = id;
    }


    public User(String login, String password, String userName, String fullName) {
        this.login = login;
        this.password = password;
        this.userName = userName;
        this.fullName = fullName;
    }


}

