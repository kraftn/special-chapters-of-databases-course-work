package ru.databases.client.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import ru.databases.client.options.BookFormat;

public class Book extends Item {
    private Long edition;
    private Person author;
    private String binding;
    private BookFormat format;
    @BsonProperty(value = "publication_place")
    private String publicationPlace;
    @BsonProperty(value = "publishing_house")
    private String publishingHouse;
    @BsonProperty(value = "pages_number")
    private Integer pagesNumber;

    public Book() {
        author = new Person();
    }

    public Long getEdition() {
        return edition;
    }

    public void setEdition(Long edition) {
        this.edition = edition;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public BookFormat getFormat() {
        return format;
    }

    public void setFormat(BookFormat format) {
        this.format = format;
    }

    public String getPublicationPlace() {
        return publicationPlace;
    }

    public void setPublicationPlace(String publicationPlace) {
        this.publicationPlace = publicationPlace;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public Integer getPagesNumber() {
        return pagesNumber;
    }

    public void setPagesNumber(Integer pagesNumber) {
        this.pagesNumber = pagesNumber;
    }
}
