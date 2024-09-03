package com.msn.scigenics.report.api.config;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.util.JRSaver;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SimpleReportFiller {

    private String reportFileName;

    private JasperReport jasperReport;


    private String basePath = "C:\\gitrepo\\reports-templates\\reports";
    private JasperPrint jasperPrint;



    private HashMap<String,JasperReport> reportHashMap = new HashMap<String, JasperReport>();

    private Map<String, Object> parameters;

    public SimpleReportFiller() {
        parameters = new HashMap<>();
    }


    @PostConstruct
    public void compileReport() {


        try {
           Collection<File> fileCollection =  FileUtils.listFiles(new File(this.basePath), new String[]{"jrxml"}, true);


            InputStream reportStream = null;

            for(File reportFiles:fileCollection) {
                reportStream = new FileInputStream(reportFiles);
                JasperReport   jasperReport = JasperCompileManager.compileReport(reportStream);
               JRSaver.saveObject(jasperReport, reportFiles.getName().replace(".jrxml", ".jasper"));
               reportHashMap.put(reportFiles.getAbsolutePath(),jasperReport);
           }

            //InputStream reportStream = null;

            /* reportStream = getClass().getClassLoader().getResourceAsStream("Blank_A4_14.jrxml");

            JRSaver.saveObject(JasperCompileManager.compileReport(reportStream), "Blank_A4_14.jrxml".replace(".jrxml", ".jasper"));

             reportStream = getClass().getClassLoader().getResourceAsStream("Blank_A4_15.jrxml");

            JRSaver.saveObject(JasperCompileManager.compileReport(reportStream), "Blank_A4_15.jrxml".replace(".jrxml", ".jasper"));

            reportStream = getClass().getClassLoader().getResourceAsStream("Blank_A4_17.jrxml");

            JRSaver.saveObject(JasperCompileManager.compileReport(reportStream), "Blank_A4_17.jrxml".replace(".jrxml", ".jasper"));
*/
           //  reportStream = this.getClass().getClassLoader().getResourceAsStream("Blank_A4_13.jrxml");
            //jasperReport = JasperCompileManager.compileReport(reportStream);
           // JRSaver.saveObject(jasperReport, "Blank_A4_13.jrxml".replace(".jrxml", ".jasper"));
        } catch (JRException | FileNotFoundException ex) {
            Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fillReport(String reportData,String fermenter,String processType) {

        try {
            JsonDataSource dataSource = new JsonDataSource(this.getClass().getClassLoader().getResourceAsStream(reportData), "com/msn/scigenics/report/api");
            String key = reportHashMap.keySet().stream().filter(p-> p.indexOf(fermenter) >= 0 && p.indexOf(processType) > 0 && p.indexOf("master") > 0 ).findFirst().get();
            jasperPrint = JasperFillManager.fillReport(reportHashMap.get(key), parameters, dataSource);
        } catch (JRException e) {
            e.printStackTrace();
        }

    }



    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getReportFileName() {
        return reportFileName;
    }

    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

}