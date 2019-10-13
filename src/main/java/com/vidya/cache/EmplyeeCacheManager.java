package com.vidya.cache;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

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
			.refreshAfterWrite(1, TimeUnit.MINUTES)
			.expireAfterAccess(24, TimeUnit.HOURS)
			.recordStats()
			.build(new CacheLoader<String, Optional<Employee>>() {
				@Override
				public Optional<Employee> load(String employeeID) throws IOException {
					System.out.println("Retreiving employee["+employeeID+"] details from db and populating the cache and return.");
					return employeeDAO.getEmployeeByID(employeeID);
				}
			});

    public static void preloadEmployeeCache() {
    	List<Employee> employees = employeeDAO.getEmployees();
    	Map<String, Optional<Employee>> emplMap = employees.stream()
    			.collect(Collectors.toMap(Employee::getId, emp -> Optional.ofNullable(emp)));
    	employeeCache.putAll(emplMap);
    }
	
	public static Optional<Employee> getEmployee(String employeeID) throws IOException, ExecutionException {
		Optional<Employee> employee = employeeCache.get(employeeID);
		return employee;
	}

	public static CacheStats getCacheStats() {
		return employeeCache.stats();
	}
}
