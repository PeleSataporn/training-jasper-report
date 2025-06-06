package com.example.jasper_report.report.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDataReqDto {
    Integer qty;
    String description;
    BigDecimal price;
    BigDecimal amount;
}
