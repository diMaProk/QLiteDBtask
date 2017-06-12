package com.gmail.pdv.SQLiteDBtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

//class is responsible for performing sql request that was read by InputFileReader class on the database

public class DBManager {

    public static void startSQLExecution() {
        
        try (Connection conn = DriverManager.getConnection("jdbc:" + InputFileReader.getDbName());
                Statement stmnt = conn.createStatement()) {
            
                     
            for (int i = 0; i < InputFileReader.getSqlStmtList().size(); i++) {
                //take each sql statement from the Statement List  
                String sqlQuery = InputFileReader.getSqlStmtList().get(i).toString();
                //create resultRecord that will store query + result of execution for writing to result file
                StringBuilder resultRecord = new StringBuilder("\nQuery number: " + (i+1) + "\n" + sqlQuery + "\nResult of execusion:\n");
                System.out.println(resultRecord);
                
                //check what type of statement was executed
                boolean isResultSet = stmnt.execute(sqlQuery);
                conn.setAutoCommit(true);
                
                if(isResultSet){ //if it was Select request - print out a result set
                    ResultSet rs = stmnt.getResultSet();
                    //get column count form result set
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();
                    
                    while(rs.next()){ //print each row of result set
                        for (int j = 1; j <= columnCount; j++){ // print each column of result set
                            String str = rs.getObject(j) + " | ";
                            System.out.print(str);
                            resultRecord.append(str); 
                            
                        }
                        System.out.println();
                        resultRecord.append("\n");
                    }
                    
                }else{ //if it was a modifications request - print out quantity of updated rows
                    String str = stmnt.getUpdateCount() + " rows were updated";
                    System.out.println(str);
                    resultRecord.append(str);
                }
                ResultsWriter.writeToFile(resultRecord.toString());
            }

        } catch (SQLException ex) {
           ex.printStackTrace();
        }
    }
}
