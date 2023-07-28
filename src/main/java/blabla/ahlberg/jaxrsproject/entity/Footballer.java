package blabla.ahlberg.jaxrsproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Footballer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String nationality;
    private String club;

    Footballer(String name, String nationality, String club) {
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
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Footballer) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.nationality, that.nationality) &&
                Objects.equals(this.club, that.club);
    }

    @Override
    public int hashCode() {
        return 12;
    }

    @Override
    public String toString() {
        return "Footballer[" +
                "name=" + name + ", " +
                "nationality=" + nationality + ", " +
                "club=" + club + ']';
    }
}
