package com.pulsus.pulsusbackend.service.impl;

import com.pulsus.pulsusbackend.dto.PostDto;
import com.pulsus.pulsusbackend.dto.WorkoutLikeDto;
import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.entity.Workout;
import com.pulsus.pulsusbackend.exception.NotFoundException;
import com.pulsus.pulsusbackend.exception.UnauthorizedException;
import com.pulsus.pulsusbackend.mapper.PostMapper;
import com.pulsus.pulsusbackend.service.PostService;
import com.pulsus.pulsusbackend.service.UserService;
import com.pulsus.pulsusbackend.service.WorkoutLikeService;
import com.pulsus.pulsusbackend.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private UserService userService;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private WorkoutLikeService workoutLikeService;

    @Override
    public PostDto getPost(Long userId, Long workoutId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Login error")); //может не нужно

        Workout workout = workoutService.findById(workoutId)
                .orElseThrow(() -> new NotFoundException("This workout does not exists"));

        if(!workoutService.checkAccess(userId, workout)) {
            throw new NotFoundException("This workout does not exists");
        }

        WorkoutLikeDto workoutLikeDto = workoutLikeService.checkLike(userId, workoutId);

        PostDto postDto = PostMapper.mapToPostDto(workout.getUser(), workout, workout.getSummary(), workoutLikeDto);

        return postDto;
    }
}
