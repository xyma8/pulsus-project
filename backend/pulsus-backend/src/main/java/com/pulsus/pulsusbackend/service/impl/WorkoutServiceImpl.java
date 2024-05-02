package com.pulsus.pulsusbackend.service.impl;
import com.pulsus.pulsusbackend.entity.FileOnServer;
import com.pulsus.pulsusbackend.entity.Workout;
import com.pulsus.pulsusbackend.service.FileOnServerService;
import com.pulsus.pulsusbackend.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService{

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private FileOnServerService fileOnServerService;

    @Override
    public Workout createWorkout(MultipartFile file, Long userId) {
        FileOnServer fileOnServer = new FileOnServer();
        fileOnServerService.addTrackFile(file, userId);
        return null;
    }
}
