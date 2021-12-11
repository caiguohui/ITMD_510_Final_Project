package com.iit.caiguohui.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelUtil {

    /**
     * read excel data in the list
     */
    public static List<List<String>> readData(File file) {
        List<List<String>> result = new ArrayList<>();
        BufferedReader br = null;
        try {
            // initialize reader object and set file path to root of project
            br = new BufferedReader(new FileReader(file));
            String line;
            // read each record in csv file
            while ((line = br.readLine()) != null) {
                // parse each record in csv file by a comma ( , )
                // into a list stored in the arraylist-> Arrays
                result.add(Arrays.asList(line.split(",")));
            }
        } catch (Exception e) {
            throw new RuntimeException("read data error.", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    throw new RuntimeException("close buffer error.", e);
                }
            }
        }
        return result;
    }
}
