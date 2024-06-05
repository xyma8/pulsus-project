package com.pulsus.pulsusbackend.repository;

import com.pulsus.pulsusbackend.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Subscription findByFollowerAndFollowed(Long follower, Long followed);

    List<Subscription> findByFollower(Long follower);

    List<Subscription> findByFollowed(Long followed);

    Integer countByFollower(Long follower);

    Integer countByFollowed(Long followed);
}
