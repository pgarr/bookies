package com.github.pgarr.bookies.dao;

import com.github.pgarr.bookies.models.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BookDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Book> getAll() {
        return (List<Book>) entityManager.createQuery("FROM Book B WHERE B.deleted is false").getResultList();
    }

    public Book getById(int id) {
        return entityManager.createQuery("FROM Book B WHERE B.deleted is false and B.id = :id", Book.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Book add(Book book) {
        entityManager.persist(book);
        return book;
    }

    public Book update(Book book) {
        entityManager.merge(book);
        return book;
    }
}
