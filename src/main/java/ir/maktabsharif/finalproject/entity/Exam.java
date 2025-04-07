package ir.maktabsharif.finalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Exam extends BaseEntity<Long>{
    private String name;
    private String description;
    private int duration;
    private int score;
    @Column(name = "start-date")
    private LocalDateTime StartDate;
    @Column(name = "end-date")
    private LocalDateTime EndDate;
    @ManyToOne
    @JoinColumn(name ="course-id")
    private Course course;
    @ManyToOne
    @JoinColumn(name="teacher-id")
    private User teacher;

    @OneToMany(mappedBy = "exam")
    private List<Question> questions;

}
