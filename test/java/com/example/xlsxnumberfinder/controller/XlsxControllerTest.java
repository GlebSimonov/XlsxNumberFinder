package com.example.xlsxnumberfinder.controller;

import com.example.xlsxnumberfinder.service.XlsxService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class XlsxControllerTest {

    private final XlsxService xlsxService = mock(XlsxService.class);
    private final XlsxController controller = new XlsxController(xlsxService);

    @Test
    void testFindNthMax_whenValidRequest() throws Exception {
        when(xlsxService.findNthMaxValueInFile("TestFile.xlsx", 2)).thenReturn("42");

        ResponseEntity<String> response = controller.findNthMax("TestFile.xlsx", 2);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("42", response.getBody());
    }

    @Test
    void testFindNthMax_whenInvalidN() throws IOException {
        when(xlsxService.findNthMaxValueInFile("TestFile.xlsx", -1))
                .thenThrow(new IllegalArgumentException("Значение N должно быть больше 0."));

        ResponseEntity<String> response = controller.findNthMax("TestFile.xlsx", -1);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Значение N должно быть больше 0.", response.getBody());
    }

    @Test
    void testFindNthMax_whenFileNotFound() throws IOException {
        when(xlsxService.findNthMaxValueInFile("missing.xlsx", 1))
                .thenThrow(new FileNotFoundException("Файл не найден: missing.xlsx"));

        ResponseEntity<String> response = controller.findNthMax("missing.xlsx", 1);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Файл не найден: missing.xlsx", response.getBody());
    }

    @Test
    void testFindNthMax_whenUnexpectedError() throws IOException {
        when(xlsxService.findNthMaxValueInFile("TestFile.xlsx", 2))
                .thenThrow(new RuntimeException("Ошибка"));

        ResponseEntity<String> response = controller.findNthMax("TestFile.xlsx", 2);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Ошибка при чтении файла"));
    }
}
