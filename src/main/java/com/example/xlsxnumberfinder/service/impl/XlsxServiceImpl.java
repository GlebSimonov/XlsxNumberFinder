package com.example.xlsxnumberfinder.service.impl;

import com.example.xlsxnumberfinder.service.XlsxService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.PriorityQueue;

@Service
public class XlsxServiceImpl implements XlsxService {

    @Override
    public String findNthMaxValueInFile(String filePath, long numberOfMaxValue) throws IOException {
        if (numberOfMaxValue <= 0) {
            throw new IllegalArgumentException("Значение N должно быть больше 0.");
        }

        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new FileNotFoundException("Файл не найден: " + filePath);
        }

        PriorityQueue<Long> minHeap = new PriorityQueue<>();

        try (Workbook workbook = new XSSFWorkbook(Files.newInputStream(path))) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    long num = (long) cell.getNumericCellValue();
                    if (!minHeap.contains(num)) {
                        minHeap.offer(num);
                    }
                    if (minHeap.size() > numberOfMaxValue) {
                        minHeap.poll();
                    }
                }
            }
        }

        if (minHeap.size() == 1 && numberOfMaxValue != 1) {
            throw new IllegalArgumentException("В таблице все значения равны");
        }

        if (minHeap.size() < numberOfMaxValue) {
            throw new IllegalArgumentException("Такого значения не существует при параметре " + numberOfMaxValue);
        }

        return String.valueOf(minHeap.peek());
    }
}
