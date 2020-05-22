package fapi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
public class UserRole {
    private int id;
    private String role;

    public UserRole() {
        super();
        // TODO Auto-generated constructor stub
    }

    public UserRole(int id, String status) {
        super();
        this.id = id;
        this.role = status;
    }


    @Override
    public String toString() {
        return String.format(
                "UserRole[id=%d, role='%s']",
                id, role);
    }
}
