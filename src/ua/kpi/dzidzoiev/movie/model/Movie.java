package ua.kpi.dzidzoiev.movie.model;


import java.util.Date;

public class Movie {
    Long id;
    String name;
    String director;
    Date duration;
    Integer year;
    String description;

    public Movie() {
    }

    public Movie(Long id) {
        this.id = id;
    }

    public Movie(Long id, String name, String director, Date duration, Integer year, String description) {
        this.id = id;
        this.name = name;
        this.director = director;
        this.duration = duration;
        this.year = year;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
