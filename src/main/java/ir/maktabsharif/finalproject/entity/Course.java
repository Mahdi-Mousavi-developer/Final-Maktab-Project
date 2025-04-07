package ir.maktabsharif.finalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Course extends BaseEntity<Long> {
    private String title;


    private String uniqueIdentifier; // این فیلد به صورت پیش‌فرض مقدار می‌گیرد
    @PrePersist
    public void generateUniqueIdentifier() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        long numericValue = Long.parseLong(uuid.substring(0, 10), 16); // تبدیل بخشی از UUID به عدد
        this.uniqueIdentifier = String.valueOf(numericValue).substring(0, 10); // محدود کردن به ۱۰
    }



    @Column(name = "start-date")
    private LocalDateTime StartDate;

    @Column(name = "end-date")
    private LocalDateTime EndDate;


    @ManyToOne
    @JoinColumn(name = "teacher-id")
    private User teacher;

    @ManyToMany
    @JoinTable(
            name = "course-student",
            joinColumns = @JoinColumn(name = "course-id"),
            inverseJoinColumns = @JoinColumn(name = "student-id")
    )
    private List<User> students;

    @OneToMany(mappedBy = "course")
    private List<Exam> examList;

}
