package com.msn.scigenics.report.api.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.msn.scigenics.report.api.config","com.msn.scigenics.report.api.controller,com.msn.scigenics.report"})
public class DemoApplication  {

    public static void main(String[] args) {


        SpringApplication.run(DemoApplication.class, args);
    }

   /* @Override
    public void run(String... args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(JasperRerportsSimpleConfig.class);
        ctx.refresh();

        SimpleReportFiller simpleReportFiller = ctx.getBean(SimpleReportFiller.class);
        simpleReportFiller.setReportFileName("sampleJavareport.jrxml");
        simpleReportFiller.compileReport();



        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", "Employee Report Example");
        parameters.put("minSalary", 15000.0);
        parameters.put("condition", " LAST_NAME ='Smith' ORDER BY FIRST_NAME");

        simpleReportFiller.setParameters(parameters);
        simpleReportFiller.fillReport("datasource.json","F1","PHT");

        SimpleReportExporter simpleExporter = ctx.getBean(SimpleReportExporter.class);
        simpleExporter.setJasperPrint(simpleReportFiller.getJasperPrint());

        simpleExporter.exportToPdf("msn_", "baeldung");
        simpleExporter.exportToXlsx("employeeReport.xlsx", "Employee Data");
        simpleExporter.exportToCsv("employeeReport.csv");
        simpleExporter.exportToHtml("employeeReport.html");


    }*/

}
