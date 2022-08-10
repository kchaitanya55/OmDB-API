package com.omdb.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("imdbID")
    private String imdbID;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String ye_ar;

    @JsonProperty("Rated")
    private String rated;

    @JsonProperty("Released")
    private String released;

    @JsonProperty("Runtime")
    private String runtime;

    @JsonProperty("Genre")
    private String genre;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Writer")
    private String writer;

    @JsonProperty("Actors")
    private String actors;

    @JsonProperty("plot")
    private String Plot;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Awards")
    private String awards;

    @JsonProperty("Poster")
    private String poster;

    @JsonProperty("Metascore")
    private String metascore;

    @JsonProperty("imdbRating")
    private String imdbRating;

    @JsonProperty("imdbVotes")
    private String imdbVotes;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("DVD")
    private String dvd;

    @JsonProperty("BoxOffice")
    private String boxOffice;

    @JsonProperty("Production")
    private String production;

    @JsonProperty("Website")
    private String website;

    @JsonProperty("Response")
    private String response;

    @JsonProperty("Ratings")
    @Transient
    private List<Ratings> ratings;

    @JsonProperty("Aggregate Rating (100)")
    private Double aggregateRating;

    @JsonProperty("Error")
    @Transient
    private String error;

    @PrePersist
    @PreUpdate
    public void calc() {
        DecimalFormat df = new DecimalFormat("#.##");
        OptionalDouble ratDouble=this.ratings.stream().mapToDouble(r->{
            String rat=r.getValue();
            if(rat.contains("/")){
                String[] values=rat.split("/");
                return (Double.parseDouble(values[0])/Double.parseDouble(values[1]))*100;
            }else if(rat.contains("%")){
                rat=rat.replace("%","");
                return Double.parseDouble(rat);
            }else{
                return 0;
            }
        }).average();
       this.aggregateRating = ratDouble.isPresent()? Double.parseDouble(df.format(ratDouble.getAsDouble())) :0;
    }
}
