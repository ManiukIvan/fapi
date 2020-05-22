package fapi.service;

import fapi.entity.Post;
import fapi.entity.PostFront;
import org.springframework.stereotype.Service;


@Service
public interface PostService {
    PostFront getPost(Long postId);
    int getPostsAmount(Long userId);


    Long addPost(PostFront postFront);

    PostFront[] getPostsWithHashtag(String hashtagText);
}
