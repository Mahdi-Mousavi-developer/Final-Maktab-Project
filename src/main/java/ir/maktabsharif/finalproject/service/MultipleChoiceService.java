package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.entity.MultipleChoiceQuestion;

public interface MultipleChoiceService {

    void Save(MultipleChoiceQuestion multipleChoiceQuestion);

    void update(Long id ,MultipleChoiceQuestion multipleChoiceQuestion);

    MultipleChoiceQuestion findById(Long id);
    Boolean checkAnswer(Long questionId, String answer);
}
