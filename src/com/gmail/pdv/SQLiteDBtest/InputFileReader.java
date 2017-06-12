package com.gmail.pdv.SQLiteDBtest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Class is responsible for reading from input.txt - text file with path to database and sql requests
//it store database name and path in dbName
//all sql request are store in List<StringBuilder> sqlStmtList
//for separating sql requests symbols ";" is used


public class InputFileReader {

    private static String dbName;
    private static final List<StringBuilder> sqlStmtList = new ArrayList<>(); 
    
    //<editor-fold defaultstate="collapsed" desc="getters">
    public static String getDbName() {
        return dbName;
    }
    
    public static List<StringBuilder> getSqlStmtList() {
        return sqlStmtList;
    }
    
//</editor-fold>
    
    public static void readTask(String inputTaskFile) {
        
        checkTaskFilePath(inputTaskFile);

               
        try (FileReader fr = new FileReader(inputTaskFile);
                BufferedReader br = new BufferedReader(fr)) {
            
            dbName = br.readLine(); //read first line - should be Data Base path
            System.out.println("Data base path: " + dbName);
            checkDBFilePath ();
            
            int sqlStmntCounter = 0; //counter for sql statements in the input file
            
            //reading sql statements in the ArrayList
            while (true){
                
                String tmpString = br.readLine();
                if (tmpString == null) // end of file
                    break;
                
                StringBuilder tmp = new StringBuilder (tmpString);
                sqlStmtList.add(sqlStmntCounter, tmp); //add first line to new element of ArrayList
                //reading balance lines of sql statement and append it to current element of ArrayList
                while (!sqlStmtList.get(sqlStmntCounter).toString().endsWith(";")){
                    sqlStmtList.get(sqlStmntCounter).append(br.readLine());
                }
                sqlStmntCounter++; //increment statement counter
                
                
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void checkTaskFilePath(String inputTaskFile) {
        File taskFile = new File(inputTaskFile);
        if (!taskFile.exists()){
            System.out.println("Sorry the file with a task to perform does not exist");
            System.exit(0);
        }
    }
    
    private static void checkDBFilePath() { //trying to connect to DB and sending empty sql query to validate connection

        int index = dbName.indexOf(":");
        String path = dbName.substring(index + 1);
        File dbFile = new File(path);
       
        if (! (dbFile.exists()) ) {
            System.out.println("\nSorry the specified database file does not exist");
            System.exit(0);
        }
    }
    

}
    
