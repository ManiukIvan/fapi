package fapi.entity;


import fapi.constants.URL;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CommentFront {
    private Long id;
    private String text;
    private Date date;
    private Long postId;
    private String ownerUserName;
    private Long ownerId;
    private String ownerImgUrl;

    public CommentFront(){

    }
    public CommentFront(String text, Date date) {
        this.text = text;
        this.date = date;
    }
    public CommentFront(Comment comment){
        this.id = comment.getId();
        this.text = comment.getText();
        this.date = comment.getDate();
        this.ownerUserName = comment.getUser().getUserName();
        this.ownerImgUrl = URL.IMAGES+comment.getUser().getId();;
    }
}
