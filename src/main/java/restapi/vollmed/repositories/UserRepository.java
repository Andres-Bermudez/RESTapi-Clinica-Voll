package restapi.vollmed.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import restapi.vollmed.models.users.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserDetails findByUserName(String username);
}
