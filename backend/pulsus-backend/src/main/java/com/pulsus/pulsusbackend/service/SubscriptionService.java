package com.pulsus.pulsusbackend.service;

import org.springframework.stereotype.Service;

@Service
public interface SubscriptionService {

    public boolean changeSubscription(Long userId, Long followedId);
}
