package com.example.doormate.service;

import com.example.doormate.domain.Alarm;
import com.example.doormate.domain.Message;
import com.example.doormate.domain.Reminder;
import com.example.doormate.domain.RepetitionPeriod;
import com.example.doormate.repository.AlarmRepository;
import com.example.doormate.repository.ReminderRepository;
import com.example.doormate.util.RepetitionDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final ReminderRepository reminderRepository;
    private final AlarmRepository alarmRepository;
    private final RepetitionDate repetitionDate;

    // === Alarm 테이블에 저장 === //
    @Transactional
    public Message saveAlarm(Long id) {
        Reminder reminder = reminderRepository.findById(id).orElse(null);
        RepetitionPeriod repetitionPeriod = reminder.getRepetitionPeriod();
        Message message;

        if (repetitionPeriod == RepetitionPeriod.DAILY) {
            message = repetitionDate.saveDailyAlarm(id);
        } else if (repetitionPeriod == RepetitionPeriod.WEEKLY) {
            message = repetitionDate.saveWeeklyAlarm(id);
        } else if (repetitionPeriod == RepetitionPeriod.MONTHLY) {
            message = repetitionDate.saveMonthlyAlarm(id);
        } else if (repetitionPeriod == RepetitionPeriod.YEARLY) {
            message = repetitionDate.saveYearlyAlarm(id);
        } else {
            message = repetitionDate.saveOneAlarm(id);
        }
        return message;
    }

    @Transactional
    public void deleteAlarm(Long id) {
        alarmRepository.deleteAllByReminderReminderId(id);
    }

    @Transactional
    public List<Reminder> findTodayAlarm() {
        List<Alarm> todayAlarmList = alarmRepository.findAllByNoticeDate(LocalDate.now());
        List<Reminder> reminders = new ArrayList<>();
        for (Alarm alarm : todayAlarmList) {
            Reminder reminder = alarm.getReminder();
            reminders.add(reminder);
        }
        return reminders;
    }
}
