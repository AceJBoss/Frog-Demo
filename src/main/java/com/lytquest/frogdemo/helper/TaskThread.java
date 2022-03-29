package com.lytquest.frogdemo.helper;

import com.lytquest.frogdemo.entity.Book;
import com.lytquest.frogdemo.repository.BookRepository;

import java.util.List;

class TaskThread implements Runnable
{
    private final BookRepository bookRepository;
    List<Book> books;

    TaskThread(BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run()
    {
        books=bookRepository.findAll();
    }
}
