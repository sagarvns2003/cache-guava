package com.vidya.service;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.vidya.cache.EmplyeeCacheManager;
import com.vidya.model.Employee;

public class EmployeeService {

	public EmployeeService() {
		EmplyeeCacheManager.preloadEmployeeCache();
	}

	public LoadingCache<String, Optional<Employee>> getEmployeeCache() {
		return EmplyeeCacheManager.getEmployeeCache();
	}
	
	public Employee getEmployee(String employeeID) throws IOException, ExecutionException {
		return EmplyeeCacheManager.getEmployee(employeeID).get();
	}

	public CacheStats getCacheStats() {
		return EmplyeeCacheManager.getCacheStats();
	}
}
