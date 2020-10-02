package com.wookie.lukapp.api.schedule;

import com.wookie.lukapp.api.DTO.ScheduleParameters;
import com.wookie.lukapp.core.aggregates.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/generate")
    public ResponseEntity<List<Schedule>> generateSchedule(@RequestBody ScheduleParameters scheduleParameters) throws Exception {
        List<Schedule> result = scheduleService.generateSchedules(scheduleParameters);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
