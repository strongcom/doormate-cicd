package com.example.doormate.controller;

import com.example.doormate.domain.Message;
import com.example.doormate.domain.Reminder;
import com.example.doormate.dto.ReminderDto;
import com.example.doormate.service.AlarmService;
import com.example.doormate.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reminder")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;
    private final AlarmService alarmService;

    @PostMapping
    @ResponseBody
    public Message create(@RequestBody ReminderDto reminderRequestDto) {
        Long savedReminderId = reminderService.saveReminder(reminderRequestDto);
        Message message = alarmService.saveAlarm(savedReminderId);
        return message;
    }


    @PutMapping("/{id}")
    @ResponseBody
    public Message update(@PathVariable Long id, @RequestBody ReminderDto reminderDto) {
        Long savedReminder = reminderService.updateReminder(id, reminderDto);
        alarmService.deleteAlarm(id);
        alarmService.saveAlarm(savedReminder);
        return new Message("알람 수정 완료");
    }

    @DeleteMapping("/{id}")
    public Message delete(@PathVariable(name = "id") Long id) {
        Message message = reminderService.deleteReminder(id);
        return message;
    }


    @GetMapping("/individual")
    @ResponseBody
    public void findOneDay() {
        // 당일 날짜에 해당하는 알림 조회(알림 테이블에서 찾기)
        // 찾은 알림 테이블의 리마인더 아이디를 찾기
        // 찾은 리마인더 아이디를 기반으로 리마인더 정보 가져오기(제목, 울리는 시간)
    }

    @GetMapping()
    @ResponseBody
    public List<Reminder> findAll() {
        return reminderService.findAllReminder();
    }



}
