package com.example.doormate.service;

import com.example.doormate.domain.Message;
import com.example.doormate.domain.RepetitionPeriod;
import com.example.doormate.dto.ReminderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
class AlarmServiceTest {

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private ReminderService reminderService;

    @Test
    void 주간_알림_등록() {
        ReminderDto reminderDto = new ReminderDto("주간 등록 테스트", "",
                LocalDate.of(2022, 10, 1), LocalDate.of(2022, 12, 12),
                LocalTime.of(12, 0), LocalTime.of(13, 0),
                RepetitionPeriod.WEEKLY, "MON TUE FIR");

        Long reminder_id = reminderService.saveReminder(reminderDto);

        Message message = alarmService.saveWeeklyAlarm(reminder_id);

        System.out.println("message = " + message);
    }


    @Test
    void deleteAlarm() {
        ReminderDto reminderDto = new ReminderDto("주간 등록 테스트", "",
                LocalDate.of(2022, 10, 1), LocalDate.of(2022, 12, 12),
                LocalTime.of(12, 0), LocalTime.of(13, 0),
                RepetitionPeriod.WEEKLY, "MON TUE FIR");

        Long reminder_id = reminderService.saveReminder(reminderDto);

        Message message = alarmService.saveWeeklyAlarm(reminder_id);

        // when
        alarmService.deleteAlarm(reminder_id);

    }

    @Test
    void findTodayAlarm() {
        ReminderDto reminderDto = new ReminderDto("매일 알림 등록", "",
                LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 12),
                LocalTime.of(12, 0), LocalTime.of(13, 0),
                RepetitionPeriod.DAILY, "");

        Long reminder_id = reminderService.saveReminder(reminderDto);

        alarmService.saveWeeklyAlarm(reminder_id);

        ReminderDto reminderDto1 = new ReminderDto("주간 등록 테스트", "",
                LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 10),
                LocalTime.of(12, 0), LocalTime.of(13, 0),
                RepetitionPeriod.WEEKLY, "MON TUE FIR");

        Long reminder_id1 = reminderService.saveReminder(reminderDto1);

        alarmService.saveWeeklyAlarm(reminder_id1);

        // when
        alarmService.findTodayAlarm();
    }
}