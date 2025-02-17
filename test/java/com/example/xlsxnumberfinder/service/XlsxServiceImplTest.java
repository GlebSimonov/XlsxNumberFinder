package com.example.xlsxnumberfinder.service;

import com.example.xlsxnumberfinder.service.impl.XlsxServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class XlsxServiceImplTest {

    private static final String TEST_FILE = "src/test/resources/TestFile.xlsx";
    private static final String TEST_FIND_VALUE = "999";
    private XlsxService xlsxService;

    @BeforeEach
    void setUp() {
        xlsxService = new XlsxServiceImpl();
    }

    @Test
    void testFindNthMaxValue_whenFileHasSufficientNumbers() throws IOException {
        String result = xlsxService.findNthMaxValueInFile(TEST_FILE, 2);
        assertNotNull(result);

        assertEquals(TEST_FIND_VALUE, result);
    }

    @Test
    void testFindNthMaxValue_whenFileHasLessThanN() throws IOException {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                xlsxService.findNthMaxValueInFile(TEST_FILE, 100)); // Проверяем слишком большое N

        assertTrue(exception.getMessage().startsWith("Такого значения не существует при параметре"));
    }

    @Test
    void testFindNthMaxValue_whenFileNotFound() {
        Exception exception = assertThrows(FileNotFoundException.class, () ->
                xlsxService.findNthMaxValueInFile("non_existent.xlsx", 1));

        assertEquals("Файл не найден: non_existent.xlsx", exception.getMessage());
    }

    @Test
    void testFindNthMaxValue_whenInvalidInput() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                xlsxService.findNthMaxValueInFile(TEST_FILE, -1));

        assertEquals("Значение N должно быть больше 0.", exception.getMessage());
    }
}
