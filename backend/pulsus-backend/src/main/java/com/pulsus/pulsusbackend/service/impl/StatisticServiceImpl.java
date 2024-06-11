package com.pulsus.pulsusbackend.service.impl;

import com.pulsus.pulsusbackend.dto.StatisticDto;
import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.entity.Workout;
import com.pulsus.pulsusbackend.entity.WorkoutSummary;
import com.pulsus.pulsusbackend.exception.UnauthorizedException;
import com.pulsus.pulsusbackend.repository.SubscriptionRepository;
import com.pulsus.pulsusbackend.repository.WorkoutRepository;
import com.pulsus.pulsusbackend.repository.WorkoutSummaryRepository;
import com.pulsus.pulsusbackend.service.StatisticService;
import com.pulsus.pulsusbackend.service.SubscriptionService;
import com.pulsus.pulsusbackend.service.UserService;
import com.pulsus.pulsusbackend.service.WorkoutService;
import com.pulsus.pulsusbackend.util.NormalizeTrackData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private UserService userService;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private WorkoutSummaryRepository workoutSummaryRepository;

    @Autowired
    private WorkoutRepository workoutRepository;


    @Override
    public StatisticDto getAllStatistic(Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Login error"));

        List<Workout> allWorkouts = workoutRepository.findByUser(user);

        Long countWorkouts = allWorkouts.stream().count();
        Float allDistance = 0f;
        Integer allAscent = 0;
        Float allTime = 0f;

        for(Workout workout: allWorkouts) {
            allDistance += workout.getSummary().getTotalDistance();
            allAscent += workout.getSummary().getTotalAscent();
            allTime += workout.getSummary().getTotalEllapsedTime();
        }

        allDistance = NormalizeTrackData.meterToKm(allDistance);
        allDistance = NormalizeTrackData.roundFloat(allDistance, 2);
        String allTimeFormatted = NormalizeTrackData.toUsualTimeString(allTime);

        StatisticDto statisticDto = new StatisticDto(countWorkouts, allDistance, allAscent, allTimeFormatted);

        return statisticDto;
    }

    @Override
    public StatisticDto getBestStatistic(Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Login error"));

        List<Workout> allWorkouts = workoutRepository.findByUser(user);

        Long countWorkouts = 0L;
        Float allDistance = 0f;
        Integer allAscent = 0;
        Float allTime = 0f;

        for(Workout workout: allWorkouts) {
            if(workout.getSummary().getTotalDistance() > allDistance)
                allDistance = workout.getSummary().getTotalDistance();

            if(workout.getSummary().getTotalAscent() > allAscent)
                allAscent = workout.getSummary().getTotalAscent();

            if(workout.getSummary().getTotalEllapsedTime() > allTime)
                allTime = workout.getSummary().getTotalEllapsedTime();
        }

        allDistance = NormalizeTrackData.meterToKm(allDistance);
        allDistance = NormalizeTrackData.roundFloat(allDistance, 2);
        String allTimeFormatted = NormalizeTrackData.toUsualTimeString(allTime);

        StatisticDto statisticDto = new StatisticDto(countWorkouts, allDistance, allAscent, allTimeFormatted);

        return statisticDto;
    }

    @Override
    public StatisticDto getAllStatisticById(Long userId, Long userStatisticId) {
        if(userId == userStatisticId) {
            return getAllStatistic(userId);
        }

        User user = userService.findById(userStatisticId)
                .orElseThrow(() -> new UnauthorizedException("Login error"));

        List<Workout> allWorkouts = workoutRepository.findByUser(user);

        Long countWorkouts = 0L;
        Float allDistance = 0f;
        Integer allAscent = 0;
        Float allTime = 0f;

        for(Workout workout: allWorkouts) {
            if(!workoutService.checkAccess(userId, workout)) continue; //если пользователь не имеет доступ к данной тренировке запрашиваемого пользователя то пропускаем цикл с текущей

            countWorkouts++;
            allDistance += workout.getSummary().getTotalDistance();
            allAscent += workout.getSummary().getTotalAscent();
            allTime += workout.getSummary().getTotalEllapsedTime();
        }

        allDistance = NormalizeTrackData.meterToKm(allDistance);
        allDistance = NormalizeTrackData.roundFloat(allDistance, 2);
        String allTimeFormatted = NormalizeTrackData.toUsualTimeString(allTime);

        StatisticDto statisticDto = new StatisticDto(countWorkouts, allDistance, allAscent, allTimeFormatted);

        return statisticDto;
    }

    @Override
    public StatisticDto getBestStatisticById(Long userId, Long userStatisticId) {
        if(userId == userStatisticId) {
            return getBestStatistic(userId);
        }

        User user = userService.findById(userStatisticId)
                .orElseThrow(() -> new UnauthorizedException("Login error"));

        List<Workout> allWorkouts = workoutRepository.findByUser(user);

        Long countWorkouts = 0L;
        Float allDistance = 0f;
        Integer allAscent = 0;
        Float allTime = 0f;

        for(Workout workout: allWorkouts) {
            if(!workoutService.checkAccess(userId, workout)) continue;

            if(workout.getSummary().getTotalDistance() > allDistance)
                allDistance = workout.getSummary().getTotalDistance();

            if(workout.getSummary().getTotalAscent() > allAscent)
                allAscent = workout.getSummary().getTotalAscent();

            if(workout.getSummary().getTotalEllapsedTime() > allTime)
                allTime = workout.getSummary().getTotalEllapsedTime();
        }

        allDistance = NormalizeTrackData.meterToKm(allDistance);
        allDistance = NormalizeTrackData.roundFloat(allDistance, 2);
        String allTimeFormatted = NormalizeTrackData.toUsualTimeString(allTime);

        StatisticDto statisticDto = new StatisticDto(countWorkouts, allDistance, allAscent, allTimeFormatted);

        return statisticDto;
    }
}
