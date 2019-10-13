package com.vidya.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheStats;
import com.vidya.cache.EmplyeeCacheManager;
import com.vidya.model.Employee;

public class EmployeeService {

	public EmployeeService() {
		EmplyeeCacheManager.preloadEmployeeCache();
	}

	public Employee getEmployee(String employeeID) throws IOException, ExecutionException {
		return EmplyeeCacheManager.getEmployee(employeeID).get();
	}

	public CacheStats getCacheStats() {
		return EmplyeeCacheManager.getCacheStats();
	}
}
