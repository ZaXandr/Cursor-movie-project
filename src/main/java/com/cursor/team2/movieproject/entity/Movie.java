package com.cursor.team2.movieproject.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@NoArgsConstructor
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
    public Rate rate;
}
