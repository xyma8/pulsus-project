package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.dto.SubscriptionCountDto;
import com.pulsus.pulsusbackend.dto.UserInfoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriptionService {

    boolean checkSubscription(Long userId, Long followedId);

    boolean changeSubscription(Long userId, Long followedId);

    List<UserInfoDto> getFollowers(Long userId);

    List<UserInfoDto> getFollowing(Long userId);

    Long getFollowersCount(Long userId);

    Long getFollowingCount(Long userId);

    SubscriptionCountDto getSubscriptionsCount(Long userId);
}
