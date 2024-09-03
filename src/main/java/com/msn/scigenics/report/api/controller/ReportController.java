package com.msn.scigenics.report.api.controller;


import com.msn.scigenics.report.ReportGenApp;
import com.msn.scigenics.report.api.config.SimpleReportExporter;
import com.msn.scigenics.report.api.config.SimpleReportFiller;
import com.msn.scigenics.report.api.model.ReportModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    @Autowired
    private SimpleReportFiller reportFiller;

    @Autowired
    private SimpleReportExporter simpleExporter;

    @Autowired
    private ReportGenApp genApp;

    @PostMapping("/generatePDF")
    public void generatePdf(@RequestBody ReportModel model) throws Exception {

        genApp.run(model.getFermenter(),model.getProcessType(), model.getJsonkey());
        reportFiller.fillReport(model.getJsonkey(),model.getFermenter(),model.getProcessType());

        simpleExporter.setJasperPrint(reportFiller.getJasperPrint());

        simpleExporter.exportToPdf("msn.pdf", "baeldung");

    }
}
