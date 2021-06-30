package com.github.pgarr.bookies.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "publishing_house")
    private String publishingHouse;

    @Column(name = "series")
    private String series;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "publishing_year")
    private String publishingYear;

    @Column(name = "pages_number")
    private int pagesNumber;

    @Column(name = "library_id")
    private String libraryId;

    @Column(name = "added_time")
    private LocalDateTime addedTime;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "deleted_time")
    private LocalDateTime deletedTime;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
}
