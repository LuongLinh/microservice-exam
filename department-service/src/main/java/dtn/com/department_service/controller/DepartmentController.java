package dtn.com.department_service.controller;

import dtn.com.department_service.entity.DepartmentEntity;
import dtn.com.department_service.model.Department;
import dtn.com.department_service.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> getAllAccounts() {
        return departmentService.getAllDepartments().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    private Department toModel(DepartmentEntity entity) {
        return new Department(
                entity.getId(),
                entity.getDepartmentName()
        );
    }
}
