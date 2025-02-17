package com.example.xlsxnumberfinder.service;

import java.io.IOException;

public interface XlsxService {
    /**
     * Находит N-ое максимальное число в файле Excel.
     *
     * @param filePath Путь к файлу Excel.
     * @param numberOfMaxValue Позиция максимального числа (N).
     * @return N-ое максимальное число в виде строки.
     * @throws IOException если не удается прочитать файл.
     */
    String findNthMaxValueInFile(String filePath, long numberOfMaxValue) throws IOException;
}
