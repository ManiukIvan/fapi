package fapi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
public class Like {
    private Long id;

    private Date date;

    private User user;

    private Post post;

    public Like(){

    }

    public Like(Date date) {
        this.date = date;
    }

    public Like(LikeFront like) {
        this.date = like.getDate();
        this.user = new User(like.getIdUser());
        this.post = new Post(like.getIdPost());
    }
}
