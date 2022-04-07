package com.cursor.team2.movieproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String name;

    @Enumerated(EnumType.STRING)
    public Category category;

    public String director;
    public String description;

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Rate rate;

    public Movie() {
        this.rate = new Rate();
    }
}
