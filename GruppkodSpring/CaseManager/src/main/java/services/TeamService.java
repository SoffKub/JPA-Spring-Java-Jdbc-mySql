package services;

import model.Status;
import model.Team;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.TeamRepo;
import repository.UserRepo;

import javax.transaction.Transactional;
import java.util.List;


/**
 * The Class TeamService - A grouping of User Features: 
 * - Create a team 
 * - Updating a team 
 * - Find by Team name 
 * - Find all teams
 * - Deactivate team
 * - Assign user to team.
 * 
 * - maximum of 10 users in a team 
 * - A User can only be part of one team at the time
 *
 */

/**
 *	@Autowire : Spring beans are injected or acquired using autowire annotation.
	@Service : allowing for implementation classes to be autodetected through classpath scanning-
	Annotate service classes with @Service. All your business logic will be in Service classes. 
	Controller and DAO layers will not have any business logic in there.
	@Transactional defines the scope of a single database transaction. 
	The database transaction happens inside the scope of a persistence context.*/


@Service
public class TeamService {

	/** The team repository. */
	private TeamRepo teamRepository;
	
	/** The user repository. */
	private UserRepo userRepository;

	/**
	 * Instantiates a new team service.
	 *
	 * @param teamRepository the team repository
	 * @param userRepository the user repository
	 */
	@Autowired
	public TeamService(TeamRepo teamRepository, UserRepo userRepository) {
		this.teamRepository = teamRepository;
		this.userRepository = userRepository;
	}

	/**
	 * Creates the team.
	 *
	 * @param team the team
	 * @return the team
	 */
	@Transactional
	public Team createTeam(Team team) {
		try {
			if (team.getId() == null) {

				return teamRepository.save(team);

			} else {
				throw new ServiceException("Team with this teamname already exists");
			}

		} catch (Exception e) {
			throw new ServiceException("Could not add team: " + team.getName(), e);
		}
	}

	/**
	 * Find all teams.
	 *
	 * @return the iterable
	 */
	public Iterable<Team> findAllTeams() {
		try {
			return teamRepository.findAll();
		} catch (Exception e) {
			throw new ServiceException("Could not get all teams ", e);
		}
	}

	/**
	 * Find by Team name.
	 *
	 * @param teamName the team name
	 * @return the team
	 */
	public Team findByName(String teamName) {
		try {
			Team team = teamRepository.findByName(teamName);
			return team;

		} catch (Exception e) {
			throw new ServiceException("Could not get  team " + teamName, e);
		}
	}

	/**
	 * Update team.
	 *
	 * @param oldName the old name
	 * @param newName the new name
	 */
	@Transactional
	public void updateTeam(String oldName, String newName) {
		try {
			Team oldTeam = teamRepository.findByName(oldName);
			if (oldTeam != null) {
				Team newTeam = teamRepository.findByName(newName);
				if (newTeam == null) {
					oldTeam.setName(newName);
					teamRepository.save(oldTeam);
				} else {
					throw new ServiceException("Team with this teamname " + newName + " exists");

				}
			} else {
				throw new ServiceException("Team with this teamname: " + oldName + "NOT exists");
			}

		} catch (Exception e) {
			throw new ServiceException("Could not update team", e);
		}
	}

	/**
	 * Deactivate team.
	 *
	 * @param teamName the team name
	 */
	@Transactional
	public void deactivateTeam(String teamName) {

		try {
			Team team = teamRepository.findByName(teamName);
			if (team != null) {
				team.setStatus(Status.INACTIVE.toString());
				teamRepository.save(team);
			} else {
				throw new ServiceException("Team with this teamname NOT exists");
			}

		} catch (Exception e) {
			throw new ServiceException("Could not deactivate team: " + teamName, e);
		}

	}

	/**
	 * Assign user to team.
	 *
	 * @param teamName the team name
	 * @param userId the user id
	 */
	@Transactional
	public void assigneUserToTeam(String teamName, Long userId) {
		try {
			Team team = teamRepository.findByName(teamName);
			User user = userRepository.findOne(userId);
			if ((team != null) && (user != null)) {
				List<User> users = userRepository.findAllByTeam(team);
				if (users.size() < 10) {
					user.setTeam(team);
					userRepository.save(user);

				} else {
					throw new ServiceException(
							"This team already has 10 users! (But it is allowed to have MAX 10 users in one team)");
				}

			} else {
				throw new ServiceException("Team with this teamId NOT exists OR User NOT exists");
			}

		} catch (Exception e) {
			throw new ServiceException("Could not update team", e);
		}
	}

}
