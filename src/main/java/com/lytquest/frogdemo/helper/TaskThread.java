package com.lytquest.frogdemo.helper;

import com.lytquest.frogdemo.entity.Book;
import com.lytquest.frogdemo.service.impl.BookServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TaskThread implements Runnable {

    private final BookServiceImpl bookService;
    public List<Book> books;

    public TaskThread(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run() {
        books=bookService.getAllBooks();
        log.info("Books ===> " + books);
    }
}
