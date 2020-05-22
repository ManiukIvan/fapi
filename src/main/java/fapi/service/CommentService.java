package fapi.service;

import fapi.entity.Comment;
import fapi.entity.CommentFront;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommentFront[] getCommentsOfPost(Long postId);
    CommentFront addComment(CommentFront comment);
}
