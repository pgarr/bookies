package com.github.pgarr.bookies.book.services;

import com.github.pgarr.bookies.book.dao.BookDAO;
import com.github.pgarr.bookies.book.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("bookService")
public class BookService {

    @Autowired
    private BookDAO bookDAO;

    @Transactional
    public List<Book> getAll() {
        return bookDAO.getAll();
    }

    @Transactional
    public Book getById(int id) {
        return bookDAO.getById(id);
    }

    @Transactional
    public Book add(Book book) {
        return bookDAO.add(book);
    }

    @Transactional
    public Book update(Book book) {
        return bookDAO.update(book);
    }
}
