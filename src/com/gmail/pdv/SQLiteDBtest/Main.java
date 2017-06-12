package com.gmail.pdv.SQLiteDBtest;

public class Main {

    public static void main(String[] args) {
        
        InputFileReader.readTask("files/input.txt");
        ResultsWriter.saveResultsToFile("files/execution_results.txt");
        DBManager.startSQLExecution();
        
    }
}
