package fapi.entity;

import lombok.Getter;
import lombok.Setter;


import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class Post {
    private Long id;

    private String description;

    private Date date;

    private User user;


    Set<HashTag> hashtags = new HashSet<>();
    public Post(){

    }

    public Post(Long id){
        this.id = id;
    }

    public Post(String description, Date date) {
        this.description = description;
        this.date = date;
    }

    public Post(PostFront postFront) {
        this.description = postFront.getDescription();
        this.date = postFront.getDate();
        this.user = new User(postFront.getOwnerUser().getId());
    }


}
