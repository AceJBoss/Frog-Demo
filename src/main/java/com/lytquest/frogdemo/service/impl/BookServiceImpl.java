package com.lytquest.frogdemo.service.impl;

import com.lytquest.frogdemo.entity.Book;
import com.lytquest.frogdemo.helper.ExcelHelper;
import com.lytquest.frogdemo.helper.ThreadPoolExecutorUtil;
import com.lytquest.frogdemo.repository.BookRepository;
import com.lytquest.frogdemo.service.ExcelService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    private final ThreadPoolExecutorUtil threadPoolExecutorUtil;
    private BookRepository repository;

    public ExcelServiceImpl(BookRepository repository, ThreadPoolExecutorUtil threadPoolExecutorUtil){
        this.repository = repository;
        this.threadPoolExecutorUtil = threadPoolExecutorUtil;
    }


    @Override
    public void saveBook(MultipartFile file) {
        try {
            List<Book> books = ExcelHelper.readExcel(file.getInputStream());
            repository.saveAll(books);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return repository.findAll();
    }


}
