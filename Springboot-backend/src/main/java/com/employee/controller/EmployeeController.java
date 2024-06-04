package com.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.exception.ResourceNotfoundException;
import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;

import jakarta.websocket.server.PathParam;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
	
//	@GetMapping(value="/")
//	public String getPage() {
//		return "Welcome";
//		
//	}
	
	//get All employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
	 return employeeRepository.findAll();
	}
	
	//create employee
	@PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
      return employeeRepository.save(employee);
	
    }
	//get Employee by id
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeebyid(@PathVariable Long id) {
		Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotfoundException("Employee Not Found"));
		return ResponseEntity.ok(employee);
	}

	//update the employee details
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployeedetails(@PathVariable Long id,@RequestBody Employee employeedetails){
			Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotfoundException("Employee Not Found"));
			employee.setFirstName(employeedetails.getFirstName());
			employee.setLastName(employeedetails.getLastName());
			employee.setEmailid(employeedetails.getEmailid());
			Employee updatedetails=employeeRepository.save(employee);
			return ResponseEntity.ok(updatedetails);
	}
	//delete employee rest api
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotfoundException("Employee Not Found"));
		employeeRepository.delete(employee);
		Map<String,Boolean> response=new HashMap<>();
		response.put("deleted",Boolean.TRUE);
		return ResponseEntity.ok(response);
		
	}
}
