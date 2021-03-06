package com.deffe.max.Models;


import com.deffe.max.Utils.Constants;
import com.google.gson.annotations.SerializedName;

public class MovieDetailsGenres
{
    @SerializedName(Constants.id)
    private Integer id;

    @SerializedName(Constants.name)
    private String name;

    public MovieDetailsGenres()
    {

    }

    public MovieDetailsGenres(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
