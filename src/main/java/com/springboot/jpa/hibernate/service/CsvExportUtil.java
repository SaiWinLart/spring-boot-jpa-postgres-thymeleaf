package com.springboot.jpa.hibernate.service; 
 
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.CSVWriter;
import com.springboot.jpa.hibernate.model.CsvUserDto; 

@Component
public class CsvExportUtil {

    public void writeUsersToCsv(PrintWriter writer, List<CsvUserDto> users) {
        CSVWriter csvWriter = new CSVWriter(writer);

        // Dynamically generate header from User class fields
        Field[] fields = CsvUserDto.class.getDeclaredFields();
        String[] header = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            header[i] = fields[i].getName();
        }
        csvWriter.writeNext(header);

        // Write user data
        for (CsvUserDto user : users) {
            String[] data = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                try {
                    data[i] = fields[i].get(user).toString();
                } catch (IllegalAccessException e) {
                    data[i] = "";
                }
            }
            csvWriter.writeNext(data);
        }
        try {
			csvWriter.close();
		} catch (IOException e) { 
			e.printStackTrace();
		}
    }
}
