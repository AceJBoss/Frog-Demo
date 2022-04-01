package com.lytquest.frogdemo.service.impl;

import com.lytquest.frogdemo.entity.Book;
import com.lytquest.frogdemo.helper.ExcelHelper;
import com.lytquest.frogdemo.helper.TaskThread;
import com.lytquest.frogdemo.helper.ThreadPoolExecutorUtil;
import com.lytquest.frogdemo.repository.BookRepository;
import com.lytquest.frogdemo.service.BookService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final ThreadPoolExecutorUtil threadPoolExecutorUtil;
    private BookRepository repository;

    public BookServiceImpl(BookRepository repository, ThreadPoolExecutorUtil threadPoolExecutorUtil){
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
        List<Book> bookList = repository.findAll();
        return downloadSorter(bookList);
    }

    @Override
    public List<Book> getAllBookAsync() {
        for (int i=0;i<20000;i++)
        {
            TaskThread taskThread=new TaskThread(repository);
            threadPoolExecutorUtil.executeTask(taskThread);
        }
        /*
            Following code created to just return list of values at the end
         */
        TaskThread taskThread = new TaskThread(repository);
        threadPoolExecutorUtil.executeTask(taskThread);
        return taskThread.books;
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
        return highestDownload;
    }


}
