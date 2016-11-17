package se.jpa.extreme;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import se.jpa.extreme.model.Employee;
import se.jpa.extreme.model.User;
import se.jpa.extreme.repository.EmployeeRepository;
import se.jpa.extreme.repository.JpaEmployeeRepository;
import se.jpa.extreme.repository.JpaUserRepository;
import se.jpa.extreme.repository.UserRepository;

public final class Main {

	private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("PersistenceUnit");
	
	public static void main(String[] args) {
		EmployeeRepository employeeRepository = new JpaEmployeeRepository(FACTORY);
		
		employeeRepository.saveOrUpdate(new Employee("Luke", "Skywalker", "1001"));
		employeeRepository.saveOrUpdate(new Employee("Leia", "Skywalker", "2002"));
		employeeRepository.getAll().forEach(e -> System.out.println(e.getNumber()));

		UserRepository userRepository = new JpaUserRepository(FACTORY);
		userRepository.saveOrUpdate(new User("masteranca"));
		
	}
}
