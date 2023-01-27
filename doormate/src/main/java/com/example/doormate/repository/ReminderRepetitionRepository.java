package com.example.doormate.repository;

import com.example.doormate.domain.RepetitionDay;
import com.example.doormate.dto.ReminderRequestDto;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReminderRepetitionRepository{

    // 이렇게 찾은 데이터를 어떻게 합칠까...?


    /*
     * 반복 등록 로직(start - end Date 가공 후, DB에 올리기) _ common
     */

    // startDate - endDate 사이의 모든 날짜 추출
    public List<LocalDate> findAllDate(ReminderRequestDto reminderRequestDto) {
        LocalDate startDate = reminderRequestDto.getStartDate().toLocalDate();
        LocalDate endDate = reminderRequestDto.getEndDate().toLocalDate();

        return startDate.datesUntil(endDate)
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
    public ArrayList<LocalDate> searchRepetitionWeekly(ReminderRequestDto reminderRequestDto) {
        ArrayList<LocalDate> repetitionDate = new ArrayList<>();

        List<LocalDate> allDate = findAllDate(reminderRequestDto);

        // 해당 날짜 사이에 반복요일에 해당하는 날짜 추출
        for (LocalDate date : allDate){
            int dateWeek = date.getDayOfWeek().getValue();
            ArrayList<Integer> repetitionNumberList = findByRepetitionDate(reminderRequestDto);
            if (repetitionNumberList.contains(dateWeek)) {
                repetitionDate.add(date);
            }
        }
        return repetitionDate;
    }

    // 빈복 요일 추출
    public ArrayList<Integer> findByRepetitionDate(ReminderRequestDto reminderRequestDto) {
        String repetitionDayString = reminderRequestDto.getRepetitionDay();
        ArrayList<String> repetitionDayList = new ArrayList<>(Arrays.asList(repetitionDayString));
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
    public ArrayList<LocalDate> searchRepetitionDaily(ReminderRequestDto reminderRequestDto) {

        List<LocalDate> allDate = findAllDate(reminderRequestDto);

        return new ArrayList<>(allDate);
    }

    // monthly 날짜 추출
    public ArrayList<LocalDate> searchRepetitionMonthly(ReminderRequestDto reminderRequestDto) {
        ArrayList<LocalDate> repetitionMonthly = new ArrayList<>();

        LocalDate startDate = reminderRequestDto.getStartDate().toLocalDate();
        LocalDate endDate = reminderRequestDto.getEndDate().toLocalDate();

        // 시작일을 기준으로 1달씩 올리면서 마지막일보다 커지면 종료
        while (LocalDate.EPOCH.isAfter(endDate)) {
            LocalDate plusMonths = startDate.plusMonths(1);
            repetitionMonthly.add(plusMonths);
        }

        return repetitionMonthly;
    }


    // yearly 날짜 추출
    public ArrayList<LocalDate> searchRepetitionYearly(ReminderRequestDto reminderRequestDto) {
        ArrayList<LocalDate> repetitionYearly = new ArrayList<>();

        LocalDate startDate = reminderRequestDto.getStartDate().toLocalDate();
        LocalDate endDate = reminderRequestDto.getEndDate().toLocalDate();

        // 시작일을 기준으로 1년씩 올리면서 마지막일보다 커지면 종료
        while (LocalDate.EPOCH.isAfter(endDate)) {
            LocalDate plusYears = startDate.plusYears(1);
            repetitionYearly.add(plusYears);
        }
        return repetitionYearly;
    }
}
