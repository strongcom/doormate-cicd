package com.example.doormate.service;

import com.example.doormate.domain.Message;
import com.example.doormate.domain.Reminder;
import com.example.doormate.dto.ReminderDto;
import com.example.doormate.repository.AlarmRepository;
import com.example.doormate.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final AlarmRepository alarmRepository;

    private static final String SAVE_REMINDER_SUCCESS_MESSAGE = "리마인더 등록 완료";
    private static final String UPDATE_SUCCESS_MESSAGE = "리마인더 수정 완료";
    private static final String ERROR_NOT_EXISTS_REMINDER_MESSAGE = "해당 리마인더가 존재하지 않습니다.";
    private static final String DELETE_SUCCESS_MESSAGE = "리마인더 삭제 완료";

    @Transactional
    public Long saveReminder(ReminderDto reminderDto) {
        Reminder reminder = reminderDto.toReminder(reminderDto);
        Reminder savedReminder = reminderRepository.save(reminder);
        return savedReminder.getReminderId();
    }

    @Transactional
    public Optional<Reminder> findOneReminder(Long id) {
        Optional<Reminder> findReminder = reminderRepository.findById(id);
        // 예외처리 해야함
        return findReminder;
    }

    @Transactional
    public Long updateReminder(Long id, ReminderDto reminderDto) {
        Reminder reminder = reminderRepository.findById(id).orElse(null);
        // 해당 리마인더 존재 여부 확인
        reminder.setTitle(reminderDto.getTitle());
        reminder.setContent(reminderDto.getContent());
        reminder.setSubTitle(reminderDto.toSubtitle(reminderDto));
        reminder.setStartDate(reminderDto.getStartDate());
        reminder.setEndDate(reminderDto.getEndDate());
        reminder.setStartTime(reminderDto.getStartTime());
        reminder.setEndTime(reminderDto.getEndTime());
        reminder.setRepetitionPeriod(reminderDto.getRepetitionPeriod());
        reminder.setRepetitionDay(reminderDto.getRepetitionDay());

        return reminder.getReminderId();
    }

    @Transactional
    public Message deleteReminder(Long id) {
        // 무결성 위반 방지를 위해 자식 테이블 값 삭제 후, 리마인더 삭제
        alarmRepository.deleteAllByReminderReminderId(id);
        reminderRepository.deleteById(id);
        return new Message(DELETE_SUCCESS_MESSAGE);
    }


}
