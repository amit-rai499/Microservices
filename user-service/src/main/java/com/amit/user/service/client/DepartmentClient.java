package com.amit.user.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.amit.user.service.ValueObject.Department;


@FeignClient(name = "DEPARTMENT-SERVICE")
public interface DepartmentClient {

	@GetMapping("/departments/{id}")
	public Department getDepartment(@PathVariable("id") Long departmentId);
}
