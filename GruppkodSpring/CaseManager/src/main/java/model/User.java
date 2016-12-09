package model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
public final class User {

	@Id
	@GeneratedValue
	private Long id;

	private String firstname;
	private String lastname;
	private String status;

	@Column(unique = true)
	private String username;

	@ManyToOne
	private Team team;

	public User() {
	}

	public User(String firstname, String lastname, String username, Team team) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.team = team;
		this.status = Status.ACTIVE.toString();
	}

	public Long getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(Status userStatus) {
		this.status = userStatus.toString();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}