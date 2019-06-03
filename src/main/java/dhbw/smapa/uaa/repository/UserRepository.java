package dhbw.smapa.uaa.repository;

import dhbw.smapa.uaa.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);
}
