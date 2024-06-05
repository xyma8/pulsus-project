package com.pulsus.pulsusbackend.service.impl;

import com.pulsus.pulsusbackend.dto.SubscriptionCountDto;
import com.pulsus.pulsusbackend.dto.UserInfoDto;
import com.pulsus.pulsusbackend.entity.Subscription;
import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.exception.NotFoundException;
import com.pulsus.pulsusbackend.exception.UnauthorizedException;
import com.pulsus.pulsusbackend.mapper.UserInfoDtoMapper;
import com.pulsus.pulsusbackend.repository.SubscriptionRepository;
import com.pulsus.pulsusbackend.service.SubscriptionService;
import com.pulsus.pulsusbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserService userService;

    @Override
    public boolean changeSubscription(Long userId, Long followedId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Login error"));

        User followed = userService.findById(followedId)
                .orElseThrow(() -> new NotFoundException("Followed user not found"));

        Subscription subscription = subscriptionRepository.findByFollowerAndFollowed(user, followed);

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

        List<Subscription> subscriptions = subscriptionRepository.findByFollowed(user);
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

        List<Subscription> subscriptions = subscriptionRepository.findByFollower(user);
        List<UserInfoDto> following = new ArrayList<>();

        for(Subscription follower: subscriptions) {
            following.add(UserInfoDtoMapper.mapToUserInfoDto(follower.getFollowed()));
        }

        return following;
    }

    @Override
    public Long getFollowersCount(Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new NotFoundException("User don't exists"));

        Long countFollowers = subscriptionRepository.countByFollower(user);

        if(countFollowers == null) countFollowers = 0L;
        return countFollowers;
    }

    @Override
    public Long getFollowingCount(Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new NotFoundException("User don't exists"));

        Long countFollowing = subscriptionRepository.countByFollowed(user);

        if(countFollowing == null) countFollowing = 0L;
        return countFollowing;
    }

    @Override
    public SubscriptionCountDto getSubscriptionsCount(Long userId) {
        SubscriptionCountDto subscriptionCountDto =
                new SubscriptionCountDto(getFollowersCount(userId), getFollowingCount(userId));

        return subscriptionCountDto;
    }

}
