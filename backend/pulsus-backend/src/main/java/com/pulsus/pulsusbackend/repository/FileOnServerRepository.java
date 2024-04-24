package com.pulsus.pulsusbackend.repository;

import com.pulsus.pulsusbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileOnServerRepository extends JpaRepository<User, Long> {

}
