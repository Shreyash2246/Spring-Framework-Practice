package com.springbootwebtutorial.web_demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootwebtutorial.web_demo.entities.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

}
