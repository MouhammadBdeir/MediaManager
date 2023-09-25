package oniline.itlogic.mediamanager.repo;

import oniline.itlogic.mediamanager.model.Bewertung;
import oniline.itlogic.mediamanager.model.Category;
import oniline.itlogic.mediamanager.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryById(Long id);
}
