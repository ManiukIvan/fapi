package fapi.service;

import fapi.constants.Address;
import fapi.constants.URL;
import fapi.entity.Post;
import fapi.entity.PostFront;
import fapi.exception.ApiRequestException;
import javafx.util.Pair;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImp implements ImageService {

    @Override
    public Pair<MediaType,byte[]> getProfileImage(Long userId) throws IOException{
        //if avatar is png pictures
        File userDirectory = new File(Address.IMAGES + userId);
        //take only images
        File[] files = userDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                String name = file.getName().toLowerCase();
                return (name.endsWith(".jpg") || name.endsWith(".png")) && file.isFile();
            }
        });
        if (files != null) {
            //there is user avatar
            if (files.length != 0) {
                InputStream in = new FileInputStream(files[0]);
                MediaType imageType = files[0].getName().toLowerCase().endsWith(".png") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
                return new Pair<>(imageType, IOUtils.toByteArray(in));
            }
            //there is no user avatar
            else {
                //=> then send standard avatar logo
                InputStream in = new FileInputStream(new File(Address.IMAGES_STANDARD_AVATAR_JPG));
                return new Pair<>(MediaType.IMAGE_JPEG, IOUtils.toByteArray(in));
            }
        }
        else {
            throw new ApiRequestException("There is no such photo");
        }
    }

    @Override
    public String changeProfileImage(MultipartFile photo, Long userId) {
        try {
            //delete old image
            File userDirectory = new File(Address.IMAGES + userId);
            //take only images
            File[] files = userDirectory.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    String name = file.getName().toLowerCase();
                    return (name.endsWith(".jpg") || name.endsWith(".png")) && file.isFile();
                }
            });
            for (File f : files) {
                Files.deleteIfExists(Paths.get(f.getPath()));
            }


            //add new Image
            Path path = Paths.get(Address.IMAGES + userId + "/" + "avatar.png");
            byte[] bytes = photo.getBytes();
            Files.write(path, bytes);
        }
        catch (IOException e){
            throw new ApiRequestException("Can't save file");
        }

        return "photo is changed successfully";
    }

    @Override
    public String[] getPostImagesURLs(Long userId,Long postId){
        //if avatar is png pictures
        File userDirectory = new File(Address.IMAGES + userId + "\\"+postId);
        //take only images
        File[] files = userDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                String name = file.getName().toLowerCase();
                return (name.endsWith(".jpg") || name.endsWith(".png")) && file.isFile();
            }
        });
        List<String> imgNames = new ArrayList<String>();
        if (files != null && files.length>0) {
            for (File f: files){
                imgNames.add(URL.IMAGES+userId +"/"+postId+"/"+f.getName());
            }
        }
        else {
            throw new ApiRequestException("There is no such post");
        }
        return imgNames.toArray(new String[imgNames.size()]);
    }



    @Override
    public Pair<MediaType,byte[]> getPostImage(Long userId,Long postId,String imgName) throws IOException{
        //if avatar is png pictures
        File userDirectory = new File(Address.IMAGES+ userId+"\\"+ postId);
        //take only images
        File[] files = userDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                String name = file.getName().toLowerCase();
                return (name.endsWith(".jpg") || name.endsWith(".png")) && file.isFile() && name.equals(imgName.toLowerCase());
            }
        });
        if (files != null && files.length>0) {
                InputStream in = new FileInputStream(files[0]);
                MediaType imageType = files[0].getName().toLowerCase().endsWith(".png") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
                return new Pair<>(imageType, IOUtils.toByteArray(in));
        }
        else {
            throw new ApiRequestException("There is no such post image");
        }
    }
    @Override
    public Pair<MediaType,byte[]> getFirstPostImage(Long userId,Long postId) throws IOException{
        //if avatar is png pictures
        File userDirectory = new File(Address.IMAGES+ userId+"\\"+ postId);
        //take only images
        File[] files = userDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                String name = file.getName().toLowerCase();
                return (name.endsWith(".jpg") || name.endsWith(".png")) && file.isFile();
            }
        });
        if (files != null && files.length>0) {
            InputStream in = new FileInputStream(files[0]);
            MediaType imageType = files[0].getName().toLowerCase().endsWith(".png") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
            return new Pair<>(imageType, IOUtils.toByteArray(in));
        }
        else {
            throw new ApiRequestException("There is no such post image");
        }
    }


    @Override
    public Pair<Long,String> addPostImages(MultipartFile[] images, Long userId, Long postId) {
        try {
            //create dir
            File theDir = new File(Address.IMAGES + userId + '/' + postId);
            theDir.mkdir();
            for (MultipartFile image : images) {
                Path path = Paths.get(Address.IMAGES + userId + "/" +postId+"/" + image.getOriginalFilename());
                byte[] bytes = image.getBytes();
                Files.write(path, bytes);
            }
        }
        catch (IOException e){
             throw new ApiRequestException("can't save post images");
        }
        return new Pair<Long,String>(postId,URL.IMAGES+userId +"/"+postId+"/first");
    }

    @Override
    public List<Pair<Long, String>> getAllPostsImages() {
        File userDirectory = new File(Address.IMAGES);
        //take all users directories
        File[] usersDir = userDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file,String name) {
                return new File(file, name).isDirectory();
            }
        });
        List<Pair<Long, String>> list = new ArrayList<>();
        for (File postDir: usersDir){
            try {
                //get first images of every user post
                List<Pair<Long, String>> userImgs = getUserPostsImages(Long.parseLong(postDir.getName()));
                if (userImgs != null && userImgs.size() > 0) {
                    list.addAll(userImgs);
                }
            } catch (NumberFormatException e){
                //user dir name is Long numbers
            }
        }

        return  list;
    }
    @Override
    public List<Pair<Long, String>> getUserPostsImages(Long userId){
        File userDirectory = new File(Address.IMAGES+ userId);
        //take only directories(posts)
        File[] posts = userDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file,String name) {
                return new File(file, name).isDirectory();
            }
        });
        List<Pair<Long,String>> imgNames = new ArrayList<>();
        if (posts != null && posts.length>0) {
            for(File post :posts){
                imgNames.add(new Pair<Long,String>(Long.parseLong(post.getName()),URL.IMAGES+userId +"/"+post.getName()+"/first"));
            }
        }
        else {
            return null;
        }
        return imgNames;
    }


    @Override
    public List<Pair<Long, String>> getPostsImages(PostFront[] posts){
        List<Pair<Long,String>> imgNames = new ArrayList<>();
        for (PostFront postFront: posts) {
            imgNames.add(new Pair<Long, String>(postFront.getId(), URL.IMAGES + postFront.getOwnerUser().getId() + "/" + postFront.getId() + "/first"));

        }
        return imgNames;
    }


}
