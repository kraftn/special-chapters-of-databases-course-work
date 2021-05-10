package ru.databases.client.models;

public class Painting extends Item {
    private Person artist;
    private String genre, style, technique;
    private Size size;
    private Integer century;

    public Painting() {
        artist = new Person();
        size = new Size();
    }

    public Person getArtist() {
        return artist;
    }

    public void setArtist(Person artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Integer getCentury() {
        return century;
    }

    public void setCentury(Integer century) {
        this.century = century;
    }

    public static class Size {
        private Double width, height;

        public Double getWidth() {
            return width;
        }

        public void setWidth(Double width) {
            this.width = width;
        }

        public Double getHeight() {
            return height;
        }

        public void setHeight(Double height) {
            this.height = height;
        }
    }
}
