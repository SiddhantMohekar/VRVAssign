package vrv.vrvassign.Repos;

import org.springframework.data.jpa.repository.JpaRepository;

import vrv.vrvassign.Model.Event;

public interface EventRepository extends JpaRepository<Event,Long>{
    Event findByName(String code);
}
