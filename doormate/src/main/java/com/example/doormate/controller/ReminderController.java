package com.example.doormate.controller;

import com.example.doormate.domain.Message;
import com.example.doormate.domain.RepetitionPeriod;
import com.example.doormate.dto.ReminderDto;
import com.example.doormate.service.AlarmService;
import com.example.doormate.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reminder")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;
    private final AlarmService alarmService;

    @PostMapping
    @ResponseBody
    public Message create(@RequestBody ReminderDto reminderRequestDto) {
        Long savedReminderId = reminderService.saveReminder(reminderRequestDto);
        RepetitionPeriod repetitionPeriod = reminderRequestDto.getRepetitionPeriod();
        Message message;

        if (repetitionPeriod == RepetitionPeriod.DAILY) {
            message = alarmService.saveDailyAlarm(savedReminderId);
        } else if (repetitionPeriod == RepetitionPeriod.WEEKLY) {
            message = alarmService.saveWeeklyAlarm(savedReminderId);
        } else if (repetitionPeriod == RepetitionPeriod.MONTHLY) {
            message = alarmService.saveMonthlyAlarm(savedReminderId);
        } else if (repetitionPeriod == RepetitionPeriod.YEARLY) {
            message = alarmService.saveYearlyAlarm(savedReminderId);
        } else {
            message = alarmService.saveAlarm(savedReminderId);
        }

        return message;
    }


    @PutMapping
    @ResponseBody
    public Message update(@RequestBody ReminderDto reminderDto) {
        reminderService.updateReminder(reminderDto)
    }

    public String findOne() {

    }
    */
}
