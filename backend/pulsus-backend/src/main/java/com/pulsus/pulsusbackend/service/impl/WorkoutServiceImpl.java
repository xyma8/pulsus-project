package com.pulsus.pulsusbackend.service.impl;
import com.pulsus.pulsusbackend.dto.*;
import com.pulsus.pulsusbackend.entity.*;
import com.pulsus.pulsusbackend.exception.*;
import com.pulsus.pulsusbackend.mapper.TypeSportMapper;
import com.pulsus.pulsusbackend.mapper.WorkoutMapper;
import com.pulsus.pulsusbackend.mapper.WorkoutSummaryMapper;
import com.pulsus.pulsusbackend.model.FITTrackData;
import com.pulsus.pulsusbackend.repository.TypeSportRepository;
import com.pulsus.pulsusbackend.repository.WorkoutRepository;
import com.pulsus.pulsusbackend.repository.WorkoutSummaryRepository;
import com.pulsus.pulsusbackend.service.*;
import com.pulsus.pulsusbackend.validator.WorkoutValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    TypeSportRepository typeSportRepository;

    @Autowired
    WorkoutSummaryRepository workoutSummaryRepository;

    @Autowired
    private FileOnServerService fileOnServerService;

    @Autowired
    TrackFileService trackFileService;

    @Autowired
    private UserService userService;

    @Autowired
    FileService fileService;

    @Autowired
    SubscriptionService subscriptionService;


    public Optional<Workout> findById(Long workoutId) {
        return workoutRepository.findById(workoutId);
    }

    @Override
    public WorkoutDto createWorkout(Long userId, MultipartFile file) {
        Workout newWorkout = new Workout();
        User user = userService.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Login error")); //проверка не нужна??

        newWorkout.setName("Новая тренировка");
        newWorkout.setAccessType(2);
        newWorkout.setTimestamp(getTimestamp());

        /*
        FITFileDto fitFileDto = trackFileService.readTrack(file);
        List<float[]> coordinates = new ArrayList<>();
        List<FITTrackData> trackData = fitFileDto.getFitTrackData();
        for(FITTrackData data: trackData) {
            float[] coord = { data.getPositionLat(), data.getPositionLong() };
            coordinates.add(coord);
        }
        String mapImage = "";
        try {
            mapImage = fileService.createMapImage(userId, coordinates);
        }catch (Exception e){
            System.out.println(e);
            throw new InternalServerException("Internal error");
        }
        */

        TrackSummaryDto trackSummaryDto = trackFileService.readTrackSummary(file);
        String typeSport = trackSummaryDto.getFitSessionData().get(0).getSport().toString();
        if(!allowedTypeSport(typeSport)) {
            throw new BadRequestException("This type sport not allowed");
        }
        newWorkout.setTypeSports(typeSport);
        newWorkout.setUser(user);

        WorkoutSummary workoutSummary = WorkoutSummaryMapper.mapToWorkoutSummary(trackSummaryDto);
        workoutSummaryRepository.save(workoutSummary);
        newWorkout.setSummary(workoutSummary);

        FileOnServer fileOnServer = fileOnServerService.addTrackFile(file, userId); // файл сохраняем после всех проверок
        newWorkout.setFileWorkout(fileOnServer);

        Workout savedWorkout = workoutRepository.save(newWorkout);
        return WorkoutMapper.mapToWorkoutDto(savedWorkout);
    }

    @Override
    public Long getCountWorkouts(Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Login error")); //проверка не нужна??

        Long countWorkouts = workoutRepository.countByUser(user);

        if(countWorkouts == null) countWorkouts = 0L;
        return countWorkouts;
    }

    @Override
    public WorkoutDto getInfoWorkout(Long userId, Long workoutId) {
        Workout workout = findById(workoutId)
                .orElseThrow(() -> new NotFoundException("This workout does not exists"));

        if(!checkAccess(userId, workout)) {
            throw new NotFoundException("This workout does not exists");
        }

        WorkoutDto workoutDto = WorkoutMapper.mapToWorkoutDto(workout);
        return workoutDto;
    }

    @Override
    public Boolean checkAccess(Long userId, Workout workout) {
        Integer accessType = workout.getAccessType();
        Long workoutUserId = workout.getUser().getId();

        if(accessType == 0) return true;

        if(userId == workoutUserId) return true;

        if(accessType==1 &&
                subscriptionService.checkSubscription(userId, workoutUserId)) return true;

        return false;
    }

    @Override
    public WorkoutSummaryDto getSummaryWorkout(Long userId, Long workoutId) {
        Workout workout = findById(workoutId)
                .orElseThrow(() -> new NotFoundException("This workout does not exists"));

        Integer accessType = workout.getAccessType();

        if(accessType==2 && workout.getUser().getId() != userId) {
            System.out.println("This workout does not exists");
            throw new NotFoundException("This workout does not exists");
        }

        WorkoutSummary workoutSummary = workout.getSummary();
        //нужна проверка на null?
        WorkoutSummaryDto workoutSummaryDto = WorkoutMapper.mapToWorkoutSummaryDto(workoutSummary);
        return workoutSummaryDto;
    }

    @Override
    public WorkoutDto editInfoWorkout(Long userId, Long workoutId, WorkoutDto editedData) {
        Workout workout = findById(workoutId)
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
        workout.setName(name);

        if(!WorkoutValidator.isValidDescription(description)) {
            throw new BadRequestException("Invalid description field");
        }
        workout.setDescription(description);

        if(WorkoutValidator.isValidAccessType(accessType)) {
            workout.setAccessType(accessType); // если валидный то меняем, если нет то не трогаем
        }

        if(!WorkoutValidator.isValidTypeSport(typeSport, typeSportRepository.findAll())) {
            workout.setTypeSports(typeSport); // тут тоже
        }

        Workout savedWorkout = workoutRepository.save(workout);
        return WorkoutMapper.mapToWorkoutDto(savedWorkout);
    }

    @Override
    public FITFileDto getTrackWorkout(Long userId, Long workoutId) {
        Workout workout = findById(workoutId)
                .orElseThrow(() -> new NotFoundException("This workout does not exists"));

        Integer accessType = workout.getAccessType();

        if(accessType==2 && workout.getUser().getId() != userId) {
            System.out.println("This workout does not exists");
            throw new NotFoundException("This workout does not exists");
        }

        FileOnServer fileOnServer = workout.getFileWorkout();
        FITFileDto fitFileDto = trackFileService.readTrack(fileOnServer);

        return fitFileDto;
    }

    @Override
    public TrackSummaryDto getTrackSummaryWorkout(Long userId, Long workoutId) {
        Workout workout = findById(workoutId)
                .orElseThrow(() -> new NotFoundException("This workout does not exists"));

        Integer accessType = workout.getAccessType();

        if(accessType==2 && workout.getUser().getId() != userId) {
            System.out.println("This workout does not exists");
            throw new NotFoundException("This workout does not exists");
        } //вынести в отдельную функцию

        FileOnServer fileOnServer = workout.getFileWorkout();
        TrackSummaryDto trackSummaryDto = trackFileService.readTrackSummary(fileOnServer);

        return trackSummaryDto;
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
