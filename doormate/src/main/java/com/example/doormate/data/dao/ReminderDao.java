package com.example.doormate.data.dao;

import com.example.doormate.domain.Reminder;
import com.example.doormate.domain.RepetitionDay;
import com.example.doormate.dto.ReminderDto;
import com.example.doormate.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReminderDao {

    private final ReminderRepository reminderRepository;

    private final HashMap<Long, Reminder> store = new HashMap<>();

    /*
     * 반복 등록 로직(start - end Date 가공 후, DB에 올리기) _ common
     */

    // startDate - endDate 사이의 모든 날짜 추출
    public List<LocalDate> findAllDate(ReminderDto reminderRequestDto) {
        LocalDate startDate = reminderRequestDto.getStartDate().toLocalDate();
        LocalDate endDate = reminderRequestDto.getEndDate().toLocalDate();

        return startDate.datesUntil(endDate.plusDays(1))
                .collect(Collectors.toList());
    }

    // 시간 추출
    // 반복 설정 시 무조건 실행
    public String findTime(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ISO_TIME);
    }

    // 분리되어 있는 날짜와 시간 합치기
    public LocalDateTime makeLocalDateTime(LocalDate localDate, String dateTime) {
        LocalTime localTime = LocalTime.parse(dateTime);
        int dayOfYear = localDate.getDayOfYear();  // 년
        int monthValue = localDate.getMonthValue();  // 월
        int dayOfMonth = localDate.getDayOfMonth();  // 일
        int hour = localTime.getHour();
        int minute = localTime.getMinute();

        return LocalDateTime.of(dayOfYear, monthValue, dayOfMonth, hour, minute);
    }


    /**
     * weekly 일 때, 반복 로직
     */

    // 반복 날짜 추출(weekly 일 때)
    public ArrayList<LocalDate> searchRepetitionWeekly(ReminderDto reminderRequestDto) {
        ArrayList<LocalDate> repetitionDate = new ArrayList<>();

        List<LocalDate> allDate = findAllDate(reminderRequestDto);
        ArrayList<Integer> repetitionNumberList = findByRepetitionDate(reminderRequestDto);
        // 해당 날짜 사이에 반복요일에 해당하는 날짜 추출
        for (LocalDate date : allDate){
            int dateWeek = date.getDayOfWeek().getValue();

            if (repetitionNumberList.contains(dateWeek)) {
                repetitionDate.add(date);
            }
        }
        return repetitionDate;
    }

    // 빈복 요일 추출
    public ArrayList<Integer> findByRepetitionDate(ReminderDto reminderRequestDto) {
        String repetitionDayString = reminderRequestDto.getRepetitionDay();
        String[] split = repetitionDayString.split(" ");
        ArrayList<String> repetitionDayList = new ArrayList<>(Arrays.asList(split));
        ArrayList<Integer> repetitionNumberList = new ArrayList<>();

        for (RepetitionDay repetitionDay : RepetitionDay.values()) {
            if (repetitionDayList.contains(repetitionDay.getDay())) {
                repetitionNumberList.add(repetitionDay.getNumber());
            }
        }
        return repetitionNumberList;
    }

    /**
     * monthly, daily, yearly 빈복 로직
     */

    // daily 날짜 추출
    public ArrayList<LocalDate> searchRepetitionDaily(ReminderDto reminderRequestDto) {

        List<LocalDate> allDate = findAllDate(reminderRequestDto);

        return new ArrayList<>(allDate);
    }

    // monthly 날짜 추출
    public ArrayList<LocalDate> searchRepetitionMonthly(ReminderDto reminderRequestDto) {
        ArrayList<LocalDate> repetitionMonthly = new ArrayList<>();

        LocalDate startDate = reminderRequestDto.getStartDate().toLocalDate();
        LocalDate endDate = reminderRequestDto.getEndDate().toLocalDate();
        repetitionMonthly.add(startDate);

        // 시작일을 기준으로 1달씩 올리면서 마지막일보다 커지면 종료
        while (endDate.isAfter(startDate)) {
            LocalDate plusMonths = startDate.plusMonths(1);
            if (endDate.isAfter(plusMonths)) {
                repetitionMonthly.add(plusMonths);
            }
            startDate = plusMonths;

        }

        return repetitionMonthly;
    }


    // yearly 날짜 추출
    public ArrayList<LocalDate> searchRepetitionYearly(ReminderDto reminderRequestDto) {
        ArrayList<LocalDate> repetitionYearly = new ArrayList<>();

        LocalDate startDate = reminderRequestDto.getStartDate().toLocalDate();
        LocalDate endDate = reminderRequestDto.getEndDate().toLocalDate();
        repetitionYearly.add(startDate);

        // 시작일을 기준으로 1년씩 올리면서 마지막일보다 커지면 종료
        while (endDate.isAfter(startDate)) {
            LocalDate plusYears = startDate.plusYears(1);
            if (endDate.isAfter(plusYears)) {
                repetitionYearly.add(plusYears);
            }
            startDate = plusYears;
        }
        return repetitionYearly;
    }
}
