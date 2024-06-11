package com.pulsus.pulsusbackend.controller;

import com.pulsus.pulsusbackend.dto.FileOnServerDto;
import com.pulsus.pulsusbackend.dto.StatisticDto;
import com.pulsus.pulsusbackend.service.StatisticService;
import com.pulsus.pulsusbackend.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/statistics")
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<StatisticDto> getAllStatistic(Authentication authentication) {
        StatisticDto statisticDto = statisticService.getAllStatistic(getUserId(authentication));

        return ResponseEntity.ok(statisticDto);
    }

    @GetMapping("/best")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<StatisticDto> getBestStatistic(Authentication authentication) {
        StatisticDto statisticDto = statisticService.getBestStatistic(getUserId(authentication));

        return ResponseEntity.ok(statisticDto);
    }

    @GetMapping("/{userId}/all")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<StatisticDto> getAllStatisticById(Authentication authentication, @PathVariable Long userId) {
        StatisticDto statisticDto = statisticService.getAllStatisticById(getUserId(authentication), userId);

        return ResponseEntity.ok(statisticDto);
    }

    @GetMapping("/{userId}/best")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<StatisticDto> getBestStatisticById(Authentication authentication, @PathVariable Long userId) {
        StatisticDto statisticDto = statisticService.getBestStatisticById(getUserId(authentication), userId);

        return ResponseEntity.ok(statisticDto);
    }


    private Long getUserId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());

        return userId;
    }
}
