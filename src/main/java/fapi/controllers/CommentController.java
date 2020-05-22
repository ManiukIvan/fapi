package fapi.controllers;

import fapi.entity.Comment;
import fapi.entity.CommentFront;
import fapi.service.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/api")
@RestController
public class CommentController {
    @Autowired
    private CommentServiceImp commentServiceImp;


    @GetMapping("/comments/{postId}")
    public CommentFront[] getUserImage(@PathVariable Long postId) {
        return commentServiceImp.getCommentsOfPost(postId);
    }

    @PostMapping("/comments/add")
    public CommentFront addComment(@RequestBody CommentFront comment) {
        return commentServiceImp.addComment(comment);
    }
}

