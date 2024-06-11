package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.dto.StatisticDto;
import org.springframework.stereotype.Service;

@Service
public interface StatisticService {

    StatisticDto getAllStatistic(Long userId);

    StatisticDto getBestStatistic(Long userId);

    StatisticDto getAllStatisticById(Long userId, Long userStatisticId);

    StatisticDto getBestStatisticById(Long userId, Long userStatisticId);

}
