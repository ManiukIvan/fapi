package fapi.controllers;


import fapi.entity.PostFront;
import fapi.service.ImageServiceImp;
import fapi.service.PostServiceImp;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping("/api")
@RestController
public class PostController {
    @Autowired
    private PostServiceImp postServiceImp;
    @Autowired
    private ImageServiceImp imageServiceImp;

    @PostMapping("/posts/add")
    public Pair<Long,String> addPost(@RequestPart("images") MultipartFile[] images, @RequestPart("post") PostFront post) {
        Long postId = postServiceImp.addPost(post);
        Long userId = post.getOwnerUser().getId();
        return imageServiceImp.addPostImages(images,userId,postId);
    }

    @GetMapping("/posts/{postId}")
    public PostFront getPost(@PathVariable Long postId) {
        return postServiceImp.getPost(postId);
    }

    @GetMapping("/posts/amount/{userId}")
    public int getPostsAmount(@PathVariable Long userId) {
        return postServiceImp.getPostsAmount(userId);
    }


}
