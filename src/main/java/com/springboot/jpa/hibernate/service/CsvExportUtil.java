package com.springboot.jpa.hibernate.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.CSVWriter;
import com.springboot.jpa.hibernate.model.CsvUserDto;
import com.springboot.jpa.hibernate.model.Role;
import com.springboot.jpa.hibernate.model.User;

@Component
public class CsvExportUtil {
	private static final CsvMapper mapper = new CsvMapper();

	// using com.opencsv.CSVWriter
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

	// using com.fast
	public static void writeUserListToCsv(List<CsvUserDto> csvUserDtoList, File file) throws IOException {
		CsvSchema schema = mapper.schemaFor(CsvUserDto.class).withHeader();
		mapper.writer(schema).writeValue(file, csvUserDtoList);
	}

	
	// using org.apache.commons.csv
	public void exportUsersToCsv(Writer writer, List<User> users) throws IOException {

		// Dynamically generate header from User class fields
		// skip the first field in User class>> serialVersionUID <<<<<
				Field[] fields = User.class.getDeclaredFields();
				String[] header = new String[fields.length];
				for (int i = 0; i < fields.length-1; i++) {
					header[i] = fields[i+1].getName();
				}
				
		try (CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.builder()
            //    .setHeader("id", "username", "password", "accountNonLocked", "roles", "failedAttemptCount", "needsPasswordChange")
              .setHeader(header)
				.build())) {
			for (User user : users) {
				printer.printRecord(user.getId(), user.getUsername(), user.getPassword(), user.isAccountNonLocked(),
						String.join(",", user.getRoles().stream().map(Role::getName).toList()),
						user.getFailedAttemptCount(), user.isNeedsPasswordChange()); 
			}
		}
	}  
}
	  
	  
