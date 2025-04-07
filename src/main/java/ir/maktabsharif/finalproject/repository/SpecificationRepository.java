package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpecificationRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {

}
