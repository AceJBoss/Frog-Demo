package com.lytquest.frogdemo.helper;

import com.lytquest.frogdemo.entity.Book;
import com.lytquest.frogdemo.repository.BookRepository;

import java.util.List;

public class TaskThread implements Runnable
{
    private final BookRepository bookRepository;
    public List<Book> books;

    public TaskThread(BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run()
    {
        books=bookRepository.findAll();
    }
}
