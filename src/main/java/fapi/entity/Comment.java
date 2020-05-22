package fapi.entity;


import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Comment {
    private Long id;

    private String text;

    private Date date;

    private User user;

    private Post post;

    public Comment(){

    }
    public Comment(String text, Date date) {
        this.text = text;
        this.date = date;
    }
    public Comment(CommentFront commentFront) {
        this.id = commentFront.getId();
        this.text = commentFront.getText();
        this.date = commentFront.getDate();
        this.user = new User(commentFront.getOwnerId());
        this.post = new Post(commentFront.getPostId());
    }
}
