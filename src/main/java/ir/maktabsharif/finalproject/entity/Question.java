package ir.maktabsharif.finalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "question-type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question extends BaseEntity<Long> {
    private String title;
    private int questionScore;
    private String questionText;
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
    @ManyToOne
    @JoinColumn(name = "teacher-id")
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "exam-id")
    private Exam exam;
    @ManyToOne
    @JoinColumn(name = "course-id")
    private Course course;
    @OneToMany(mappedBy = "question")
    private List<Answer> answers=new ArrayList<>();
}
