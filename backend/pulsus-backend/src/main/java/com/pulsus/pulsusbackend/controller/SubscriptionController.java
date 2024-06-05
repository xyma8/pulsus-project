package com.pulsus.pulsusbackend.controller;

import com.pulsus.pulsusbackend.dto.SubscriptionCountDto;
import com.pulsus.pulsusbackend.dto.UserInfoDto;
import com.pulsus.pulsusbackend.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private SubscriptionService subscriptionService;

    @PostMapping("/changeSubscription")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> changeSubscription(Authentication authentication, @RequestParam Long followedId) {
        boolean changeSubscription = subscriptionService.changeSubscription(getUserId(authentication), followedId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/followers/{userId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<UserInfoDto>> getFollowersByUserId(Authentication authentication, @PathVariable Long userId) {
        List<UserInfoDto> followers = subscriptionService.getFollowers(userId);

        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following/{userId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<UserInfoDto>> getFollowingByUserId(Authentication authentication, @PathVariable Long userId) {
        List<UserInfoDto> following = subscriptionService.getFollowing(userId);

        return ResponseEntity.ok(following);
    }

    @GetMapping("/followers/{userId}/count")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Long> getFollowersCountByUserId(Authentication authentication, @PathVariable Long userId) {
        Long followersCount = subscriptionService.getFollowersCount(userId);

        return new ResponseEntity<>(followersCount, HttpStatus.OK);
    }

    @GetMapping("/following/{userId}/count")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Long> getFollowingCountByUserId(Authentication authentication, @PathVariable Long userId) {
        Long followingCount = subscriptionService.getFollowingCount(userId);

        return new ResponseEntity<>(followingCount, HttpStatus.OK);
    }

    @GetMapping("/followers")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<UserInfoDto>> getFollowers(Authentication authentication) {
        List<UserInfoDto> followers = subscriptionService.getFollowers(getUserId(authentication));

        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<UserInfoDto>> getFollowing(Authentication authentication) {
        List<UserInfoDto> following = subscriptionService.getFollowing(getUserId(authentication));

        return ResponseEntity.ok(following);
    }

    @GetMapping("/followers/count")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Long> getFollowersCount(Authentication authentication) {
        Long followersCount = subscriptionService.getFollowersCount(getUserId(authentication));

        return new ResponseEntity<>(followersCount, HttpStatus.OK);
    }

    @GetMapping("/following/count")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Long> getFollowingCount(Authentication authentication) {
        Long followingCount = subscriptionService.getFollowingCount(getUserId(authentication));

        return new ResponseEntity<>(followingCount, HttpStatus.OK);
    }

    @GetMapping("/{userId}/count")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<SubscriptionCountDto> getSubscriptionsCountById(Authentication authentication, @PathVariable Long userId) {
        SubscriptionCountDto subscriptionCountDto = subscriptionService.getSubscriptionsCount(userId);

        return ResponseEntity.ok(subscriptionCountDto);
    }

    @GetMapping("/count")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<SubscriptionCountDto> getSubscriptionsCountById(Authentication authentication) {
        SubscriptionCountDto subscriptionCountDto = subscriptionService.getSubscriptionsCount(getUserId(authentication));

        return ResponseEntity.ok(subscriptionCountDto);
    }

    private Long getUserId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());

        return userId;
    }
}
