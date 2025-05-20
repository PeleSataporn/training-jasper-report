package com.example.jasper_report.report;

import com.example.jasper_report.report.model.ItemDataReqDto;
import com.example.jasper_report.report.model.JasperReportReqDto;
import lombok.val;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ReportService {

    public InputStreamResource generateReport()
            throws IOException, JRException {

        val params = new HashMap<String, Object>();
        params.put(JRParameter.REPORT_LOCALE, new Locale("th"));

        val filePath = "report/training_report.jrxml";
        val jasperXmlInputStream = new ClassPathResource(filePath).getInputStream();
        val jasperDesign = JRXmlLoader.load(jasperXmlInputStream);
        val jasperReport = JasperCompileManager.compileReport(jasperDesign);

        return processStream(
                jasperReport, setReportData(), params);
    }

    public JasperReportReqDto setReportData() {

        val data = new JasperReportReqDto();
        data.setCompany("Your Company Inc.");
        data.setAddress("1234 Company St, Company Town, ST 12345");
        data.setCustomerName("Customer Name");
        data.setCustomerAddress("1234 Company St, Company Town, ST 12345");
        data.setQuoteNo("0000226");
        data.setQuoteDate(new Date());
        data.setDueDate(new Date());

        val items = List.of(
                new ItemDataReqDto(1, "HVAC system inspection", new BigDecimal("150.00"), new BigDecimal("150.00")),
                new ItemDataReqDto(2, "Replacement air filters", new BigDecimal("25.00"), new BigDecimal("50.00")),
                new ItemDataReqDto(3, "Duct cleaning service", new BigDecimal("75.00"), new BigDecimal("225.00")),
                new ItemDataReqDto(1, "Thermostat installation", new BigDecimal("120.00"), new BigDecimal("120.00")),
                new ItemDataReqDto(5, "Ventilation check", new BigDecimal("80.00"), new BigDecimal("400.00")),
                new ItemDataReqDto(2, "Filter replacement", new BigDecimal("30.00"), new BigDecimal("60.00")),
                new ItemDataReqDto(3, "System tuning", new BigDecimal("90.00"), new BigDecimal("270.00")),
                new ItemDataReqDto(1, "Fan installation", new BigDecimal("100.00"), new BigDecimal("100.00")),
                new ItemDataReqDto(4, "Pipe cleaning", new BigDecimal("40.00"), new BigDecimal("160.00")),
                new ItemDataReqDto(2, "Duct sealant", new BigDecimal("55.00"), new BigDecimal("110.00")),
                new ItemDataReqDto(1, "HVAC system inspection", new BigDecimal("150.00"), new BigDecimal("150.00")),
                new ItemDataReqDto(2, "Replacement air filters", new BigDecimal("25.00"), new BigDecimal("50.00")),
                new ItemDataReqDto(3, "Duct cleaning service", new BigDecimal("75.00"), new BigDecimal("225.00")),
                new ItemDataReqDto(1, "Thermostat installation", new BigDecimal("120.00"), new BigDecimal("120.00")),
                new ItemDataReqDto(5, "Ventilation check", new BigDecimal("80.00"), new BigDecimal("400.00")),
                new ItemDataReqDto(2, "Filter replacement", new BigDecimal("30.00"), new BigDecimal("60.00")),
                new ItemDataReqDto(3, "System tuning", new BigDecimal("90.00"), new BigDecimal("270.00")),
                new ItemDataReqDto(1, "Fan installation", new BigDecimal("100.00"), new BigDecimal("100.00")),
                new ItemDataReqDto(4, "Pipe cleaning", new BigDecimal("40.00"), new BigDecimal("160.00")),
                new ItemDataReqDto(2, "Duct sealant", new BigDecimal("55.00"), new BigDecimal("110.00")),
                new ItemDataReqDto(1, "HVAC system inspection", new BigDecimal("150.00"), new BigDecimal("150.00")),
                new ItemDataReqDto(2, "Replacement air filters", new BigDecimal("25.00"), new BigDecimal("50.00")),
                new ItemDataReqDto(3, "Duct cleaning service", new BigDecimal("75.00"), new BigDecimal("225.00")),
                new ItemDataReqDto(1, "Thermostat installation", new BigDecimal("120.00"), new BigDecimal("120.00")),
                new ItemDataReqDto(5, "Ventilation check", new BigDecimal("80.00"), new BigDecimal("400.00")),
                new ItemDataReqDto(2, "Filter replacement", new BigDecimal("30.00"), new BigDecimal("60.00")),
                new ItemDataReqDto(3, "System tuning", new BigDecimal("90.00"), new BigDecimal("270.00")),
                new ItemDataReqDto(1, "Fan installation", new BigDecimal("100.00"), new BigDecimal("100.00")),
                new ItemDataReqDto(4, "Pipe cleaning", new BigDecimal("40.00"), new BigDecimal("160.00")),
                new ItemDataReqDto(2, "Duct sealant", new BigDecimal("55.00"), new BigDecimal("110.00"))
        );

        data.setItem(items);
        data.setSubtotal(new BigDecimal("545.00"));
        data.setVat(new BigDecimal("27.25"));
        data.setTotal(new BigDecimal("575.25"));
        return data;
    }

    public InputStreamResource processStream(
            JasperReport compiledReport, Object req, HashMap<String, Object> params)
            throws JRException, IOException {

        val output = new ByteArrayOutputStream();

        try (output) {
            val print =
                    JasperFillManager.fillReport(
                            compiledReport, params, new JRBeanCollectionDataSource(List.of(req)));

            val exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(output));
            exporter.exportReport();
        }

        return new InputStreamResource(new ByteArrayInputStream(output.toByteArray()));
    }
}
