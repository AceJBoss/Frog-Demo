package com.lytquest.frogdemo;

import com.lytquest.frogdemo.entity.Book;
import com.lytquest.frogdemo.repository.BookRepository;
import com.lytquest.frogdemo.service.impl.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class FrogDemoApplicationTests {

    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookRepository bookRepository;

    @DisplayName("Test to Add Books()")
    @Test
    public void addBooks(){
        List<Book> book = IntStream.rangeClosed(1,5)
                .mapToObj(b->new Book("Title " + b, "Description " + b, new Random().nextInt() * 1000))
                        .collect(Collectors.toList());
        when(bookRepository.saveAll(book)).thenReturn(book);
        assertEquals(book, book);
    }

    @DisplayName("Test Getting All Books()")
    @Test
    public void getBooks(){
        when(bookRepository.findAll()).thenReturn(Stream.of(new Book(100L, "Title 1", "Content 1", 5),
                new Book(200L, "Title 2", "Content 2", 6)).collect(Collectors.toList()));
        assertEquals(2, bookService.getAllBooks().size());
    }

}
