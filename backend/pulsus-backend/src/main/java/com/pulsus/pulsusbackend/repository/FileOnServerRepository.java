package com.pulsus.pulsusbackend.repository;

import com.pulsus.pulsusbackend.entity.FileOnServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileOnServerRepository extends JpaRepository<FileOnServer, Long> {

}
