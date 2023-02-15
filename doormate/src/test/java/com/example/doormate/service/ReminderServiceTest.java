package com.example.doormate.service;

import com.example.doormate.domain.RepetitionPeriod;
import com.example.doormate.dto.ReminderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
class ReminderServiceTest {

    @Autowired
    private ReminderService reminderService;

    @Autowired
    private AlarmService alarmService;

    @Test
    void 리마인더_삭제() {
        ReminderDto reminderDto = new ReminderDto("주간 등록 테스트", "",
                LocalDate.of(2022, 10, 1), LocalDate.of(2022, 12, 12),
                LocalTime.of(12, 0), LocalTime.of(13, 0),
                RepetitionPeriod.WEEKLY, "MON TUE FIR");

        Long reminder_id = reminderService.saveReminder(reminderDto);
        alarmService.saveAlarm(reminder_id);

        // when
        alarmService.deleteAlarm(reminder_id);
        reminderService.deleteReminder(reminder_id);


    }
}