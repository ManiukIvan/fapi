package fapi.controllers;


import fapi.entity.PostFront;
import fapi.service.ImageServiceImp;
import fapi.service.PostServiceImp;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequestMapping("/api")
@RestController
public class ImageController {
    @Autowired
    private ImageServiceImp imageServiceImp;

    @Autowired
    private PostServiceImp postServiceImp;

    @PostMapping("/images/changeProfileImage/{userId}")
    public String changeProfilePhoto(@RequestParam("imageFile") MultipartFile photo, @PathVariable Long userId) {
        return imageServiceImp.changeProfileImage(photo,userId);
    }

    @GetMapping("/images/{userId}")
    public ResponseEntity<byte[]>  getUserImage(@PathVariable Long userId) throws IOException {
        Pair<MediaType,byte[]> image =  imageServiceImp.getProfileImage(userId);
        return ResponseEntity.ok().contentType(image.getKey()).body(image.getValue());
    }

    //Get images with selected name of necessary post
    @GetMapping("/images/{userId}/{postId}/{imgName}")
    public ResponseEntity<byte[]>  getPostImage(@PathVariable Long userId,@PathVariable Long postId,@PathVariable String imgName) throws IOException {
        Pair<MediaType,byte[]> image =  imageServiceImp.getPostImage(userId,postId,imgName);
        return ResponseEntity.ok().contentType(image.getKey()).body(image.getValue());
    }

    //Get first images of necessary post
    @GetMapping("/images/{userId}/{postId}/first")
    public ResponseEntity<byte[]>  getFirstPostImage(@PathVariable Long userId,@PathVariable Long postId) throws IOException {
        Pair<MediaType,byte[]> image =  imageServiceImp.getFirstPostImage(userId,postId);
        return ResponseEntity.ok().contentType(image.getKey()).body(image.getValue());
    }

    //Get URL of images of necessary post
    @GetMapping("/images/{userId}/{postId}/urls")
    public String[]  getURLsOfPostImages(@PathVariable Long userId,@PathVariable Long postId) {
        String[] strings =imageServiceImp.getPostImagesURLs(userId,postId);
        return strings;
    }


    @GetMapping("/images/posts/all")
    public Pair<Long,String>[] getAllPostsImages() {
        List<Pair<Long, String>> img = imageServiceImp.getAllPostsImages();
        //reverse sort by id, bigger id => post is more new
        img.sort(new Comparator<Pair<Long, String>>() {
            @Override
            public int compare(Pair<Long, String> o1, Pair<Long, String> o2) {
                return o2.getKey().compareTo(o1.getKey());
            }
        });
        return img.toArray(new Pair[img.size()]);
    }

    @GetMapping("/images/posts/limit/{start}/{amount}")
    public Pair<Long,String>[] getPostsLimitImages(@PathVariable int start,@PathVariable int amount) {
        PostFront[] posts = postServiceImp.getPostsLimit(start,amount);;
        if (posts != null) {
            List<Pair<Long,String>> imgs =  imageServiceImp.getPostsImages(posts);
            return imgs.toArray(new Pair[imgs.size()]);
        }
        else {
            return null;
        }
    }


    @GetMapping("/images/posts/{userId}")
    public Pair<Long,String>[] getUserPostsImages(@PathVariable Long userId) {
        List<Pair<Long, String>> img =imageServiceImp.getUserPostsImages(userId);
        //reverse sort by id, bigger id => post is more new
        img.sort(new Comparator<Pair<Long, String>>() {
            @Override
            public int compare(Pair<Long, String> o1, Pair<Long, String> o2) {
                return o2.getKey().compareTo(o1.getKey());
            }
        });
        return img.toArray(new Pair[img.size()]);
    }



    @GetMapping("/images/posts/hashtags/{hashtagText}")
    public Pair<Long,String>[] getPostsWithHashtag(@PathVariable String hashtagText) {
        PostFront[] posts=  postServiceImp.getPostsWithHashtag(hashtagText);
        if (posts != null) {
            List<Pair<Long,String>> imgs =  imageServiceImp.getPostsImages(posts);
            return imgs.toArray(new Pair[imgs.size()]);
        }
        else {
            return null;
        }
    }

}
