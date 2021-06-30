package com.github.pgarr.bookies.dao;

import com.github.pgarr.bookies.models.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Book> getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        return (List<Book>) session.createQuery("FROM Book B WHERE B.deleted is false").list();
    }

    public Book getById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("FROM Book B WHERE B.deleted is false and B.id = :id", Book.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Book add(Book book) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(book);
        return book;
    }

    public Book update(Book book) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(book);
        return book;
    }
}
