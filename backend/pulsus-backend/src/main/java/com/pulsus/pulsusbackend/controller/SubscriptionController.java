package com.pulsus.pulsusbackend.controller;

import com.pulsus.pulsusbackend.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private SubscriptionService subscriptionService;

    @GetMapping("/changeSubscription")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> changeSubscription(Authentication authentication, @RequestParam Long followedId) {
        boolean changeSubscription = subscriptionService.changeSubscription(getUserId(authentication), followedId);

        return null;
    }


    private Long getUserId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());

        return userId;
    }
}
