package com.lytquest.frogdemo.controller;

import com.lytquest.frogdemo.dto.ApiResponse;
import com.lytquest.frogdemo.entity.Book;
import com.lytquest.frogdemo.helper.ExcelHelper;
import com.lytquest.frogdemo.service.impl.ExcelServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin("http://localhost:8081")
@RestController
@RequestMapping("/api/book")
public class BookController {

    private ExcelServiceImpl fileService;
    public BookController(ExcelServiceImpl fileService){
        this.fileService = fileService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                fileService.saveBook(file);
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Uploaded the file successfully: " + file.getOriginalFilename()));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ApiResponse("Could not upload the file: " + file.getOriginalFilename() + "!"));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Please upload an excel file!"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> books = fileService.getAllBooks();
            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
