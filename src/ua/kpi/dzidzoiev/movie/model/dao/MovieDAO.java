package ua.kpi.dzidzoiev.movie.model.dao;

import ua.kpi.dzidzoiev.movie.model.Movie;

public interface MovieDAO {

    long create(Movie movie);

    Movie read(long id);

    void update(Movie movie);

    void delete(Movie movie);

}
