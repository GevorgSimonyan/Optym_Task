package com.gevorg.task.adminmax.controllers;

import com.gevorg.task.adminmax.models.AuthBody;
import com.gevorg.task.adminmax.models.Log;
import com.gevorg.task.adminmax.models.LogsResultModel;
import com.gevorg.task.adminmax.services.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/log")
@Api(value = "/api/log", tags = {"Api logs"}, description = "Logs", produces = "application/json")
public class LogsController extends BaseController {

    @Autowired
    LogService logService;


    @GetMapping("logs")
    public ResponseEntity<LogsResultModel> logs(@RequestParam(defaultValue = "0") Integer pageNo,
                                                @RequestParam(defaultValue = "10") Integer pageSize,
                                                @RequestParam(defaultValue = "createdOn") String sortBy,
                                                @ApiParam(value = "Sort Direction 1 is ASC -1 is Desc")
                                                @RequestParam(defaultValue = "1") Integer sortDirection,
                                                @RequestParam(defaultValue = "") String email,
                                                @RequestParam(required = false) Map<String, String> parameters) {

        LogsResultModel result = logService.getLogs(pageNo, pageSize, sortBy, sortDirection, email, parameters);

        return OK(result);
    }

    @GetMapping("exportLogs")
    public ResponseEntity<Resource> exportLogs() {

        String filename = "logs.csv";
        InputStreamResource file = new InputStreamResource(logService.exportLogsToCSV());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }


}
