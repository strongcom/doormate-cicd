package com.example.doormate.repository;

import com.example.doormate.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    @Modifying
    @Query("delete from Alarm alarm where alarm.reminder.reminderId = :id")
    void deleteAllByReminderReminderId(@Param("id") Long id);

    @Query("select alarm from Alarm alarm where alarm.noticeDate = :noticeDate")
    List<Alarm> findAllByNoticeDate(LocalDate noticeDate);

}
