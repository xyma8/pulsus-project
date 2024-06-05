package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.dto.UserInfoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriptionService {

    boolean changeSubscription(Long userId, Long followedId);

    List<UserInfoDto> getFollowers(Long userId);

    List<UserInfoDto> getFollowing(Long userId);

    Integer getFollowersCount(Long userId);

    Integer getFollowingCount(Long userId);


}
