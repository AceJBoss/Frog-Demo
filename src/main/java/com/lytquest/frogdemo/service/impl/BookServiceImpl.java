package com.lytquest.frogdemo.service.impl;

import com.lytquest.frogdemo.entity.Book;
import com.lytquest.frogdemo.helper.ExcelHelper;
import com.lytquest.frogdemo.helper.TaskThread;
import com.lytquest.frogdemo.repository.BookRepository;
import com.lytquest.frogdemo.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private BookRepository repository;

    public BookServiceImpl(BookRepository repository){
        this.repository = repository;
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
    @Cacheable(cacheNames = "books")
    public List<Book> getAllBooks() {
        List<Book> bookList = repository.findAll();
        log.info("Available Books => " + bookList);
        return downloadSorter(bookList);
    }

   // Implementing quick sort algorithm to return book with the least download
    public List<Book> downloadSorter(List<Book> books) {
        if (books.size() <= 1) {
            return books;
        }

        List<Book> lowestDownload = new ArrayList<>();
        List<Book> highestDownload = new ArrayList<>();

        Book pivot = books.get(0);
        for (int i = 1; i < books.size(); i++) {
            if (books.get(i).getDownloads() < pivot.getDownloads()) {
                lowestDownload.add(books.get(i));
            } else {
                highestDownload.add(books.get(i));
            }
        }
        lowestDownload = downloadSorter(lowestDownload);
        highestDownload = downloadSorter(highestDownload);
        lowestDownload.add(pivot);
        lowestDownload.addAll(highestDownload);
        return lowestDownload;
    }

    // Using ThreadPoolExecutor to read Data
    public synchronized void readDataAsync(){
        int cpuCount = Runtime.getRuntime().availableProcessors(); //
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(cpuCount);

        for(int c = 0; c < 100; c++){
            threadPoolExecutor.execute(new TaskThread(BookServiceImpl.this));
        }
    }


}
