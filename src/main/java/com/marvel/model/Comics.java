package com.marvel.model;



import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comics")
public class Comics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comictitle")
    private String comicTitle;
    @Column(name = "issue")
    private String issue;
    @Column(name = "writer")
    private String writer;
    @Column(name = "penciler")
    private String penciler;
    @Column(name = "published")
    private String published;
    @Column(name = "created")
    private String created;
    @Column(name = "selled")
    private Integer selled;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "characters_comics",
            joinColumns = @JoinColumn(name = "comics_id"),
            inverseJoinColumns = @JoinColumn(name = "characters_id"))
    private Set<Characters> characters = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComicTitle() {
        return comicTitle;
    }

    public void setComicTitle(String comicTitle) {
        this.comicTitle = comicTitle;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPenciler() {
        return penciler;
    }

    public void setPenciler(String penciler) {
        this.penciler = penciler;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getPublished() {
        return published;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Set<Characters> getCharacters() {
        return characters;
    }

    public void setCharacters(Set<Characters> characters) {
        this.characters = characters;
    }

    public Integer getSelled() {
        return selled;
    }

    public void setSelled(Integer selled) {
        this.selled = selled;
    }


}