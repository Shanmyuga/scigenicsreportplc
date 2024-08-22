package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App implements CommandLineRunner


{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main( String[] args ) throws SQLException, ClassNotFoundException {

        SpringApplication.run(App.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

       String batchName = args[0];
       List<InputParams> keyParametersList = new ArrayList<InputParams>();

       for(int idx =1;idx<args.length;idx++) {
        String[] array = StringUtils.delimitedListToStringArray(args[idx],":");
        InputParams params = new InputParams();

        params.setBlock(array[1]);
        params.setSource(array[0]);
        params.setBlockDesc(array[2]);
           keyParametersList.add(params);

       }

      List<BatchHeaderData> batchHeaderDataList =  jdbcTemplate.query("SELECT       [USERPROCEDUREID]           ,[STARTTIME]      ,[ENDTIME]      ,[DURATION]                 ,[TAGNAME]  FROM [ProcedureAnalyst].[pa].[TPB_VW_PROC_EXEC] p  where p.USERPROCEDUREID = ?" , new PreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps) throws SQLException {
              ps.setString(1, batchName);

          }
      },new RowMapper<BatchHeaderData>() {

            public BatchHeaderData mapRow(ResultSet rs, int rowNum) throws SQLException {
                BatchHeaderData batchHeaderData = new BatchHeaderData();
                batchHeaderData.setBatchId(rs.getString(1));
                batchHeaderData.setStartDateTime(rs.getTimestamp(2));
                batchHeaderData.setEndDateTime(rs.getTimestamp(3));
                batchHeaderData.setDuration(rs.getString(4));
                //batchHeaderData.setProcessName("Pressure Hold Test");
                return batchHeaderData;
            }
        });
        List<BatchSetParams> params = jdbcTemplate.query("SELECT        distinct         [NAME]        ,[EU]               ,[VALUE]    FROM [ProcedureAnalyst].[pa].[TPB_VW_PROC_ATTR_RECIPE] p,      pa.[TPB_VW_PROC_EXEC] pr where pr.PROCEDUREEXECUTIONID = p.PROCEDUREEXECUTIONID and pr.USERPROCEDUREID = ?  ", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, batchName);

            }
        }, new RowMapper<BatchSetParams>() {

            @Override
            public BatchSetParams mapRow(ResultSet rs, int rowNum) throws SQLException {
                BatchSetParams params = new BatchSetParams();
                params.setBatchSetParamName(rs.getString(1));
                params.setBatchSetParamUnits(rs.getString(2));
                params.setBatchSetParamValue(rs.getString(3));
                return params;
            }
        });
        List<BatchStepData> totalList = new ArrayList<>();
        BatchHeaderData data = batchHeaderDataList.get(0);
        Calendar cal = Calendar.getInstance();
        cal.setTime(data.getStartDateTime());
        int idx = 1;
        List<BatchActualValues> previousActualList = new ArrayList();
        do{

            Timestamp prevTimestamp= new Timestamp(cal.getTimeInMillis());
            cal.add(Calendar.SECOND,1);
            Timestamp newTimeStamp = new Timestamp(cal.getTimeInMillis());
            //System.out.println(idx++);

          List<BatchStepData>  data1 = jdbcTemplate.query("    SELECT         [USERPROCEDUREID]               ,[STARTTIME]        ,[ENDTIME]        ,[DURATION]                       ,[TAGNAME]    FROM [ProcedureAnalyst].[pa].[TPB_VW_PROC_EXEC] p      where p.USERPROCEDUREID = ? and p.STARTTIME >= ? and P.STARTTIME < ?", new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1,batchName);
                    ps.setTimestamp(2, prevTimestamp);
                    ps.setTimestamp(3, newTimeStamp);
                }
            }, new RowMapper<BatchStepData>() {
                @Override
                public BatchStepData mapRow(ResultSet rs, int rowNum) throws SQLException {
                    BatchStepData data = new BatchStepData();
                    data.getActualValueMap().put("BatchStep",rs.getString(5));
                    data.getActualValueMap().put("logTime",rs.getString(2));
                    for(InputParams parameter:keyParametersList) {
                        data.getActualValueMap().put(parameter.getReportKey(), "");
                        //data.getActualValueMap().put("key",parameter.getReportKey());
                    }
                    return data;

                }


            });

            totalList.addAll(data1);
           for(InputParams params1:keyParametersList) {
               List<BatchActualValues> actualValues = jdbcTemplate.query("SELECT          [SOURCE]        ,[VALUE]        ,[ALARMLIMIT]        ,[UNITS]        ,[DESCRIPTION]        ,[Timestamp]            FROM [ProcedureAnalyst].[pa].[TPB_VW_PE_EVENTS] p      where p.BLOCK = ? and p.DESCRIPTION = ? and p.SOURCE = ? and p.TIMESTAMP >= ? and p.TIMESTAMP < ? order by p.TIMESTAMP asc", new PreparedStatementSetter() {
                   @Override
                   public void setValues(PreparedStatement ps) throws SQLException {
                       ps.setTimestamp(4, prevTimestamp);
                       ps.setTimestamp(5, newTimeStamp);
                       ps.setString(1,params1.getBlock());
                       ps.setString(3,params1.getSource());
                       ps.setString(2,params1.getBlockDesc().replaceAll("-","."));
                   }
               }, new RowMapper<BatchActualValues>() {
                   @Override
                   public BatchActualValues mapRow(ResultSet rs, int rowNum) throws SQLException {
                       BatchActualValues values = new BatchActualValues();
                       values.setActualProcessVariableName(rs.getString(1) + "." + rs.getString(5));
                       values.setGetActualProcessVariableValue(rs.getString(2));
                       values.setGetActualProcessVariableUnits(rs.getString(4));
                       values.setActualValueChangeTimeStamp(rs.getTimestamp(6));
                       return values;
                   }
               });
               if(idx == 1 && actualValues.size() == 0) {
                   actualValues = jdbcTemplate.query("SELECT     TOP (1)       [SOURCE]        ,[VALUE]        ,[ALARMLIMIT]        ,[UNITS]        ,[DESCRIPTION]        ,[Timestamp]            FROM [ProcedureAnalyst].[pa].[TPB_VW_PE_EVENTS] p      where p.BLOCK = ? and p.DESCRIPTION = ? and p.SOURCE = ? and p.TIMESTAMP  < ? and  p.value != '' order by p.TIMESTAMP desc", new PreparedStatementSetter() {
                       @Override
                       public void setValues(PreparedStatement ps) throws SQLException {
                           ps.setTimestamp(4, prevTimestamp);

                           ps.setString(1,params1.getBlock());
                           ps.setString(3,params1.getSource());
                           ps.setString(2,params1.getBlockDesc().replaceAll("-","."));
                       }
                   }, new RowMapper<BatchActualValues>() {
                       @Override
                       public BatchActualValues mapRow(ResultSet rs, int rowNum) throws SQLException {
                           BatchActualValues values = new BatchActualValues();
                           values.setActualProcessVariableName(rs.getString(1) + "." + rs.getString(5));
                           values.setGetActualProcessVariableValue(rs.getString(2));
                           values.setGetActualProcessVariableUnits(rs.getString(4));
                           values.setActualValueChangeTimeStamp(rs.getTimestamp(6));
                           return values;
                       }
                   });
               }

               if (actualValues.size() > 0) {
                   if (totalList.size() > 0 && totalList.size() ==1 && idx ==1) {
                       BatchStepData prevstepData = totalList.get(totalList.size() - 1);
                       for (BatchActualValues actualValues1 : actualValues) {
                           prevstepData.addtoMap(params1.getReportKey(),actualValues1.getGetActualProcessVariableValue());
                           BatchStepData stepData = new BatchStepData();

                           stepData.addtoMap("BatchStep", prevstepData.getActualValueMap().get("BatchStep"));
                           stepData.addtoMap("logTime", actualValues1.getActualValueChangeTimeStamp());
                           stepData.addtoMap(params1.getReportKey(), actualValues1.getGetActualProcessVariableValue());
                          // stepData.addtoMap("key",params1.getReportKey());
                           //totalList.add(stepData);
                       }
                   }
                   else {
                       if(totalList.size()>0 && idx != 1 ) {
                           BatchStepData prevstepData = totalList.get(totalList.size() - 1);
                           for (BatchActualValues actualValues1 : actualValues) {

                               BatchStepData stepData = new BatchStepData();

                               stepData.addtoMap("BatchStep", prevstepData.getActualValueMap().get("BatchStep"));
                               stepData.addtoMap("logTime", actualValues1.getActualValueChangeTimeStamp());
                               stepData.addtoMap(params1.getReportKey(), actualValues1.getGetActualProcessVariableValue());
                               // stepData.addtoMap("key",params1.getReportKey());
                             totalList.add(stepData);
                           }
                       }
                   }
               }
               else {

               }
           }
      /*   if(actualValues.size() >0 ) {
             previousActualList.clear();
             previousActualList.addAll(actualValues);
BatchActualValues actualValues1 = actualValues.get(actualValues.size()-1);
             for(BatchStepData stepData:data1) {

                     stepData.addActualParam(actualValues1);

             }
         }
         else {
             if(previousActualList.size() > 0 ) {
                 BatchActualValues actualValues1 = previousActualList.get(previousActualList.size()-1);
                 for(BatchStepData stepData:data1) {

                     stepData.addActualParam(actualValues1);

                 }
             }
         }
*/
           // totalList.addAll(data1);
            //System.out.println(totalList.size());
