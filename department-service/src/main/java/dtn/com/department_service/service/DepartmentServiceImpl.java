package dtn.com.department_service.service;

import dtn.com.department_service.entity.DepartmentEntity;
import dtn.com.department_service.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentEntity> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
