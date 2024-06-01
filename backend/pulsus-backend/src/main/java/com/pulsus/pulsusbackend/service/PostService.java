package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.dto.PostDto;
import org.springframework.stereotype.Service;

@Service
public interface PostService {

    PostDto getPost(Long userId, Long workoutId);
}
