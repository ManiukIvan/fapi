package fapi.controllers;



import fapi.entity.Like;
import fapi.entity.LikeFront;
import fapi.service.LikeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api")
@RestController
public class LikeController {
    @Autowired
    private LikeServiceImp likeServiceImpl;

    @GetMapping("/likes/{postId}")
    public int getLikesAmount(@PathVariable Long postId) {
         return likeServiceImpl.getLikesAmountByPostId(postId);
    }


    @GetMapping("/likes/{postId}/{userId}")
    public LikeFront getLike(@PathVariable Long postId, @PathVariable Long userId) {
        return likeServiceImpl.getLike(postId,userId);
    }

    @PostMapping("/likes/set")
    public int addLike( @RequestBody LikeFront like) {
        return likeServiceImpl.addLike(like);
    }

    @PostMapping("/likes/unset/{postId}")
    public int removeLike(@PathVariable Long postId, @RequestBody Long userId) {
        return likeServiceImpl.removeLike(postId,userId);
    }


}
