package fapi.service;


import fapi.constants.URL;
import fapi.entity.Post;
import fapi.entity.PostFront;
import fapi.exception.ResponseErrorHandlerIml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class PostServiceImp implements PostService {
    @Autowired
    private HashTagServiceImp hashTagServiceImp;

    private RestTemplate restTemplate;

    public PostServiceImp(){
        RestTemplateBuilder builder = new RestTemplateBuilder();
        restTemplate = builder.errorHandler(new ResponseErrorHandlerIml()).build();;
    }

    @Override
    public PostFront getPost(Long postId) {
        ResponseEntity<Post> response =
                restTemplate.getForEntity(
                        URL.POSTS+postId,
                        Post.class);
        Post post = response.getBody();
        String descriptionWithTags = hashTagServiceImp.getTextWithHashTags(post.getDescription());
        post.setDescription(descriptionWithTags);
        return new PostFront(post);
    }


    @Override
    public PostFront[] getPostsWithHashtag(String hashtagText) {
        ResponseEntity<Post[]> response =
                restTemplate.getForEntity(
                        URL.POSTS+"hashtags/"+hashtagText,
                        Post[].class);
        Post[] posts = response.getBody();
        PostFront[] postsFront =new PostFront[posts.length];
        for(int i=0; i < postsFront.length; i++){
            postsFront[i] = new PostFront(posts[i]);
        }
        return postsFront;
    }


    public PostFront[] getPostsLimit(int start,int amount) {
        ResponseEntity<Post[]> response =
                restTemplate.getForEntity(
                        URL.POSTS+"limit/"+start+"/"+amount,
                        Post[].class);
        Post[] posts = response.getBody();
        PostFront[] postsFront =new PostFront[posts.length];
        for(int i=0; i < postsFront.length; i++){
            postsFront[i] = new PostFront(posts[i]);
        }
        return postsFront;
    }


    @Override
    public Long addPost(PostFront postFront) {
        Post post = new Post(postFront);
        ResponseEntity<Long> response =
                restTemplate.postForEntity(
                        URL.POSTS+"add",
                        post,
                        Long.class);
        Long  postId = response.getBody();
        return postId;
    }

    @Override
    public int getPostsAmount(Long userId){
        ResponseEntity<Integer> response =
            restTemplate.getForEntity(
                    URL.POSTS_AMOUNT+userId,
                    Integer.class);
        return response.getBody();
    }
}
