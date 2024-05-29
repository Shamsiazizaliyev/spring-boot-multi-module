package az.ingress.repository;
import az.ingress.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepostory extends JpaRepository<User,Integer> {



    Optional<User> findByUsername(String username);

}
