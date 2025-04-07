package ir.maktabsharif.finalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExamStudent extends BaseEntity<Long> {
    @ManyToOne
    @JoinColumn(name="exam-id")
    private Exam exam;
    @ManyToOne
    @JoinColumn(name="student-id")
    private User student;
    @Enumerated(EnumType.STRING)
    private ExamStudentState examStudentState;
    private int finalScore;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
