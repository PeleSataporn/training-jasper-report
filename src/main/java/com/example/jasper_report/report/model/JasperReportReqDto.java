package com.example.jasper_report.report.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class JasperReportReqDto {
    String address;
    String company;
    String customerName;
    String customerAddress;
    String quoteNo;
    Date quoteDate;
    Date dueDate;
    List<ItemDataReqDto> item;
    BigDecimal subtotal;
    BigDecimal vat;
    BigDecimal total;
}
