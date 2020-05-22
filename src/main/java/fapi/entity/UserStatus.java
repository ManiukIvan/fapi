package fapi.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStatus {
    private int id;
    private String status;

    public UserStatus() {
    }

    public UserStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(
                "UserStatus[id=%d, status='%s']",
                id, status);
    }
}
