package com.example.doormate.service;

import com.example.doormate.domain.*;
import com.example.doormate.repository.AlarmRepository;
import com.example.doormate.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final ReminderRepository reminderRepository;
    private final AlarmRepository alarmRepository;
    private final static String SAVE_ALARM_SUCCESS_MESSAGE = "알림 등록 완료";

    // === Alarm 테이블에 저장 === //
    @Transactional
    public Message saveAlarm(Long id) {
        Reminder reminder = reminderRepository.findById(id).orElse(null);
        RepetitionPeriod repetitionPeriod = reminder.getRepetitionPeriod();
        Message message;

        if (repetitionPeriod == RepetitionPeriod.DAILY) {
            message = saveDailyAlarm(id);
        } else if (repetitionPeriod == RepetitionPeriod.WEEKLY) {
            message = saveWeeklyAlarm(id);
        } else if (repetitionPeriod == RepetitionPeriod.MONTHLY) {
            message = saveMonthlyAlarm(id);
        } else if (repetitionPeriod == RepetitionPeriod.YEARLY) {
            message = saveYearlyAlarm(id);
        } else {
            message = saveAlarm(id);
        }
        return message;
    }

    @Transactional
    public Message deleteAlarm(Long id) {

        alarmRepository.deleteAllByReminderReminderId(id);

        return new Message("알람 삭제 완료");

    }

    @Transactional
    public List<Alarm> findTodayAlarm() {
        List<Alarm> todayAlarmList = alarmRepository.findAllByNoticeDate(LocalDate.now());
        return todayAlarmList;
    }



    public Message saveOneAlarm(Long id) {
        Reminder findReminder = reminderRepository.findById(id).orElse(null);
        if (findReminder != null) {
            Alarm alarm = findReminder.toAlarm(findReminder);
            alarmRepository.save(alarm);
            return new Message(SAVE_ALARM_SUCCESS_MESSAGE);
        }
        return new Message("등록실패");
    }

    // === 반복 reminder Alarm 테이블에 저장 === //

    public Message saveDailyAlarm(Long savedReminderId) {
        Reminder findReminder = reminderRepository.findById(savedReminderId).orElse(null);
        List<LocalDate> allDate = findAllDate(findReminder.getStartDate(), findReminder.getEndDate());
        for (LocalDate date : allDate) {
            Alarm alarm = new Alarm(findReminder, date, findReminder.getStartTime(), findReminder.getEndTime());
            alarmRepository.save(alarm);
        }
        return new Message("매일 알림등록 완료");
    }


    public Message saveWeeklyAlarm(Long savedReminderId) {
        Reminder findReminder = reminderRepository.findById(savedReminderId).orElse(null);
        ArrayList<Integer> repetitionDate = findByRepetitionDate(findReminder.getRepetitionDay());

        List<LocalDate> allDate = findAllDate(findReminder.getStartDate(), findReminder.getEndDate());

        for (LocalDate date : allDate
        ) {
            int day = date.getDayOfWeek().getValue();
            if (repetitionDate.contains(day)) {
                Alarm alarm = new Alarm(findReminder, date, findReminder.getStartTime(), findReminder.getEndTime());
                alarmRepository.save(alarm);
            }
        }
        return new Message("주간 알림등록 완료");
    }


    public Message saveMonthlyAlarm(Long savedReminderId) {
        Reminder findReminder = reminderRepository.findById(savedReminderId).orElse(null);
        List<LocalDate> allDate = findAllDate(findReminder.getStartDate(), findReminder.getEndDate());

        int day = findReminder.getStartDate().getDayOfMonth();

        for (LocalDate date : allDate) {
            if (date.getDayOfMonth() == day) {
                Alarm alarm = new Alarm(findReminder, date, findReminder.getStartTime(), findReminder.getEndTime());
                alarmRepository.save(alarm);
            }
        }
        return new Message("월간 알림등록 완료");
    }

    public Message saveYearlyAlarm(Long savedReminderId) {
        Reminder findReminder = reminderRepository.findById(savedReminderId).orElse(null);
        List<LocalDate> allDate = findAllDate(findReminder.getStartDate(), findReminder.getEndDate());

        int month = findReminder.getStartDate().getMonthValue();
        int day = findReminder.getStartDate().getDayOfMonth();

        for (LocalDate date : allDate) {
            if (date.getMonthValue() == month && date.getDayOfMonth() == day) {
                Alarm alarm = new Alarm(findReminder, date, findReminder.getStartTime(), findReminder.getEndTime());
                alarmRepository.save(alarm);
            }
        }
        return new Message("연간 알림등록 완료");
    }


    // startDate - endDate 사이의 모든 날짜 추출
    public List<LocalDate> findAllDate(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate.plusDays(1))
                .collect(Collectors.toList());
    }

    // 빈복 요일 추출
    public ArrayList<Integer> findByRepetitionDate(String repetitionDay) {
        String[] split = repetitionDay.split(" ");
        ArrayList<String> repetitionDayList = new ArrayList<>(Arrays.asList(split));
        ArrayList<Integer> repetitionNumberList = new ArrayList<>();

        for (RepetitionDay day : RepetitionDay.values()) {
            if (repetitionDayList.contains(day.getDay())) {
                repetitionNumberList.add(day.getNumber());
            }
        }
        return repetitionNumberList;
    }

}
