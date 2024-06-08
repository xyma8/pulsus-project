package com.pulsus.pulsusbackend.service.impl;

import com.pulsus.pulsusbackend.dto.WorkoutLikeDto;
import com.pulsus.pulsusbackend.entity.Subscription;
import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.entity.WorkoutLike;
import com.pulsus.pulsusbackend.exception.NotFoundException;
import com.pulsus.pulsusbackend.exception.UnauthorizedException;
import com.pulsus.pulsusbackend.repository.SubscriptionRepository;
import com.pulsus.pulsusbackend.repository.WorkoutLikeRepository;
import com.pulsus.pulsusbackend.service.UserService;
import com.pulsus.pulsusbackend.service.WorkoutLikeService;
import com.pulsus.pulsusbackend.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.pulsus.pulsusbackend.entity.Workout;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkoutLikeServiceImpl implements WorkoutLikeService {

    @Autowired
    private WorkoutLikeRepository workoutLikeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkoutService workoutService;


    @Override
    public WorkoutLikeDto checkLike(Long userId, Long workoutId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Login error"));

        Workout workout = workoutService.findById(workoutId)
                .orElseThrow(() ->  new NotFoundException("This workout does not exists"));

        if(!workoutService.checkAccess(userId, workout)) {
            throw new NotFoundException("This workout does not exists");
        }

        WorkoutLike like = workoutLikeRepository.findByUserAndWorkout(user, workout);

        Long countLikes = workoutLikeRepository.countByWorkout(workout);
        if(countLikes == null) countLikes = 0L;

        if(like != null){
            return new WorkoutLikeDto(true, countLikes);
        } else {
            return new WorkoutLikeDto(false, countLikes);
        }
    }

    @Override
    public WorkoutLikeDto changeLike(Long userId, Long workoutId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Login error"));

        Workout workout = workoutService.findById(workoutId)
                .orElseThrow(() ->  new NotFoundException("This workout does not exists"));

        if(!workoutService.checkAccess(userId, workout)) {
            throw new NotFoundException("This workout does not exists");
        }

        WorkoutLike like = workoutLikeRepository.findByUserAndWorkout(user, workout);

        Long countLikes;

        if(like != null) { // если лайк уже поставлен -> удаляем запись с лайком
            workoutLikeRepository.delete(like);
            countLikes = workoutLikeRepository.countByWorkout(workout);
            return new WorkoutLikeDto(false, countLikes);
        } else {
            WorkoutLike newLike = new WorkoutLike();
            newLike.setUser(user);
            newLike.setWorkout(workout);
            workoutLikeRepository.save(newLike);
            countLikes = workoutLikeRepository.countByWorkout(workout);
            return new WorkoutLikeDto(true, countLikes);
        }
    }

    @Override
    public Long getLikesCount(Long userId, Long workoutId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Login error")); // не нужно?

        Workout workout = workoutService.findById(workoutId)
                .orElseThrow(() ->  new NotFoundException("This workout does not exists"));

        if(!workoutService.checkAccess(userId, workout)) {
            throw new NotFoundException("This workout does not exists");
        }

        Long countLikes = workoutLikeRepository.countByWorkout(workout);
        if(countLikes == null) countLikes = 0L;
        return countLikes;
    }

}
