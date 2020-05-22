package fapi.entity;

import fapi.constants.URL;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class UserFront {
    private Long id;
    private String userName;
    private String fullName;
    private UserRole userRole;
    private String avatarImageURL;

    public UserFront(){

    }

    public UserFront(String userName, String fullName) {
        this.userName = userName;
        this.fullName = fullName;
    }
    public UserFront(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.fullName = user.getFullName();
        this.userRole = user.getUserRole();
        this.avatarImageURL = URL.IMAGES+user.getId();
    }


}

