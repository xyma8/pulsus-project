package com.pulsus.pulsusbackend.controller;

import com.pulsus.pulsusbackend.dto.PostDto;
import com.pulsus.pulsusbackend.dto.WorkoutDto;
import com.pulsus.pulsusbackend.service.PostService;
import com.pulsus.pulsusbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/{workoutId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<PostDto> getPost(Authentication authentication, @PathVariable Long workoutId) {
        PostDto postDto = postService.getPost(getUserId(authentication), workoutId);

        return ResponseEntity.ok(postDto);
    }


    private Long getUserId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());

        return userId;
    }
}
