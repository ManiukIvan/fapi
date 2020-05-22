package fapi.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class PostFront {
    private Long id;
    private String description;
    private Date date;
    private UserFront ownerUser;

    public PostFront(){

    }

    public PostFront(Post post) {
        this.id = post.getId();
        this.description = post.getDescription();
        this.date = post.getDate();
        this.ownerUser = new UserFront(post.getUser());
    }
}
