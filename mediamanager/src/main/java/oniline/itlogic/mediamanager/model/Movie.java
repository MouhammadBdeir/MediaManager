package oniline.itlogic.mediamanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    private String title;
    private String overview;
    private String release_date;
    private String director; // Hinweis: Diese Information muss von einer anderen Quelle bezogen werden
    private String[] genres; // Hinweis: Diese Information muss von einer anderen Quelle bezogen werden
    private String URLTrailer;
    private  String imgSrc;
    // Standardkonstruktor (wird von Jackson benötigt)
    public Movie() {}

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getURLTrailer() {
        return URLTrailer;
    }

    public void setURLTrailer(String URLTrailer) {
        this.URLTrailer = URLTrailer;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getImgSrc() {
        return imgSrc;
    }
    public void setImgSrc(String path) {
        this.imgSrc=path;
    }
}


