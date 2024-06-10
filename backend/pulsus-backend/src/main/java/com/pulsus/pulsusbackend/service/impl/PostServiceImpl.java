package com.pulsus.pulsusbackend.service.impl;

import com.pulsus.pulsusbackend.dto.PostDto;
import com.pulsus.pulsusbackend.dto.UserInfoDto;
import com.pulsus.pulsusbackend.dto.WorkoutLikeDto;
import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.entity.Workout;
import com.pulsus.pulsusbackend.entity.WorkoutSummary;
import com.pulsus.pulsusbackend.exception.NotFoundException;
import com.pulsus.pulsusbackend.exception.UnauthorizedException;
import com.pulsus.pulsusbackend.mapper.PostMapper;
import com.pulsus.pulsusbackend.repository.WorkoutRepository;
import com.pulsus.pulsusbackend.repository.WorkoutSummaryRepository;
import com.pulsus.pulsusbackend.service.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private UserService userService;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private WorkoutLikeService workoutLikeService;

    @Autowired
    private WorkoutRepository workoutRepository;


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

    @Override
    @Transactional()
    public List<Long> getPosts(Long userId, Integer page, Integer size) {
        List<Long> allPosts = workoutRepository.getWorkoutsForPosts(userId.toString(), page, size);

        return allPosts;
    }

    @Override
    @Transactional()
    public List<Long> getPostsByUserId(Long userId, Long postsUserId, Integer page, Integer size) {
        //Pageable pageable = PageRequest.of(page, size, Sort.by("startTime").descending());

        List<Long> allPosts = workoutRepository.getWorkoutsForPosts(postsUserId.toString(), page, size);

        List<Long> posts = new ArrayList<>();

        for(Long post: allPosts) {
            Optional<Workout> optionalWorkout = workoutService.findById(post);
            if (optionalWorkout.isPresent()) {
                Workout workout = optionalWorkout.get();
                if (workoutService.checkAccess(userId, workout)) {
                    posts.add(post);
                }
            } else {
                continue;
            }
        }

        return posts;
    }

    @Override
    @Transactional()
    public List<Long> getFeed(Long userId, Integer page, Integer size) {

        List<UserInfoDto> followingList = subscriptionService.getFollowing(userId);

        String followedUserId = userId.toString();
        int i = 0;
        for(UserInfoDto user: followingList) {
            i++;
            followedUserId = followedUserId + "," + user.getId();
        }

        if(i == 0) { // если пользователь ни на кого не подписан нет смысла проверять на access к его же тренировкам, для этого есть просто getPosts()
            List<Long> allPosts = getPosts(userId, page, size);
            return  allPosts;
        }

        List<Long> allPosts = workoutRepository.getWorkoutsForPosts(followedUserId, page, size);

        List<Long> posts = new ArrayList<>();

        for(Long post: allPosts) {
            Optional<Workout> optionalWorkout = workoutService.findById(post);
            if (optionalWorkout.isPresent()) {
                Workout workout = optionalWorkout.get();
                if (workoutService.checkAccess(userId, workout)) {
                    posts.add(post);
                }
            } else {
                continue;
            }
        }

        return posts;
    }
}
