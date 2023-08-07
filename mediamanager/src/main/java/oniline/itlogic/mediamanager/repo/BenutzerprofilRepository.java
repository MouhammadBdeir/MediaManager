package oniline.itlogic.mediamanager.repo;



import oniline.itlogic.mediamanager.model.Benutzerprofil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@org.springframework.transaction.annotation.Transactional(readOnly = true)
public interface BenutzerprofilRepository extends JpaRepository<Benutzerprofil, Long> {
    Optional<Benutzerprofil> findBenutzerprofilById(Long id);
    Optional<Benutzerprofil> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Benutzerprofil a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);
}