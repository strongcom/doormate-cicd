package com.example.doormate.domain;

public enum RepetitionDay {
    MON(1, "MON"),
    TUE(2, "TUE"),
    WED(3, "WED"),
    THUR(4, "THUR"),
    FRI(5, "FRI"),
    SAT(6, "SAT"),
    SUN(7, "SUN");

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
