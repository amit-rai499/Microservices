package com.amit.department.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amit.department.service.entity.Department;
import com.amit.department.service.repository.DepartmentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepartmentService {
	
	@Autowired
	private DepartmentRepository departmentRepository;

	
	public Department saveDepartment(Department department) {
		log.info("Inside save department method of Department Service");
		return departmentRepository.save(department);
	}


	public Department findDepartmentById(Long departmentId) {
		log.info("Inside  findDepartmentById method of Department Service");
		return departmentRepository.findByDepartmentId(departmentId);
	}

}
