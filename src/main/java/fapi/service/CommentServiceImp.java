package fapi.service;

import fapi.constants.URL;
import fapi.entity.Comment;
import fapi.entity.CommentFront;
import fapi.exception.ResponseErrorHandlerIml;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;



@Service
public class CommentServiceImp implements CommentService {
    private RestTemplate restTemplate;

    public CommentServiceImp(){
        RestTemplateBuilder builder = new RestTemplateBuilder();
        restTemplate = builder.errorHandler(new ResponseErrorHandlerIml()).build();;
    }

    @Override
    public CommentFront[] getCommentsOfPost(Long postId) {
        ResponseEntity<Comment[]> response =
            restTemplate.getForEntity(
                    URL.COMMENTS+postId,
                    Comment[].class);
        Comment[] comments = response.getBody();
        List<CommentFront> commentsFront =new ArrayList<>(comments.length);
        for(int i=0; i < comments.length; i++){
            commentsFront.add(new CommentFront(comments[i]));
        }
        //sort comments by date from lower to bigger
        commentsFront.sort(new Comparator<CommentFront>() {
            @Override
            public int compare(CommentFront o1, CommentFront o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        return commentsFront.toArray(new CommentFront[commentsFront.size()]);
    }

    @Override
    public CommentFront addComment(CommentFront comment) {
        Comment sendingComment = new Comment(comment);
        ResponseEntity<Comment> response =
                restTemplate.postForEntity(
                        URL.COMMENTS+"add",
                        sendingComment,
                        Comment.class);
        CommentFront commentFront = new CommentFront(response.getBody());
        commentFront.setOwnerImgUrl(URL.IMAGES+comment.getOwnerId());
        commentFront.setOwnerUserName(comment.getOwnerUserName());
        return commentFront;
    }
}
