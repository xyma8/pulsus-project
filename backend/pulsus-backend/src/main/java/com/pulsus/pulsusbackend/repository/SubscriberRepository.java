package com.pulsus.pulsusbackend.repository;

import com.pulsus.pulsusbackend.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscription, Long> {

}
