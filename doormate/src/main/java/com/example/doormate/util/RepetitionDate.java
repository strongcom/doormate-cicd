package com.example.doormate.util;

import com.example.doormate.domain.Alarm;
import com.example.doormate.domain.Message;
import com.example.doormate.domain.Reminder;
import com.example.doormate.domain.RepetitionDay;
import com.example.doormate.repository.AlarmRepository;
import com.example.doormate.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Configuration
public class RepetitionDate {

    private final AlarmRepository alarmRepository;
    private final ReminderRepository reminderRepository;

    public Message saveOneAlarm(Long id) {
        Reminder findReminder = reminderRepository.findById(id).orElse(null);
        if (findReminder != null) {
            Alarm alarm = findReminder.toAlarm(findReminder);
            alarmRepository.save(alarm);
            return new Message("알림등록완료");
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