//code to be executed / loop body
//update statement
            idx++;
        }while (data.getEndDateTime().after(new Timestamp(cal.getTimeInMillis())));
        System.out.println(totalList);

        for(InputParams params1:keyParametersList) {
            String prevValue = null;
            for (BatchStepData steps : totalList) {
                String val = (String) steps.getActualValueMap().get(params1.getReportKey());
                if (val == null || "".equals(val)) {
                    if (prevValue != null && !"".equals(prevValue)) {
                        steps.getActualValueMap().put(params1.getReportKey(), prevValue);

                    }
                } else {
                    prevValue = val;
                }
            }
        }
        for(InputParams params1:keyParametersList) {
            String prevValue = null;
            for (BatchStepData steps : totalList) {
                if(steps.getActualValueMap().get(params1.getReportKey()) == null) {
                    steps.getActualValueMap().put(params1.getReportKey(),"");
                }
            }
        }


        ReportClass reportClass = new ReportClass();
        reportClass.setHeaderData(batchHeaderDataList.get(0));
        reportClass.setSetParamsList(params);
        reportClass.setStepDataList(totalList);
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        mapper.setDateFormat(df);
        String jsonData = mapper.writeValueAsString(reportClass);
        String finaljson = "{ \"report\":" + jsonData + " } ";
        System.out.println(finaljson);

    }



}
