package com.vidya;

import com.google.common.cache.CacheStats;
import com.vidya.model.Employee;
import com.vidya.service.EmployeeService;

public class AppCache {

	private EmployeeService employeeService;

	public AppCache() {
		this.employeeService = new EmployeeService();
	}

	public static void main(String[] args) throws Exception {

		AppCache app = new AppCache();

		CacheStats cacheStats = app.employeeService.getCacheStats();
		System.out.println(cacheStats.toString());

		Employee employee = app.employeeService.getEmployee("100");
		employee = app.employeeService.getEmployee("101");
		employee = app.employeeService.getEmployee("102");
		employee = app.employeeService.getEmployee("103");
		employee = app.employeeService.getEmployee("104");
		employee = app.employeeService.getEmployee("105");
		
		cacheStats = app.employeeService.getCacheStats();
		System.out.println(cacheStats.toString());
		
		employee = app.employeeService.getEmployee("100");
		employee = app.employeeService.getEmployee("104");
		employee = app.employeeService.getEmployee("100");
		employee = app.employeeService.getEmployee("106");
		
		cacheStats = app.employeeService.getCacheStats();
		System.out.println(cacheStats.toString());

		System.out.println("Cache Data: " + app.employeeService.getEmployeeCache().asMap().toString());
	}

}