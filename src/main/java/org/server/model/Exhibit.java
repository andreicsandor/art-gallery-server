package org.server.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="Exhibit", uniqueConstraints={@UniqueConstraint(columnNames = {"name", "artist"})})
public class Exhibit extends Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Exhibit() {}

    public Exhibit(String name, String artist, String type, int year) {
        super(name, artist, type, year);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Exhibit exhibit = (Exhibit) obj;
        return Objects.equals(artist, exhibit.artist) && Objects.equals(name, exhibit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "<<Exhibit: " + id + ", Name: " + name + ", Artist: " + artist + ", Type: " + type + ", Year: " + year + ">>";
    }
}
