package ir.maktabsharif.finalproject.service.Impl;

import ir.maktabsharif.finalproject.Specification.UserSpecification;
import ir.maktabsharif.finalproject.entity.Role;
import ir.maktabsharif.finalproject.entity.User;
import ir.maktabsharif.finalproject.exception.EntityNotFoundException;
import ir.maktabsharif.finalproject.repository.SpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SpecificationService {
    private final SpecificationRepository specificationRepository;

    @Autowired
    public SpecificationService(SpecificationRepository specificationRepository) {
        this.specificationRepository = specificationRepository;
    }

    public List<User> searchUsers(String firstName, String lastName, String role) {
//        Role foundedRole = this.findRoleByString(role);
        Specification<User> spec = UserSpecification.searchUsers(firstName, lastName, role);
        return specificationRepository.findAll(spec);
    }

    public Role findRoleByString(String role) {
        if (role.equalsIgnoreCase("STUDENT")) {
            return Role.STUDENT;
        } else {
            return Role.TEACHER;
        }
    }
}

