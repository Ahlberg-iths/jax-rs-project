package blabla.ahlberg.jaxrsproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Footballer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String nationality;
    @NotNull
    private String club;

    public Footballer(String name, String nationality, String club) {
        this.name = name;
        this.nationality = nationality;
        this.club = club;
    }

    public Footballer() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public String getClub() {
        return club;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setClub(String club) {
        this.club = club;
    }

    @Override
    public String toString() {
        return "Footballer[" +
                "name=" + name + ", " +
                "nationality=" + nationality + ", " +
                "club=" + club + ']';
    }
}
