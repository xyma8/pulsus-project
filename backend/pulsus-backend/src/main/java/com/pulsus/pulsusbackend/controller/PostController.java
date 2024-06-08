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

import java.util.List;

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

    @GetMapping("/{page}/{size}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Long>> getPosts(Authentication authentication, @PathVariable Integer page, @PathVariable Integer size) {
        List<Long> postsUser = postService.getPosts(getUserId(authentication), page, size);

        return ResponseEntity.ok(postsUser);
    }

    @GetMapping("/{userId}/{page}/{size}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Long>> getPostsByUserId(Authentication authentication, @PathVariable Long userId, @PathVariable Integer page, @PathVariable Integer size) {
        List<Long> postsUser = postService.getPostsByUserId(getUserId(authentication), userId, page, size);

        return ResponseEntity.ok(postsUser);
    }

    @GetMapping("/feed/{page}/{size}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Long>> getFeed(Authentication authentication, @PathVariable Integer page, @PathVariable Integer size) {
        List<Long> postsUser = postService.getFeed(getUserId(authentication), page, size);

        return ResponseEntity.ok(postsUser);
    }


    private Long getUserId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());

        return userId;
    }
}
