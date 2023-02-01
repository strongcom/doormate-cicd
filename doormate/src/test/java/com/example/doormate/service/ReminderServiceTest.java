package com.example.doormate.service;

import com.example.doormate.domain.Reminder;
import com.example.doormate.domain.RepetitionDay;
import com.example.doormate.domain.RepetitionPeriod;
import com.example.doormate.domain.User;
import com.example.doormate.dto.ReminderDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class ReminderServiceTest {

    @Autowired
    ReminderService reminderService;

    @Test
    void 리마인더_저장() {
        //given
        User user = new User();
        user.setUserId(1L);

        ReminderDto requestDto = ReminderDto.builder()
                .title("물챙기기")
                .content("물챙기기 내용")
                .startDate(LocalDateTime.of(2023,1,11,14,00))
                .endDate(LocalDateTime.of(2023,1,24,19,00))
                .repetitionPeriod(null)
                .repetitionDay(null)
                .build();

        //when
        Reminder reminder = requestDto.toEntity(requestDto);
        Reminder savedReminder = reminderService.create(reminder);

        //then
        Assertions.assertThat(savedReminder.getReminderId()).isEqualTo(1);

    }

    @Test
    void 반복_저장() {
        // given
        ReminderDto reminder1 = ReminderDto.builder()
                .title("물챙기기")
                .content("물챙기기 내용")
                .startDate(LocalDateTime.of(2023,1,11,14,00))
                .endDate(LocalDateTime.of(2023,1,31,19,00))
                .repetitionPeriod(RepetitionPeriod.DAILY)
                .repetitionDay(null)
                .build();

        ReminderDto reminder2 = ReminderDto.builder()
                .title("맥북 충전기 챙기기")
                .content(null)
                .startDate(LocalDateTime.of(2023,2,1,14,00))
                .endDate(LocalDateTime.of(2023,2,17,19,00))
                .repetitionPeriod(RepetitionPeriod.WEEKLY)
                .repetitionDay("MON WED FRI")
                .build();

        // when
        reminderService.createRepetitionReminder(reminder1);
        reminderService.createRepetitionReminder(reminder2);


        // then
    }

    @Test
    void 요일별_날짜_저장() {

        // given
        LocalDate startDate = LocalDate.of(2023, 1, 8);
        LocalDate endDate = LocalDate.of(2023, 1, 20);
        ArrayList<String> repetitionDayDto = new ArrayList<>(Arrays.asList("MON", "WED"));

        // 반복 요일 추출
        ArrayList<Integer> repetitionNumberList = new ArrayList<>();
        ArrayList<LocalDate> repetitionDate = new ArrayList<>();

        for (RepetitionDay repetitionDay : RepetitionDay.values()) {
            if (repetitionDayDto.contains(repetitionDay.getDay())) {
                repetitionNumberList.add(repetitionDay.getNumber());
            }
        }
        System.out.println("repetitionNumberList = " + repetitionNumberList);


        // 날짜 범위 사이 모두 구하기
        List<LocalDate> AllDay = startDate.datesUntil(endDate)
                .collect(Collectors.toList());
        System.out.println("AllDay = " + AllDay);

        // 모든 날짜 범위에서 해당되는 요일만 add()
        for (LocalDate date : AllDay){
            int dateWeek = date.getDayOfWeek().getValue();
            if (repetitionNumberList.contains(dateWeek)) {
                repetitionDate.add(date);
            }
        }
        System.out.println("repetitionDate = " + repetitionDate);
    }
}