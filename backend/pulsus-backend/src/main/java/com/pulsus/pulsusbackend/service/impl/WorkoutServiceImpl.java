package com.pulsus.pulsusbackend.service.impl;
import com.pulsus.pulsusbackend.dto.TypeSportDto;
import com.pulsus.pulsusbackend.dto.WorkoutDto;
import com.pulsus.pulsusbackend.entity.FileOnServer;
import com.pulsus.pulsusbackend.entity.TypeSport;
import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.entity.Workout;
import com.pulsus.pulsusbackend.exception.*;
import com.pulsus.pulsusbackend.mapper.TypeSportMapper;
import com.pulsus.pulsusbackend.mapper.WorkoutMapper;
import com.pulsus.pulsusbackend.repository.TypeSportRepository;
import com.pulsus.pulsusbackend.repository.WorkoutRepository;
import com.pulsus.pulsusbackend.service.FileOnServerService;
import com.pulsus.pulsusbackend.service.UserService;
import com.pulsus.pulsusbackend.service.WorkoutService;
import com.pulsus.pulsusbackend.validator.WorkoutValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    TypeSportRepository typeSportRepository;

    @Autowired
    private FileOnServerService fileOnServerService;

    @Autowired
    private UserService userService;

    @Override
    public WorkoutDto createWorkout(MultipartFile file, Long userId) {
        Workout newWorkout = new Workout();
        User user = userService.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Login error"));

        newWorkout.setName("Новая тренировка");
        newWorkout.setAccessType(2);
        newWorkout.setTimestamp(getTimestamp());
        String typeSport = fileOnServerService.getTypeSport(file);
        if(!allowedTypeSport(typeSport)) {
            throw new InternalServerException("This type sport not allowed");
        }
        newWorkout.setTypeSports(typeSport);
        newWorkout.setUser(user);
        FileOnServer fileOnServer = fileOnServerService.addTrackFile(file, userId); // файл сохраняем после всех проверок
        newWorkout.setFilesOnServer(fileOnServer);

        Workout savedWorkout = workoutRepository.save(newWorkout);
        return WorkoutMapper.mapToWorkoutDto(savedWorkout);
    }

    @Override
    public WorkoutDto getInfoWorkout(Long userId, Long workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new NotFoundException("This workout does not exists"));

        Integer accessType = workout.getAccessType();

        if(accessType==2 && workout.getUser().getId() != userId) {
            System.out.println("This workout does not exists");
            throw new NotFoundException("This workout does not exists");
        }

        WorkoutDto workoutDto = WorkoutMapper.mapToWorkoutDto(workout);
        return workoutDto;
    }

    @Override
    public WorkoutDto editInfoWorkout(Long userId, Long workoutId, WorkoutDto editedData) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new NotFoundException("This workout does not exists"));

        if(workout.getUser().getId() != userId) {
            throw new ForbiddenException("Access denied");
        }

        String name = editedData.getName();
        String description = editedData.getDescription();
        Integer accessType = editedData.getAccessType();
        String typeSport = editedData.getTypeSport();

        if(!WorkoutValidator.isValidName(name)) {
            throw new BadRequestException("Invalid name field");
        }

        if(!WorkoutValidator.isValidDescription(description)) {
            throw new BadRequestException("Invalid description field");
        }

        if(!WorkoutValidator.isValidAccessType(accessType)) {
            accessType = 2; // правильнее будет вобще не изменять и не трогать
        }

        if(!WorkoutValidator.isValidTypeSport(typeSport, typeSportRepository.findAll())) {
            typeSport = "CYCLING"; // тут тоже правильнее будет не трогать
        }

        workout.setName(name);
        workout.setDescription(description);
        workout.setAccessType(accessType);
        workout.setTypeSports(typeSport);

        Workout savedWorkout = workoutRepository.save(workout);
        return WorkoutMapper.mapToWorkoutDto(savedWorkout);
    }

    @Override
    public List<TypeSportDto> getTypesSport() {
        List<TypeSport> list = typeSportRepository.findAll();
        List<TypeSportDto> listDto = new ArrayList<>();
        for(TypeSport type: list) {
            listDto.add(TypeSportMapper.mapToTypeSportDto(type));
        }

        return listDto;
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
