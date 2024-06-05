package com.pulsus.pulsusbackend.mapper;

import com.pulsus.pulsusbackend.dto.SubscriptionCountDto;
import com.pulsus.pulsusbackend.dto.UserCardInfoDto;
import com.pulsus.pulsusbackend.entity.User;

public class UserCardInfoMapper {

    public static UserCardInfoDto mapToUserCardInfo(User user, SubscriptionCountDto subscriptionCountDto, Long workoutsCount) {
        return new UserCardInfoDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getLogin(),
                subscriptionCountDto.getFollowing(),
                subscriptionCountDto.getFollowers(),
                workoutsCount
        );
    }
}
