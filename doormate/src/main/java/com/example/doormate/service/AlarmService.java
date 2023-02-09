package com.example.doormate.service;

import com.example.doormate.domain.Alarm;
import com.example.doormate.domain.Message;
import com.example.doormate.domain.Reminder;
import com.example.doormate.repository.AlarmRepository;
import com.example.doormate.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final ReminderRepository reminderRepository;
    private final AlarmRepository alarmRepository;

    private static final String SAVE_ALARM_SUCCESS_MESSAGE = "알림 등록 완료";

    // === Alarm 테이블에 저장 === //
    public Message saveAlarm(Long id) {
        Optional<Reminder> findReminder = reminderRepository.findById(id);
        if (findReminder.isPresent()) {
            Reminder reminder = findReminder.get();
            Alarm alarm = reminder.toAlarm(reminder);
            alarmRepository.save(alarm);
            return new Message(SAVE_ALARM_SUCCESS_MESSAGE);
        }
        return new Message("등록실패");
    }

    // === 반복 reminder Alarm 테이블에 저장 === //
    public Message saveRepetitionAlarm() {

    }
}
