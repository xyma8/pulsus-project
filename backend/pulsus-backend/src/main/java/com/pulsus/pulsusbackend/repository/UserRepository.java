package com.pulsus.pulsusbackend.repository;

import com.pulsus.pulsusbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByLogin(String login);
}
