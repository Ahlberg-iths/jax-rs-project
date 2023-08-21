package blabla.ahlberg.jaxrsproject;

import blabla.ahlberg.jaxrsproject.dto.FootballerDTO;
import blabla.ahlberg.jaxrsproject.entity.Footballer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

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

    Optional<Footballer> findOne(long id) {
        return Optional.ofNullable(entityManager.find(Footballer.class, id));
    }

    List<Footballer> findAll() {
        return entityManager.createQuery("SELECT f FROM Footballer f", Footballer.class).getResultList();
    }

    void updateOne(Long id, FootballerDTO dto) {
        var footballerToUpdate = findOne(id);
        footballerToUpdate.ifPresent(footballer -> {
            footballer.setClub(dto.club());
            footballer.setName(dto.name());
            footballer.setNationality(dto.nationality());
        });
        //TODO:: Throw Exception if footballer not found
    }

    void deleteOne(long id) {
        var footballer = findOne(id);
        footballer.ifPresent(baller -> entityManager.remove(baller));
    }

}
