package oniline.itlogic.mediamanager.repo;

import oniline.itlogic.mediamanager.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmSerienRepository extends JpaRepository<Media, Long> {
    Optional<Media> findFilmSerienById(Long id);
}
