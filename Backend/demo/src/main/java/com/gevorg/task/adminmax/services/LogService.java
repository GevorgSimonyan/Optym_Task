package com.gevorg.task.adminmax.services;

import com.gevorg.task.adminmax.models.Log;
import com.gevorg.task.adminmax.models.LogsResultModel;
import com.gevorg.task.adminmax.models.User;
import com.gevorg.task.adminmax.repositories.LogRepository;
import com.gevorg.task.adminmax.repositories.UserRepository;
import com.gevorg.task.adminmax.utilitis.Util;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class LogService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    LogRepository logRepository;

    @Autowired
    UserRepository userRepository;


//    public Optional<User> findById(String id) {
//        return logRepository.findById(id);
//    }
//
//    public User findUserByEmail(String email) {
//        return repository.findByEmail(email);
//    }


    public LogsResultModel getLogs(int pageNo, int pageSize, String sortBy, int sortDirection, String email, Map<String, String> parameters) {


        LogsResultModel result = new LogsResultModel();
        Pageable paging = Util.getPagging(pageNo, pageSize, sortBy, sortDirection);


        Query query = new Query();
        query.with(paging);

        if (email != null && !email.isEmpty()) {
            User user = userRepository.findByEmail(email);
            query.addCriteria(Criteria.where("user").is(user));
        }

        Criteria dateCreteria = Criteria.where("createdOn");
        String startDateString = parameters.get("startDate");
        String endDateString = parameters.get("endDate");

        Date startDate = null;
        if (startDateString != null) {
            try {
                startDate = new Date(Long.valueOf(startDateString));
                dateCreteria.gte(startDate);
            } catch (Exception e) {
                startDate = null;
            }

        }
        Date endDate = null;
        if (endDateString != null) {
            try {
                endDate = new Date(Long.valueOf(endDateString));
                dateCreteria.lt(endDate);
            } catch (Exception e) {
                endDate = null;
            }
        }

        if (startDate != null || endDate != null) {
            query.addCriteria(dateCreteria);
        }


        List<Log> list = mongoTemplate.find(query, Log.class);
        Page<Log> pagedResult = PageableExecutionUtils.getPage(list, paging,
                () -> mongoTemplate.count(query, Log.class));

        result.setTotalCount(pagedResult.getTotalPages());
        if (pagedResult.hasContent()) {
            result.setLogs(pagedResult.getContent());

        } else {
            result.setLogs(new ArrayList<Log>());
        }

        return result;
    }


    public ByteArrayInputStream exportLogsToCSV() {
        List<Log> logsList = mongoTemplate.findAll(Log.class);


        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {

            List<String> headerData = Arrays.asList(
                    String.valueOf("ID"),
                    "DATE",
                    "LOG"
            );

            csvPrinter.printRecord(headerData);

            for (Log log : logsList) {
                List<String> data = Arrays.asList(
                        String.valueOf(log.getId()),
                        log.getCreatedOn().toString(),
                        log.getLogText()
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }

    }


    public ByteArrayInputStream exportLogs() {

        List<Log> logsList = mongoTemplate.findAll(Log.class);

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Logs");

        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Date");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Log");
        headerCell.setCellStyle(headerStyle);


        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        for (Log log : logsList) {

            Row row = sheet.createRow(2);
            Cell cell = row.createCell(0);
            cell.setCellValue(log.getCreatedOn().toString());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(log.getLogText());
            cell.setCellStyle(style);
        }

        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
