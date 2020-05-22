package fapi.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class LikeFront {
    private Long id;

    private Date date;

    private Long idUser;

    private Long idPost;;

    public LikeFront(){

    }

    public LikeFront(Like like) {
        this.id = like.getId();
        this.date = like.getDate();
        this.idUser = like.getUser().getId();
    }
}
