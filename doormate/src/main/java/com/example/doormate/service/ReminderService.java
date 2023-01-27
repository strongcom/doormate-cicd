package com.example.doormate.service;

import com.example.doormate.domain.Reminder;
import com.example.doormate.repository.ReminderRepetitionRepository;
import com.example.doormate.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final ReminderRepetitionRepository reminderRepetitionRepository;


    // 리마인더 내용 저장
    public Reminder save(Reminder reminder) {
        Reminder savedReminder = reminderRepository.save(reminder);
        return savedReminder;
    }

    // 만약 repetitionPeriod 값이 존재한다면 반복 날짜 지정후 새로 저장


}
