package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public enum TicketType {
    CHILD(BigDecimal.valueOf(12.50)),
    STUDENT(BigDecimal.valueOf(18)),
    ADULT(BigDecimal.valueOf(25));
    public final BigDecimal price;
}
