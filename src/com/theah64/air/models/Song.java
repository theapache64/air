package com.theah64.air.models;

/**
 * Created by theapache64 on 14/3/18.
 */
public class Song {

    private final String id, path;

    public Song(String id, String path) {
        this.id = id;
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public String getName() {
        return path.split("/")[path.split("/").length - 1];
    }
}
