package com.shuja1497.olaplay;

/**
 * Created by shuja1497 on 12/20/17.
 */

public class Song {

    private String song, url, artists, cover_image;

    public Song(String song , String url , String artists, String cover_image){

        this.song = song;
        this.url = url;
        this.artists = artists;
        this.cover_image = cover_image;
    }

    public String getSong() {
        return song;
    }

    public String getUrl() {
        return url;
    }

    public String getArtists() {
        return artists;
    }

    public String getCover_image() {
        return cover_image;
    }
}
