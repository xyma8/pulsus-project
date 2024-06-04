package com.pulsus.pulsusbackend.service.impl;

import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.exception.UnauthorizedException;
import com.pulsus.pulsusbackend.service.SubscriptionService;
import com.pulsus.pulsusbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private UserService userService;

    @Override
    public boolean changeSubscription(Long userId, Long followedId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Login error"));
        return false;
    }
}
