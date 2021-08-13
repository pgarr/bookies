package com.github.pgarr.bookies.book.controllers;

import com.github.pgarr.bookies.book.models.Book;
import com.github.pgarr.bookies.book.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping(value = "/")
    public String getHomePage() {
        return "redirect:/books";
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public String getAll(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);

        return "booksList";
    }
}
