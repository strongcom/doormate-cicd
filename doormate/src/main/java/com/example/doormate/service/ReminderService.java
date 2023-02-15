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
    public Message updateReminder(Long id, ReminderDto reminderDto) {
        Optional<Reminder> reminder = reminderRepository.findById(id);
        // 해당 리마인더 존재 여부 확인
        if (reminder.isPresent()) {
            reminder.get().setTitle(reminderDto.getTitle());
            reminder.get().setContent(reminderDto.getContent());
            reminder.get().setSubTitle(reminderDto.toSubtitle(reminderDto));
            reminder.get().setStartDate(reminderDto.getStartDate());
            reminder.get().setEndDate(reminderDto.getEndDate());
            reminder.get().setStartTime(reminderDto.getStartTime());
            reminder.get().setEndTime(reminderDto.getEndTime());
            reminder.get().setRepetitionPeriod(reminderDto.getRepetitionPeriod());
            reminder.get().setRepetitionDay(reminderDto.getRepetitionDay());
            reminderRepository.save(reminder.get());
        }
        return new Message(UPDATE_SUCCESS_MESSAGE);
    }

    @Transactional
    public Message deleteReminder(Long id) {
        reminderRepository.deleteById(id);
        return new Message(DELETE_SUCCESS_MESSAGE);
    }


}
