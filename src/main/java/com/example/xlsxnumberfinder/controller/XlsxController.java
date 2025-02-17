package com.example.xlsxnumberfinder.controller;

import com.example.xlsxnumberfinder.service.XlsxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api")
@Tag(name = "Excel Number Finder", description = "API для поиска N-ного максимального числа в файле Excel")
public class XlsxController {

    private final XlsxService xlsxService;

    @Autowired
    public XlsxController(XlsxService xlsxService) {
        this.xlsxService = xlsxService;
    }

    @PostMapping("/findNthMax")
    @Operation(summary = "Найти N-ное максимальное число в файле Excel")
    public ResponseEntity<String> findNthMax(
            @Parameter(description = "Путь к файлу Excel") @RequestParam String filePath,
            @Parameter(description = "Позиция максимального числа (N)") @RequestParam long numberOfMaxValue) {

        try {
            String result = xlsxService.findNthMaxValueInFile(filePath, numberOfMaxValue);
            return ResponseEntity.ok(result);
        } catch (FileNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}
