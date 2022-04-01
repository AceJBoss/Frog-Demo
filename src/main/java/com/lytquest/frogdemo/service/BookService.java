package com.lytquest.frogdemo.service;

import com.lytquest.frogdemo.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    public void saveBook(MultipartFile file);
    public List<Book> getAllBooks();
}
