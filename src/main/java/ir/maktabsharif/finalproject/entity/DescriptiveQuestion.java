package ir.maktabsharif.finalproject.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("DESCRIPTIVE")
@AllArgsConstructor
@NoArgsConstructor
public class DescriptiveQuestion extends Question {
    private String answer;
    public DescriptiveQuestion copy() {
        DescriptiveQuestion copy = new DescriptiveQuestion();
        copy.setTitle(this.getTitle());
        copy.setQuestionText(this.getQuestionText());
        copy.setQuestionType(this.getQuestionType());
        copy.setTeacher(this.getTeacher());
        copy.setCourse(this.getCourse());
        return copy;
    }
}
