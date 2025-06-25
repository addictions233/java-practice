package com.one.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class DateDTO {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public DateDTO(LocalDate date) {
        this.date = date;
    }

    public DateDTO() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
