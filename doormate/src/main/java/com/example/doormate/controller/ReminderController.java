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
    public List<Reminder> findDay() {
        return alarmService.findTodayAlarm();
    }

    @GetMapping()
    @ResponseBody
    public List<Reminder> findAll() {
        return reminderService.findAllReminder();
    }

}
