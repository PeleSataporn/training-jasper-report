package com.example.jasper_report.report;

import com.example.jasper_report.report.model.JasperReportReqDto;
import com.example.jasper_report.report.model.ReportDataReqDto;
import com.google.zxing.WriterException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
public class ReportService {

    public InputStreamResource generateReport()
            throws IOException, JRException, WriterException {

        val data = ReportDataReqDto.builder().build();

        val params = new HashMap<String, Object>();
        params.put(JRParameter.REPORT_LOCALE, new Locale("th"));

        val filePath = "report/job_order_qr/training_report.jrxml";
        val jasperXmlInputStream = new ClassPathResource(filePath).getInputStream();
        val jasperDesign = JRXmlLoader.load(jasperXmlInputStream);
        val jasperReport = JasperCompileManager.compileReport(jasperDesign);

        return processStream(
                jasperReport, setReportData(data), params);
    }

    public JasperReportReqDto setReportData(
            ReportDataReqDto data)
            throws WriterException {
        return new JasperReportReqDto();
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
