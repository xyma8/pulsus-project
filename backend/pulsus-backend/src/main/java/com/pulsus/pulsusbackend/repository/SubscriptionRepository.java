package com.pulsus.pulsusbackend.repository;

import com.pulsus.pulsusbackend.entity.Subscription;
import com.pulsus.pulsusbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Subscription findByFollowerAndFollowed(User follower, User followed);

    List<Subscription> findByFollower(User follower);

    List<Subscription> findByFollowed(User followed);

    Long countByFollower(User follower);

    Long countByFollowed(User followed);
}
