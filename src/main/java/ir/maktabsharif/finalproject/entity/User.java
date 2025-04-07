package ir.maktabsharif.finalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "WebappUser")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public class User extends BaseEntity<Long> {
    private String username;
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "national_code")
    private String nationalCode;
    private LocalDateTime dob;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private RegistrationStatus registrationStatus = RegistrationStatus.PENDING;

    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;
    @ManyToMany(mappedBy = "students")
    private List<Course> coursesForStudent;

    @OneToMany(mappedBy = "teacher")
    private List<Exam> exams;
}
