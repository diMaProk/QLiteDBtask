package com.gmail.pdv.SQLiteDBtest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultsWriter {

    private static File resFile;
    private static int writeCounter = 1;

    public static void saveResultsToFile(String path) {
        resFile = new File(path);
        
        //delete result file that was created by previous program run
        if (resFile.exists())
            resFile.delete();
    }

    public static void writeToFile(String resultStr) {
        try (FileWriter writer = new FileWriter(resFile, true)) {
            
            writer.write (resultStr);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
