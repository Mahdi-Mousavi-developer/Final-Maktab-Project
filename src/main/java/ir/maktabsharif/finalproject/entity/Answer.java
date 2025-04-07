package ir.maktabsharif.finalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Answer extends BaseEntity<Long>{
    private String studentAnswer;
    private int studentScore;
    @ManyToOne
    @JoinColumn(name = "question-id")
    private Question question;
    @ManyToOne
    @JoinColumn(name = "student-id")
    private User student;
}
