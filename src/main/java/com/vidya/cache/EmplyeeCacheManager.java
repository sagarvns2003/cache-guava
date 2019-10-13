package com.vidya.cache;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.vidya.dao.EmployeeDAO;
import com.vidya.model.Employee;

public class EmplyeeCacheManager {

	private static EmployeeDAO employeeDAO = new EmployeeDAO();
	
	private static LoadingCache<String, Optional<Employee>> employeeCache = CacheBuilder.newBuilder()
			.maximumSize(1000)
			.expireAfterAccess(24, TimeUnit.HOURS)
			.recordStats()
			.build(new CacheLoader<String, Optional<Employee>>() {
				@Override
				public Optional<Employee> load(String employeeID) throws IOException {
					System.out.println("Retreiving employee["+employeeID+"] details from db and populating the cache and return.");
					return employeeDAO.getEmployeeByID(employeeID);
				}
			});


	
	public static Optional<Employee> getEmployee(String employeeID) throws IOException, ExecutionException {
		Optional<Employee> employee = employeeCache.get(employeeID);
		return employee;
	}

	public static CacheStats getCacheStats() {
		return employeeCache.stats();
	}
}
