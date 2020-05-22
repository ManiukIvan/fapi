package fapi.service;

import fapi.entity.PostFront;
import javafx.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ImageService {

    Pair<MediaType,byte[]> getProfileImage(Long userId) throws IOException;
    String changeProfileImage(MultipartFile file, Long userId) throws IOException;

    String[] getPostImagesURLs(Long userId,Long postId);
    List<Pair<Long, String>> getUserPostsImages(Long userId);

    Pair<MediaType,byte[]> getPostImage(Long userId,Long postId,String imgName) throws IOException;
    Pair<MediaType,byte[]> getFirstPostImage(Long userId, Long postId) throws IOException;

    Pair<Long,String> addPostImages(MultipartFile[] images, Long userId, Long postId);

    List<Pair<Long, String>> getAllPostsImages();

    List<Pair<Long, String>> getPostsImages(PostFront[] posts);
}
