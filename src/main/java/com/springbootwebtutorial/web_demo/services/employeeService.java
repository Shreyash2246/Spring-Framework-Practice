package com.springbootwebtutorial.web_demo.services;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import com.springbootwebtutorial.web_demo.dto.EmployeeDTO;
import com.springbootwebtutorial.web_demo.entities.EmployeeEntity;
import com.springbootwebtutorial.web_demo.repositories.EmployeeRepository;

@Service
public class employeeService {

    final EmployeeRepository employeeRepository;

    final ModelMapper modelMapper;

    public employeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees(Integer age, String sortby) {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities.stream()
                .map(entity -> modelMapper.map(entity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeEntity inputEmployee) {
        // we can also add validation logic here
        // admin or employee role check can be done here
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        EmployeeEntity savedEmployee = employeeRepository.save(toSaveEntity);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    public EmployeeDTO getEmployeeById(Long id, EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(id);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public boolean isEmployeeExist(Long id) {
        return employeeRepository.existsById(id);
    }

    public Boolean deleteEmployeeById(Long id) {
        boolean exists = isEmployeeExist(id);
        if (!exists) {
            return false;
        }
        employeeRepository.deleteById(id);
        return true;
    }

    public EmployeeDTO patchEmployee(Long id, Map<String,Object> updates) {
        boolean exists = isEmployeeExist(id);
        if (!exists) return null;
        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
        updates.forEach((field, value) -> {
            Field fieldToUpdate = ReflectionUtils.getRequiredField(EmployeeEntity.class, field);
            fieldToUpdate.setAccessible(true);
            ReflectionUtils.setField(fieldToUpdate, employeeEntity, value);
        });
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }

}
