package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.exceptions.ReportGenerationException;
import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@Service
public class ReportService {
    private final DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);


    public ReportService(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void generateReport(OutputStream outputStream,int id) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/reports/cvCanada.jrxml")) {
            // Define parameter values
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("id", id);
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//            // Create a Word exporter
//            JRDocxExporter exporter = new JRDocxExporter();
//            // Set input and output
//            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        } catch (JRException e) {
            logger.error("Error compiling or filling the Jasper report", e);
            throw new ReportGenerationException("Failed to generate report", e);
        } catch (SQLException e) {
            logger.error("Database error while generating the report", e);
            throw new ReportGenerationException("Database error occurred", e);
        }

    }
}
