package oniline.itlogic.mediamanager.repo;

import oniline.itlogic.mediamanager.model.Bewertung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BewertungenRepository extends JpaRepository<Bewertung, Long> {
    Optional<Bewertung> findBewertungenById(Long id);
}