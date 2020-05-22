package fapi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;


@Getter
@Setter
public class Report {
    private Long id;

    private String description;

    private Date date;

    private User user;

    private Post post;

    public Report(){

    }

    public Report(String description,Date date) {
        this.description = description;
        this.date = date;
    }
}
