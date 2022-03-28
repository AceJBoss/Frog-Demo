package com.lytquest.frogdemo.service.impl;

import com.lytquest.frogdemo.entity.Book;
import com.lytquest.frogdemo.helper.ExcelHelper;
import com.lytquest.frogdemo.repository.BookRepository;
import com.lytquest.frogdemo.service.ExcelService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {
    private BookRepository repository;
    public ExcelServiceImpl(BookRepository repository){
        this.repository = repository;
    }
    @Override
    public void saveBook(MultipartFile file) {
        try {
            List<Book> tutorials = ExcelHelper.excelToBooks(file.getInputStream());
            repository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return repository.findAll();
    }


}
