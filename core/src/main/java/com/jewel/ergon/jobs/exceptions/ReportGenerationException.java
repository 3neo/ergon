package com.jewel.ergon.jobs.exceptions;

import net.sf.jasperreports.engine.JRException;

public class ReportGenerationException extends RuntimeException {
    public ReportGenerationException(String failedToGenerateReport, Throwable cause) {
    }
}
