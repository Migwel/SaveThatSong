package dev.migwel.sts.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

import java.util.Date;

@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_generator")
    @SequenceGenerator(name = "song_generator", sequenceName = "song_seq", allocationSize = 1)
    private Long id;

    private String username;
    private String artist;
    private String title;
    private String rawData;

    @Column(insertable = false) // Rely on Postgres now() default value
    private Date creationDate;

    public Song() {
        // Needed for Spring JPA
    }

    public Song(Long id, String username, String artist, String title, String rawData) {
        this.id = id;
        this.username = username;
        this.artist = artist;
        this.title = title;
        this.rawData = rawData;
    }

    public Song(String username, String artist, String title, String rawData) {
        this.username = username;
        this.artist = artist;
        this.title = title;
        this.rawData = rawData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public Date getCreationDate() {
        return new Date(creationDate.getTime());
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = new Date(creationDate.getTime());
    }
}
