package org.example;
/*
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;

public class PrintOutAllLists {
    public void printAllLists(String csvFilePath) {
        try {
            CSVParser csvParser = getCsvParser(csvFilePath);

            if (csvParser != null) {
                printAllRecords(csvParser);
                csvParser.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CSVParser getCsvParser(String filePath) {
        try {
            return new CSVParser(new FileReader(filePath), CSVFormat.DEFAULT.withHeader());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void printAllRecords(CSVParser csvParser) {
        for (CSVRecord record : csvParser) {
            printRecord(record, csvParser.getHeaderNames());
            System.out.println("------------"); // Separator between records
        }
    }

    private void printRecord(CSVRecord record, Iterable<String> headers) {
        for (String header : headers) {
            System.out.println(header + ": " + record.get(header));
        }
    }
}
*/
