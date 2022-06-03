package com.example.csv;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVFileGenerator implements CommandLineRunner {

    /**
     * https://howtodoinjava.com/java/library/supercsv-parse-read-write-csv/
     * https://www.youtube.com/watch?v=yacZ7B73M30&t=349s
     * */

    public CellProcessor[] getProcessors() {
        return new CellProcessor[] {
                new ConvertNullTo("NULL"),
                new ConvertNullTo("NULL"),
                new ConvertNullTo("NULL")
        };
    }

    @Override
    public void run(String... args) throws IOException {

        System.out.println("CSV File generation start...");

        List<Report> reports = new ArrayList<>();

        reports.add(new Report(1234, 101, "Store1"));
        reports.add(new Report(1235, 102, "Store2"));
        reports.add(new Report(1236, null, "Store3"));

        ICsvBeanWriter iCsvBeanWriter = new CsvBeanWriter(new FileWriter("report.csv"), CsvPreference.STANDARD_PREFERENCE);

        String[] header = {"Store_Number", "Tag_Id", "Store_Name"};
        String[] fields = {"storeNbr", "tagId", "storeName"};

        iCsvBeanWriter.writeHeader(header);
        CellProcessor[] processors = getProcessors();

        for (Report report : reports)
            iCsvBeanWriter.write(report, fields, processors);

        iCsvBeanWriter.close();

        System.out.println("Done...");

        // Deletes the file
        File file = new File("report.csv");
        if (file.delete())
            System.out.println("File deleted successfully");
        else
            System.out.println("Unable to delete file");
    }
}
