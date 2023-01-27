package com.example.doormate.controller;

import com.example.doormate.domain.Reminder;
import com.example.doormate.dto.ReminderRequestDto;
import com.example.doormate.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reminder")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @PostMapping
    public ResponseEntity<Reminder> createReminder(@RequestBody ReminderRequestDto reminderRequestDto) {
        Reminder savedReminder = reminderService.save(reminderRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(savedReminder);
    }
}
