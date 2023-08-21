package blabla.ahlberg.jaxrsproject;

import blabla.ahlberg.jaxrsproject.dto.FootballerDTO;
import blabla.ahlberg.jaxrsproject.dto.FootballerWithIdDTO;
import blabla.ahlberg.jaxrsproject.entity.Footballer;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Mapper {

    public Mapper() {
    }

    FootballerWithIdDTO mapToFootballerWithIdDTO(Footballer footballer) {
        return new FootballerWithIdDTO(
                footballer.getId(), footballer.getName(), footballer.getNationality(), footballer.getClub()
        );
    }

    Footballer mapToFootballer(FootballerDTO dto) {
        return new Footballer(dto.name(), dto.nationality(), dto.club());
    }

}
