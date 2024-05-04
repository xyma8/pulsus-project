package com.pulsus.pulsusbackend.service.impl;
import com.pulsus.pulsusbackend.dto.WorkoutDto;
import com.pulsus.pulsusbackend.entity.FileOnServer;
import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.entity.Workout;
import com.pulsus.pulsusbackend.exception.InternalServerException;
import com.pulsus.pulsusbackend.exception.UnauthorizedException;
import com.pulsus.pulsusbackend.mapper.WorkoutMapper;
import com.pulsus.pulsusbackend.repository.WorkoutRepository;
import com.pulsus.pulsusbackend.service.FileOnServerService;
import com.pulsus.pulsusbackend.service.UserService;
import com.pulsus.pulsusbackend.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    private FileOnServerService fileOnServerService;

    @Autowired
    private UserService userService;

    @Override
    public WorkoutDto createWorkout(MultipartFile file, Long userId) {
        Workout newWorkout = new Workout();
        FileOnServer fileOnServer = fileOnServerService.addTrackFile(file, userId);
        User user = userService.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Login error"));

        newWorkout.setName("Тренировка");
        newWorkout.setAccessType(2);
        newWorkout.setTimestamp(getTimestamp());
        String typeSport = fileOnServerService.getTypeSport(file);
        if(!allowedTypeSport(typeSport)) {
            throw new InternalServerException("This type sport not allowed");
        }
        newWorkout.setTypeSports(typeSport);
        newWorkout.setUser(user);
        newWorkout.setFilesOnServer(fileOnServer);

        Workout savedWorkout = workoutRepository.save(newWorkout);
        return WorkoutMapper.mapToWorkoutDto(savedWorkout);
    }

    private Boolean allowedTypeSport(String typeSport) {
        if(!typeSport.toLowerCase().equals("cycling")) {
            return false;
        }

        return true;
    }

    private Date getTimestamp() {
        Instant instant = Instant.now();
        Date currentDate = Date.from(instant);
        return currentDate;
    }
}
