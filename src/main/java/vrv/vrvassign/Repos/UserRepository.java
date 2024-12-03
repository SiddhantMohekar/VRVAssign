package vrv.vrvassign.Repos;

import org.springframework.data.jpa.repository.JpaRepository;

import vrv.vrvassign.Model.User;

public interface UserRepository extends JpaRepository<User,Long>{
    User findByEmail(String email);
}
