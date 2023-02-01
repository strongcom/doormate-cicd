package com.example.doormate.service;

import com.example.doormate.data.dao.ReminderDao;
import com.example.doormate.domain.Reminder;
import com.example.doormate.domain.RepetitionPeriod;
import com.example.doormate.dto.ReminderDto;
import com.example.doormate.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final ReminderDao reminderRepetitionRepository;
    private final HashMap<Long, Reminder> store = new HashMap<>();
    private static Long repetitionId = 0L;

    // 리마인더 내용 저장
    public Reminder create(Reminder reminder) {
        return reminderRepository.save(reminder);
    }

    /*
    반복 설정 이외에 나머지 데이터는 빌더를 이용해 Reminder 객체에 저장, Id 제공하기
     */
    public String saveSubTitle(ReminderDto reminder) {
        RepetitionPeriod repetitionPeriod = reminder.getRepetitionPeriod();
        int startHour = reminder.getStartDate().toLocalTime().getHour();
        int startMinute = reminder.getStartDate().toLocalTime().getMinute();
        int endHour = reminder.getEndDate().toLocalTime().getHour();
        int endMinute = reminder.getEndDate().toLocalTime().getMinute();

        return repetitionPeriod + " " + startHour + "시 " + startMinute + "분 - " + endHour + "시 " + endMinute + "분 사이 알림";
    }


    /**
     * 반복 설정 저장(daily, weekly, monthly, yearly) -> 리스트에 저장 후 saveAll()을 이용하여 한꺼번에 저장
     */

    // 만약 repetitionPeriod 값이 존재한다면 반복 날짜 지정후 새로 저장
    public void createRepetitionReminder(ReminderDto ReminderDto) {
        Long repetitionId = ++this.repetitionId;
        ArrayList<LocalDate> date = new ArrayList<>();
        // 리마인더 dto가 들어오면 우선 시간 분리 후 start-end time 지정
        String startTime = reminderRepetitionRepository.findTime(ReminderDto.getStartDate());
        String endTime = reminderRepetitionRepository.findTime(ReminderDto.getEndDate());

        // 시간을 분리한 뒤 반복 종류에 따른 날짜 구분
        if (ReminderDto.getRepetitionPeriod() == RepetitionPeriod.DAILY) {
            date = reminderRepetitionRepository.searchRepetitionDaily(ReminderDto);
        } else if (ReminderDto.getRepetitionPeriod() == RepetitionPeriod.WEEKLY) {
            date = reminderRepetitionRepository.searchRepetitionWeekly(ReminderDto);
        } else if (ReminderDto.getRepetitionPeriod() == RepetitionPeriod.MONTHLY) {
            date = reminderRepetitionRepository.searchRepetitionMonthly(ReminderDto);
        } else if (ReminderDto.getRepetitionPeriod() == RepetitionPeriod.YEARLY) {
            date = reminderRepetitionRepository.searchRepetitionYearly(ReminderDto);
        }

        // 날짜와 시간 합치기 -> Reminder 엔티티에 담아 리스트에 저장
        // saveAll
        for (LocalDate localDate: date) {
            LocalDateTime startDate = reminderRepetitionRepository.makeLocalDateTime(localDate, startTime);
            LocalDateTime endDate = reminderRepetitionRepository.makeLocalDateTime(localDate, endTime);

            Reminder savedReminder = Reminder.builder()
                    .subTitle(saveSubTitle(ReminderDto))
                    .title(ReminderDto.getTitle())
                    .content(ReminderDto.getContent())
                    .startDate(startDate)
                    .endDate(endDate)
                    .repetitionId(repetitionId)
                    .repetitionPeriod(ReminderDto.getRepetitionPeriod())
                    .repetitionDay(ReminderDto.getRepetitionDay())
                    .build();
            reminderRepository.save(savedReminder);
        }

    }
}
