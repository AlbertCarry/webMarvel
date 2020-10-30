package com.marvel.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

@Entity
@Table(name = "characters_comics")
public class CharactersComics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "characters_id")
    private Long charactersId;
    @Column(name = "comics_id")
    private Long comicsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCharactersId() {
        return charactersId;
    }

    public void setCharactersId(Long charactersId) {
        this.charactersId = charactersId;
    }

    public Long getComicsId() {
        return comicsId;
    }

    public void setComicsId(Long comicsId) {
        this.comicsId = comicsId;
    }

    @Override
    public String toString() {
        return "CharactersComics{" +
                ", charactersId=" + charactersId +
                ", comicsId=" + comicsId +
                '}';
    }
}
