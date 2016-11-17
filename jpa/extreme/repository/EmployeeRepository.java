package se.jpa.extreme.repository;

import java.util.List;

import se.jpa.extreme.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee>{

	List<Employee> getAll();
	Employee getByNumber(String number);
	
}
