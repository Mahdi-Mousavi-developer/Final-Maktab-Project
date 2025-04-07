package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entity.RegistrationStatus;
import ir.maktabsharif.finalproject.entity.Role;
import ir.maktabsharif.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findWebappUserByUsername(String username);

    @Query(value = " select w from User w where w.nationalCode like %:keyword%")
    List<User> findWebappUserByNationalCode(@Param("keyword") String nationalCode);

    List<User> findWebappUserByRole(Role role);

    @Query(value = "from User w where w.registrationStatus=:registrationStatus ", nativeQuery = false)
    List<User> findByRegistrationStatus(@Param("registrationStatus") RegistrationStatus registrationStatus);

    @Query(value = " select w from User w where w.firstName like %:keyword%")
    List<User> findByFirstName(@Param("keyword") String firstname);

    @Query(value = " select w from User w where w.lastName like %:keyword%")
    List<User> findByLastName(@Param("keyword") String lastname);

    @Query(value = " select w from User w where w.username like %:keyword%")
    List<User> findByUsernameForSearch(@Param("keyword") String username);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u " +
            "WHERE u.role = 'STUDENT' " +
            "AND u.id NOT IN (SELECT s.id FROM Course c JOIN c.students s WHERE c.id = :courseId)")
    List<User> findStudentsNotInCourse(@Param("courseId") Long courseId);


    List<User> findWebappUserByDob(Date dob);

    List<User> findWebappUserByCreateDate(Date createDate);
}

