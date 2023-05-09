package org.server.model;

import jakarta.persistence.*;

import java.util.Objects;

@MappedSuperclass
public class Artwork {
    @Column(name = "name", nullable = false, length = 50)
    protected String name;

    @Column(name = "artist", nullable = false, length = 25)
    protected String artist;

    @Column(name = "type", nullable = false, length = 50)
    protected String type;

    @Column(name = "year", nullable = false)
    protected int year;

    public Artwork() {}

    public Artwork(String name, String artist, String type, int year) {
        this.name = name;
        this.artist = artist;
        this.type = type;
        this.year = year;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Artwork artwork = (Artwork) obj;
        return Objects.equals(artist, artwork.artist) && Objects.equals(name, artwork.name);
    }

    @Override
    public String toString() {
        return "<<Artwork: " + "generic" + ", Name: " + name + ", Artist: " + artist + ", Type: " + type + ", Year: " + year + ">>";
    }
}
