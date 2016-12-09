package services;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.TeamRepo;
import repository.UserRepo;
import repository.WorkItemRepo;

import javax.transaction.Transactional;
import java.util.List;


/**
 * The Class UserService- user system belonging to a team features: 
 * - Create a User 
 * - Updating a User 
 * - Gets the all users in team 
 * - Get user based on user ID 
 * - Get user based on user firstName, lastName or userName
 * - Download all User as part of a certain team
 * - Deactivate use and activate user
 *
 * - A User must have a username that is at least 10 characters 
 * - When a User get deactivated the status of all his WorkItem will be change to un started
 
 */

/**
 *	@Autowire : Spring beans are injected or acquired using autowire annotation.
	@Service : allowing for implementation classes to be autodetected through classpath scanning-
	Annotate service classes with @Service. All your business logic will be in Service classes. 
	Controller and DAO layers will not have any business logic in there.
	@Transactional defines the scope of a single database transaction. 
	The database transaction happens inside the scope of a persistence context.*/



@Service
public class UserService {

	/** The user repository. */
	private final UserRepo userRepository;
	
	/** The team repository. */
	private final TeamRepo teamRepository;
	
	/** The work item repository. */
	private final WorkItemRepo workItemRepository;

	/**
	 * Instantiates a new user service.
	 *
	 * @param userRepository the user repository
	 * @param teamRepository the team repository
	 * @param workItemRepository the work item repository
	 */
	@Autowired
	public UserService(UserRepo userRepository, TeamRepo teamRepository,
					   WorkItemRepo workItemRepository) {
		this.userRepository = userRepository;
		this.teamRepository = teamRepository;
		this.workItemRepository = workItemRepository;
	}

	/**
	 * Gets the user.
	 *
	 * @param Id the id
	 * @return the user
	 */
	public User getUser(Long Id) {
		return userRepository.findOne(Id);
	}

	/**
	 * Gets the user by username.
	 *
	 * @param username the username
	 * @return the user by username
	 */
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	/**
	 * Gets the user by firstname.
	 *
	 * @param firstname the firstname
	 * @return the user by firstname
	 */
	public List<User> getUserByFirstname(String firstname) {
		return userRepository.findByFirstname(firstname);
	}

	/**
	 * Gets the user by lastname.
	 *
	 * @param lastname the lastname
	 * @return the user by lastname
	 */
	public List<User> getUserByLastname(String lastname) {
		return userRepository.findByLastname(lastname);
	}

	/**
	 * Gets the all users in team.
	 *
	 * @param team the team
	 * @return the all users in team
	 */
	public List<User> getAllUsersInTeam(Team team) {
		return userRepository.findAllByTeam(team);
	}

	/**
	 * Creates the user.
	 *
	 * @param user the user
	 * @return the user
	 */
	@Transactional
	public User createUser(User user) {
		if (user.getUsername().length() >= 10) {
			teamRepository.save(user.getTeam());
			List<User> users = userRepository.findAllByTeam(user.getTeam());
			if (users.size() < 10) {
				return userRepository.save(user);

			} else {
				throw new ServiceException(
						"This team already has 10 users.");
			}

		} else
			throw new ServiceException("Username must be at least 10 characters.");
	}

	/**
	 * Update user.
	 *
	 * @param user the user
	 * @return the user
	 */
	@Transactional
	public User updateUser(User user) {
		if (user.getUsername().length() >= 10) {
			teamRepository.save(user.getTeam());
			List<User> users = userRepository.findAllByTeam(user.getTeam());
			if (users.size() < 10) {
				return userRepository.save(user);

			} else {
				throw new ServiceException(
						"This team already has 10 users.");
			}
		} else
			throw new ServiceException("Username must be at least 10 characters long.");
	}

	/**
	 * Deactivate user.
	 *
	 * @param user the user
	 * @return the user
	 */
	@Transactional
	public User deactivateUser(User user) {
		user.setStatus(Status.INACTIVE);
		List<WorkItem> workItems = workItemRepository.findAllByUser(user);
		for (WorkItem workItem : workItems) {
			workItem.setStatus(WorkItemStatus.Unstarted.toString());
		}
		return userRepository.save(user);
	}

	/**
	 * Activate user.
	 *
	 * @param user the user
	 * @return the user
	 */
	@Transactional
	public User activateUser(User user) {
		user.setStatus(Status.ACTIVE);
		return userRepository.save(user);
	}
}
