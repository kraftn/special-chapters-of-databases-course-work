package ru.databases.client.models;

public class Person {
    private String surname, name, patronymic;

    public Person(String surname, String name, String patronymic) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
    }

    public Person() {}

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(surname != null ? surname + " " : "");
        res.append(name != null ? name + " " : "");
        res.append(patronymic != null ? patronymic : "");
        return res.toString();
    }
}
