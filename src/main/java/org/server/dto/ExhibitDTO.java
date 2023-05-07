package org.server.dto;

public class ExhibitDTO {
    private String name;
    private String artist;
    private String type;
    private int year;
    private String gallery;

    public ExhibitDTO() {
    }

    public ExhibitDTO(String name, String artist, String type, int year, String gallery) {
        this.name = name;
        this.artist = artist;
        this.type = type;
        this.year = year;
        this.gallery = gallery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }
}
