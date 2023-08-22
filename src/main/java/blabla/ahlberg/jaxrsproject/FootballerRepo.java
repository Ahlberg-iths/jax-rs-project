package blabla.ahlberg.jaxrsproject;

import blabla.ahlberg.jaxrsproject.dto.FootballerDTO;
import blabla.ahlberg.jaxrsproject.entity.Footballer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@Transactional
@ApplicationScoped
public class FootballerRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    Mapper mapper;

    void insertOne(FootballerDTO dto) {
        var footballer = mapper.mapToFootballer(dto);
        entityManager.persist(footballer);
    }

    Footballer findOne(long id) {
        var footballer = entityManager.find(Footballer.class, id);
        if (footballer == null) throw new NotFoundException();
        return footballer;
    }

    List<Footballer> findAll() {
        return entityManager.createQuery("SELECT f FROM Footballer f", Footballer.class).getResultList();
    }

    void updateOne(Long id, @Valid FootballerDTO dto) {
        var footballer = findOne(id);
        try {
            footballer.setNationality(dto.nationality());
            footballer.setName(dto.name());
            footballer.setClub(dto.club());
        } catch (RuntimeException e) {
            throw new BadRequestException(e);
        }
    }

    void deleteOne(long id) {
        var footballer = findOne(id);
        entityManager.remove(footballer);
    }

}
