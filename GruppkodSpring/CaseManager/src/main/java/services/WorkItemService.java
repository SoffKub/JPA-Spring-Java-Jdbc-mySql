package services;

import model.Status;
import model.User;
import model.WorkItem;
import model.WorkItemStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.WorkItemRepo;

import javax.transaction.Transactional;
import java.util.List;

/** The Class WorkItemService- WorkItem - an item that is assigned to a User Features: 
	- Create a Work Item 
	- Update status on WorkItem:UNSTARTED, STARTED or DONE 
	- Find WorkItem by title containing
	- Find WorkItwm by description containing
	- Delete WorkItem
	- add workItem to User 
	- Find all WorkItems by status 
	- Find all by WorkItems by team name 
	- Find all WorkItems by user based on users ID
	- Find all WorkItems by user based on users name
	
*
* - A WorkItem can not be assigned to a user who is disabled 
* - A User can have a maximum of 5 while Work Items
*/

/**
 *	@Autowire : Spring beans are injected or acquired using autowire annotation.
	@Service : allowing for implementation classes to be autodetected through classpath scanning-
	Annotate service classes with @Service. All your business logic will be in Service classes. 
	Controller and DAO layers will not have any business logic in there.
	@Transactional defines the scope of a single database transaction. 
	The database transaction happens inside the scope of a persistence context.*/




@Service
public class WorkItemService {

	/** The workItem repository. */
	private final WorkItemRepo workItemRepository;

	/**
	 * Instantiates a new work item service.
	 *
	 * @param workItemRepository the work item repository
	 */
	@Autowired
	public WorkItemService(WorkItemRepo workItemRepository) {
		this.workItemRepository = workItemRepository;
	}

	/**
	 * Find all by WorkItems by team name.
	 *
	 * @param name the name
	 * @return the list
	 */
	public List<WorkItem> findAllByTeamName(String name) {
		return workItemRepository.findAllByTeamName(name);
	}

	/**
	 * Find all WorkItems by status.
	 *
	 * @param status the status
	 * @return the list
	 */
	@Transactional
	public List<WorkItem> findAllByStatus(WorkItemStatus status) {
		return this.workItemRepository.findAllByStatus(status.toString());
	}

	/**
	 * Find all WorkItems by user ID.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	@Transactional
	public List<WorkItem> findAllByUser(Long userId) {
		return this.workItemRepository.findAllByUser(userId);
	}

	/**
	 * Find all WorkItem by users name.
	 *
	 * @param user the user
	 * @return the list
	 */
	@Transactional
	public List<WorkItem> findAllByUser(User user) {
		return this.workItemRepository.findAllByUser(user);
	}

	/**
	 * Creates the WokItem.
	 *
	 * @param workItem the work item
	 * @return the work item
	 */
	@Transactional
	public WorkItem create(WorkItem workItem) {
		return this.workItemRepository.save(workItem);
	}

	/**
	 * Update status on WorkItem.
	 *
	 * @param id the id
	 * @param status the status
	 * @return true, if successful
	 */
	@Transactional
	public boolean updateStatus(Long id, WorkItemStatus status) {
		WorkItem workItem = workItemRepository.findOne(id);
		if (workItem == null)
			return false;
		workItem.setStatus(status.toString());
		workItemRepository.save(workItem);
		return true;
	}

	/**
	 * Delete WorkItem.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	@Transactional
	public boolean delete(Long id) {
		WorkItem workItem = workItemRepository.findOne(id);
		if (workItem == null)
			return false;
		workItemRepository.delete(workItem);
		return true;
	}

	/**
	 * Delete.
	 *
	 * @param workItem the work item
	 * @return true, if successful
	 */
	public boolean delete(WorkItem workItem) {
		return delete(workItem.getId());
	}

	/**
	 * Find WorkItem by title containing.
	 *
	 * @param text the text
	 * @return the list
	 */
	public List<WorkItem> findByTitleContaining(String text) {
		return workItemRepository.findByTitleContaining(text);
	}

	/**
	 * Find WorkItwm by description containing.
	 *
	 * @param text the text
	 * @return the list
	 */
	public List<WorkItem> findByDescriptionContaining(String text) {
		return workItemRepository.findByDescriptionContaining(text);
	}

	/**
	 * Adds the workItem to user.
	 *
	 * @param workItem the work item
	 * @param user the user
	 */
	@Transactional
	public void addWorkItemToUser(WorkItem workItem, User user) {
		if (user.getStatus().equals(Status.ACTIVE.toString()) && checkNumberofWorkItems(user)) {
			workItem.setUser(user);
			this.workItemRepository.save(workItem);
		} else {
			throw new ServiceException("Can not add a user to work item. User is not active or already has more than 5 work items.");
		}
	}

	/**
	 * This method checks to see if there are 5 work items or less assigned to a user.
	 *
	 * @param user the user
	 * @return true, if successful
	 */
	public boolean checkNumberofWorkItems(User user) {
		return this.workItemRepository.findAllByUser(user).size() < 5;
	}
}
