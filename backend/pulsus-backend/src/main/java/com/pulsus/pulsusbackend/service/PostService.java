package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    PostDto getPost(Long userId, Long workoutId);

    List<Long> getPosts(Long userId, Integer page, Integer size);

    List<Long> getPostsByUserId(Long userId, Long postsUserId, Integer page, Integer size);

    List<Long> getFeed(Long userId, Integer page, Integer size);
}
