package com.example.doormate.controller;

import com.example.doormate.domain.Reminder;
import com.example.doormate.dto.ReminderDto;
import com.example.doormate.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reminder")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @PostMapping
    @ResponseBody
    public String createReminder(@RequestBody ReminderDto reminderRequestDto) {
        Reminder reminder = reminderRequestDto.toEntity(reminderRequestDto);
        if (reminder.getRepetitionPeriod() != null) {
            reminderService.createRepetitionReminder(reminderRequestDto);
        } else {
            reminderService.create(reminder);
        }

        return "200 OK";
    }
}
