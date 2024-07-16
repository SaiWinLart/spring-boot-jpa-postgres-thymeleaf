package com.springboot.jpa.hibernate.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
public class CsvImportUtils {
	 private static final CsvMapper mapper = new CsvMapper();

	    public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
	        CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
	        MappingIterator<T> it = mapper.readerFor(clazz).with(schema).readValues(stream);
	        return it.readAll();
	    }
}