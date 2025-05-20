package com.example.jasper_report.report;

import com.google.zxing.WriterException;
import lombok.val;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/training-jasper")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/report")
    public ResponseEntity<InputStreamResource> report() throws IOException, JRException {
        val pdfStream = reportService.generateReport();

        val headers = new HttpHeaders();
        headers.add(
                HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=training_report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfStream);
    }
}
