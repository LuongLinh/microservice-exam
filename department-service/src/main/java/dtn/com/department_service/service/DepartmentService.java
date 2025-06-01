package dtn.com.department_service.service;

import dtn.com.department_service.entity.DepartmentEntity;

import java.util.List;

public interface DepartmentService {
    List<DepartmentEntity> getAllDepartments();
}
