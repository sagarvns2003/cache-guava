package com.vidya.dao;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.vidya.model.Employee;

public class EmployeeDAO {

	public Optional<Employee> getEmployeeByID(String employeeID) {
		return Optional.ofNullable(new Employee(employeeID, "Name" + employeeID, "department"));
	}

	public List<Employee> getEmployees() {
		List<Employee> emps = ImmutableList.<Employee>builder()
				.add(new Employee("100", "Name1", "department"))
				.add(new Employee("101", "Name1", "department"))
				.add(new Employee("102", "Name1", "department"))
				.build();
		return emps;
	}
}