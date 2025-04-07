package ir.maktabsharif.finalproject.entity.Dto;

import ir.maktabsharif.finalproject.entity.*;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class MultipleChoiceQuestionDto {
    private String title;
    private int questionScore;
    private String questionText;
    private QuestionType questionType;
    private User teacher;
    private Exam exam;
    private Course course;
    private int correctOption;
    private List<String> choices;
}
