package com.amit.department.service.repository;

import org.springframework.stereotype.Repository;

import com.amit.department.service.entity.Department;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long>{

	Department findByDepartmentId(Long departmentId);

}
