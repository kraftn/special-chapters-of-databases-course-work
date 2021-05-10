package ru.databases.client.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDate;

public class Item {
    private ObjectId id;
    private String name;
    @BsonProperty(value = "creation_year")
    private Integer creationYear;
    @BsonProperty(value = "entering_date")
    private LocalDate enteringDate;
    private Double worth;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCreationYear() {
        return creationYear;
    }

    public void setCreationYear(Integer creationYear) {
        this.creationYear = creationYear;
    }

    public LocalDate getEnteringDate() {
        return enteringDate;
    }

    public void setEnteringDate(LocalDate enteringDate) {
        this.enteringDate = enteringDate;
    }

    public Double getWorth() {
        return worth;
    }

    public void setWorth(Double worth) {
        this.worth = worth;
    }
}
