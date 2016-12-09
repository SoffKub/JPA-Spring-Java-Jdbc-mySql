package repository;

import model.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepo extends CrudRepository<Team, Long> {


	//	@Query("select e from #{#entityName} e where e.name = :name")
	//	public Team byName(@Param("name") String xyz);

	Team findByName(String name);


}