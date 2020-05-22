package fapi.service;

import fapi.constants.URL;
import fapi.entity.Like;
import fapi.entity.LikeFront;
import fapi.exception.ApiRequestException;
import fapi.exception.ResponseErrorHandlerIml;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LikeServiceImp implements LikeService {
    private RestTemplate restTemplate;

    public LikeServiceImp(){
        RestTemplateBuilder builder = new RestTemplateBuilder();
        restTemplate = builder.errorHandler(new ResponseErrorHandlerIml()).build();;
    }
    @Override
    public int getLikesAmountByPostId(Long postId){
        ResponseEntity<Integer> response =
                restTemplate.getForEntity(
                        URL.LIKES+postId,
                        Integer.class);
        return response.getBody();
    }

    @Override
    public LikeFront getLike(Long postId, Long userId) {
        ResponseEntity<Like> response =
            restTemplate.getForEntity(
                    URL.LIKES+postId+"/"+userId,
                    Like.class);
        if (response.getBody() != null) {
            return new LikeFront(response.getBody());
        }
        else {
            return null;
        }
    }

    @Override
    public int addLike(LikeFront like) {
        ResponseEntity<Integer> response =
                restTemplate.postForEntity(
                        URL.LIKES+"set",
                        new Like(like),
                        Integer.class,"contentType: 'application/json; charset=utf-8'");
        return response.getBody();
    }

    @Override
    public int removeLike(Long postId, Long userId) {
        ResponseEntity<Integer> response =
            restTemplate.postForEntity(
                    URL.LIKES+"unset/"+postId,
                    userId,
                    Integer.class);
        return response.getBody();
    }
}
