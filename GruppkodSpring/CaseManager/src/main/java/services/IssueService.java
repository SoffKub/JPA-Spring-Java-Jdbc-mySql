package services;

import model.Issue;
import model.WorkItem;
import model.WorkItemStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.IssueRepo;
import repository.WorkItemRepo;

import javax.transaction.Transactional;
import java.util.List;


/**
 * The Class IssueService- 
 * Create, retrieve, update an issue, Gets the all WorkItems with issue, Gets the issue by name

 * issue - a remark that can be given a workItem when it is not accepted. 
 * Can only be added to a work item that has Status DONE, and thus changes the status to UNSTARTED. 
 * Retrieve all work item which has an issue.
 */


/**
 *	@Autowire : Spring beans are injected or acquired using autowire annotation.
	@Service : allowing for implementation classes to be autodetected through classpath scanning-
	Annotate service classes with @Service. All your business logic will be in Service classes. 
	Controller and DAO layers will not have any business logic in there.
	@Transactional defines the scope of a single database transaction. 
	The database transaction happens inside the scope of a persistence context.*/

@Service
public class IssueService {

	/** The issue repository. */
	private IssueRepo issueRepository;
	
	/** The work item repository. */
	private WorkItemRepo workItemRepository;

	/**
	 * Instantiates a new issue service.
	 *
	 * @param issueRepository the issue repository
	 * @param workItemRepository the work item repository
	 */
	@Autowired
	public IssueService(IssueRepo issueRepository, WorkItemRepo workItemRepository) {
		this.issueRepository = issueRepository;
		this.workItemRepository = workItemRepository;
	}

	/**
	 * Creates the issue.
	 *
	 * @param issue the issue
	 * @return the issue
	 */
	
	@Transactional
	public Issue createIssue(Issue issue) {
		try {
			if (issue.getId() == null) {
				Issue newIssue = issueRepository.save(issue);
				return newIssue;
			} else {
				throw new ServiceException("Create issue " + issue.getDescription() + " failed. Issue already exists");
			}
		} catch (Exception e) {
			throw new ServiceException("Create issue " + issue.getDescription() + " failed", e);
		}

	}

	/**
	 * Assign to workItem.
	 *
	 * @param issue the issue
	 * @param workItem the work item
	 */
	@Transactional
	public void assignToWorkItem(Issue issue, WorkItem workItem) {
		WorkItem newWorkItem = workItem;
		Issue newIssue = issue;
		try {
			if (issue.getId() == null) {
				newIssue = issueRepository.save(issue);
			}
			if (workItem.getId() == null) {
				newWorkItem = workItemRepository.save(workItem);
			}
			if (newWorkItem.getStatus().equals("Done")) {
				newWorkItem.setIssue(newIssue);
				newWorkItem.setStatus(WorkItemStatus.Unstarted.toString());
				workItemRepository.save(newWorkItem);
			} else {
				throw new ServiceException("Assign issue to work item failed. Status of work item is not 'Done'");
			}
		} catch (Exception e) {
			throw new ServiceException("Could not assign issue to work item", e);
		}

	}

	/**
	 * Update issue.
	 *
	 * @param issue the issue
	 * @param new_description the new description
	 * @return the issue
	 */
	@Transactional
	public Issue updateIssue(Issue issue, String new_description) {
		try {
			Issue newIssue = issue;
			if (issue.getId() == null) {
				newIssue = issueRepository.save(issue);
			}
			Issue findIssue = issueRepository.findByDescription(new_description);
			if (!issueRepository.exists(issue.getId())) {
				throw new ServiceException(
						"Could not update issue.Issue:" + issue.getDescription() + " doesn't exist.");
			}
			if (findIssue == null) {
				newIssue.setDescription(new_description);
				issueRepository.save(newIssue);
				return newIssue;
			} else
				throw new ServiceException("Issue with name:" + new_description + " already exists.");
		} catch (Exception e) {
			throw new ServiceException("Could not update issue with id:" + issue.getId(), e);
		}
	}

	/**
	 * Gets the all items with issue.
	 *
	 * @param issue the issue
	 * @return the all items with issue
	 */
	public List<WorkItem> getAllItemsWithIssue(Issue issue) {
		try {
			return issueRepository.findAllByIssue(issue);
		} catch (Exception e) {
			throw new ServiceException("Could not get all items with issue:" + issue.getDescription(), e);
		}
	}

	/**
	 * Gets the issue by name.
	 *
	 * @param name the name
	 * @return the issue by name
	 */
	public Issue getIssueByName(String name) {
		try {
			return issueRepository.findByDescription(name);
		} catch (Exception e) {
			throw new ServiceException("Could not get issue with name:" + name, e);
		}
	}

}
