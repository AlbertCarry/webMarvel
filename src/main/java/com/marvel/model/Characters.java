package com.marvel.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "characters")
@ApiModel(value = "Characters")
public class Characters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    @ApiModelProperty(value = "Character name",example = "BOB")
    private String name = "";
    @Column(name = "secondname")
    @ApiModelProperty(value = "Character secondname",example = "SPONGE")
    private String secondName="";
    @Column(name = "aliases")
    @ApiModelProperty(value = "Character name",example = "SQUARE PANTS")
    private String aliases;
    @Column(name = "otheraliases")
    private String otherAliases;
    @Column(name = "height")
    private String height;
    @Column(name = "weight")
    private String weight;
    @Column(name = "eyes")
    private String eyes;
    @Column(name = "hair")
    private String hair;
    @Column(name = "gender")
    private String gender;
    @Column(name = "universe")
    private String universe;
    @Column(name = "education")
    private String education;
    @Column(name = "placeoforigin")
    private String placeoforigin;
    @Column(name = "identity")
    private String identity;
    @Column(name = "knownrelatives")
    private String knownrelatives;
    @Column(name = "powers")
    private String powers;
    @Column(name = "groupaffiliation")
    private String groupaffiliation;
    @Column(name = "overview")
    private String overview;
    @Column(name = "biography")
    private String biography;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "characters_comics",
            joinColumns = @JoinColumn(name = "characters_id"),
            inverseJoinColumns = @JoinColumn(name = "comics_id"))
    private Set<Comics> comics = new HashSet<>();

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

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAliases() {
        return aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public String getOtherAliases() {
        return otherAliases;
    }

    public void setOtherAliases(String otherAliases) {
        this.otherAliases = otherAliases;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getEyes() {
        return eyes;
    }

    public void setEyes(String eyes) {
        this.eyes = eyes;
    }

    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUniverse() {
        return universe;
    }

    public void setUniverse(String universe) {
        this.universe = universe;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPlaceoforigin() {
        return placeoforigin;
    }

    public void setPlaceoforigin(String placeoforigin) {
        this.placeoforigin = placeoforigin;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getKnownrelatives() {
        return knownrelatives;
    }

    public void setKnownrelatives(String knownrelatives) {
        this.knownrelatives = knownrelatives;
    }

    public String getPowers() {
        return powers;
    }

    public void setPowers(String powers) {
        this.powers = powers;
    }

    public String getGroupaffiliation() {
        return groupaffiliation;
    }

    public void setGroupaffiliation(String groupaffiliation) {
        this.groupaffiliation = groupaffiliation;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Set<Comics> getComics() {
        return comics;
    }

    public void setComics(Set<Comics> comics) {
        this.comics = comics;
    }


}
