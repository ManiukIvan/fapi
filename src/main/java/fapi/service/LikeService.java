package fapi.service;

import fapi.entity.LikeFront;
import org.springframework.stereotype.Service;

@Service
public interface LikeService {
    int getLikesAmountByPostId(Long postId);

    LikeFront getLike(Long postId, Long userId);

    int addLike(LikeFront like);

    int removeLike(Long postId, Long userId);
}
