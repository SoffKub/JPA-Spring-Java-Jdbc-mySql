import model.Team;
import model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.TeamService;
import services.UserService;

import java.util.List;

public final class Main {

	public static void main(String[] args) {

		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
			context.scan("./");
			context.refresh();

			TeamService teamService = context.getBean(TeamService.class);
			UserService userService = context.getBean(UserService.class);
//			WorkItemService workItemService = context.getBean(WorkItemService.class);
//			IssueService issueService = context.getBean(IssueService.class);

			// create team
			Team team1 = new Team("Sweden");
			teamService.createTeam(team1);
			Team team2 = new Team("Norway");
			teamService.createTeam(team2);
			Team team3 = new Team("Finland");
			teamService.createTeam(team3);
			Team team4 = new Team("Denmark");
			teamService.createTeam(team4);

			// update team.
			teamService.updateTeam("Finland", "USA");

			// deactivate team.
			teamService.deactivateTeam("Denmark");

			// get all teams.
			teamService.findAllTeams().forEach(System.out::println);

			// create user and put in team.
			User user = new User("Tyson", "La", "tyson.la90", team1);
			userService.createUser(user);

			// deactivate user.
			userService.deactivateUser(user);

			// get user by ID.
			userService.getUser((long) 13);

			// get user by username.
			userService.getUserByUsername("tyson.la90");

			// get all users by first name.
			List<User> fNameList = userService.getUserByFirstname("Tyson");
			System.out.println(fNameList);

			// get all users by last name.
			List<User> lNameList = userService.getUserByLastname("La");
			System.out.println(lNameList);

			// get all users in team.
			List<User> uTeamList = userService.getAllUsersInTeam(team1);
			System.out.println(uTeamList);

			/* WORK ITEM */

			/* ISSUE */

		}
	}
}
