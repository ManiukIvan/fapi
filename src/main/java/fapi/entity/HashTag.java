package fapi.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class HashTag {
    private Long id;

    private String text;

    private Set<Post> accounts = new HashSet<>();

    public HashTag(){

    }

    public HashTag(String text) {
        this.text = text;
    }
}
