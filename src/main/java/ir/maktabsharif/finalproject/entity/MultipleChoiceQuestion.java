package ir.maktabsharif.finalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("MULTIPLE_CHOICE")
@AllArgsConstructor
@NoArgsConstructor
public class MultipleChoiceQuestion extends Question {
    private String correctOption;


    @ElementCollection
    @CollectionTable(name = "multiple-choice", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "choice")
    private List<String> choices;


    public MultipleChoiceQuestion copy() {
        MultipleChoiceQuestion copy = new MultipleChoiceQuestion();
        copy.setTitle(this.getTitle());
        copy.setQuestionText(this.getQuestionText());
        copy.setQuestionType(this.getQuestionType());
        copy.setTeacher(this.getTeacher());
        copy.setCourse(this.getCourse());
        copy.setCorrectOption(this.getCorrectOption());
        if (this.choices != null) {
            copy.setChoices(new ArrayList<>(this.choices));
        } else {
            copy.setChoices(null);
        }
        return copy;
    }
}
