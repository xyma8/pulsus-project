package com.pulsus.pulsusbackend.repository;

import com.pulsus.pulsusbackend.entity.TypeSport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeSportRepository extends JpaRepository<TypeSport, Integer> {

}
