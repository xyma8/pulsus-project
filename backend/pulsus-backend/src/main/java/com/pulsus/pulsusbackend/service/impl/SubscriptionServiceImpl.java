package com.pulsus.pulsusbackend.service.impl;

import com.pulsus.pulsusbackend.dto.UserInfoDto;
import com.pulsus.pulsusbackend.entity.Subscription;
import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.exception.NotFoundException;
import com.pulsus.pulsusbackend.exception.UnauthorizedException;
import com.pulsus.pulsusbackend.mapper.UserInfoDtoMapper;
import com.pulsus.pulsusbackend.repository.SubscriptionRepository;
import com.pulsus.pulsusbackend.service.SubscriptionService;
import com.pulsus.pulsusbackend.service.UserInfoDetails;
import com.pulsus.pulsusbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public boolean changeSubscription(Long userId, Long followedId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Login error"));

        User followed = userService.findById(followedId)
                .orElseThrow(() -> new NotFoundException("Followed user not found"));

        Subscription subscription = subscriptionRepository.findByFollowerAndFollowed(userId, followedId);

        if(subscription != null) { // если подписка уже есть то значит пользователь отписывается -> удаляем запись
            subscriptionRepository.delete(subscription);
            return true;
        } else {
            Subscription newSubscription = new Subscription();
            newSubscription.setFollower(user);
            newSubscription.setFollowed(followed);
            subscriptionRepository.save(newSubscription);
            return true;
        }
    }

    @Override
    public List<UserInfoDto> getFollowers(Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new NotFoundException("User don't exists"));

        List<Subscription> subscriptions = subscriptionRepository.findByFollowed(userId);
        List<UserInfoDto> followers = new ArrayList<>();

        for(Subscription follower: subscriptions) {
            followers.add(UserInfoDtoMapper.mapToUserInfoDto(follower.getFollower()));
        }

        return followers;
    }

    @Override
    public List<UserInfoDto> getFollowing(Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new NotFoundException("User don't exists"));

        List<Subscription> subscriptions = subscriptionRepository.findByFollower(userId);
        List<UserInfoDto> following = new ArrayList<>();

        for(Subscription follower: subscriptions) {
            following.add(UserInfoDtoMapper.mapToUserInfoDto(follower.getFollowed()));
        }

        return following;
    }

    @Override
    public Integer getFollowersCount(Long userId) {
        Integer countFollowers = subscriptionRepository.countByFollower(userId);

        return countFollowers;
    }

    @Override
    public Integer getFollowingCount(Long userId) {
        Integer countFollowing = subscriptionRepository.countByFollowed(userId);

        return countFollowing;
    }

}
