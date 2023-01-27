package com.example.doormate.domain;

public enum RepetitionDay {
    MON(1, "월요일"),
    TUE(2, "화요일"),
    WED(3, "수요일"),
    THUR(4, "목요일"),
    FRI(5, "금요일"),
    SAT(6, "토요일"),
    SUN(7, "일요일");

    private int number;
    private String day;

    RepetitionDay(int number, String day) {
        this.number = number;
        this.day = day;
    }

    public int getNumber() {
        return number;
    }

    public String getDay() {
        return day;
    }
}
