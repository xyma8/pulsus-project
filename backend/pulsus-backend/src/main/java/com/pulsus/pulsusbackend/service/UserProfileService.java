package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.dto.SubscriptionCountDto;
import com.pulsus.pulsusbackend.dto.UserCardInfoDto;
import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.exception.NotFoundException;
import com.pulsus.pulsusbackend.mapper.UserCardInfoMapper;
import com.pulsus.pulsusbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private WorkoutService workoutService;

    public UserCardInfoDto getUserCardInfo(Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new NotFoundException("User don't exists"));

        SubscriptionCountDto subscriptionCountDto
                = subscriptionService.getSubscriptionsCount(userId);

        Long workoutsCount =  workoutService.getCountWorkouts(userId);

        UserCardInfoDto userCardInfoDto
                = UserCardInfoMapper.mapToUserCardInfo(user, subscriptionCountDto, workoutsCount);

        return userCardInfoDto;
    }
}
