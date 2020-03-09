package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.Player;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
	
	List<Player> findById(int id);
	List<Player> findByName(String name);
	List<Player> findByAge(int age);
	List<Player> findByGender(char gender);
	List<Player> findByTeamId(int id);
}
