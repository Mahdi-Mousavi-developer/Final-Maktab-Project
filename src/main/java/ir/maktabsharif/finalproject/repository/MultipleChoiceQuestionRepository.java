package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entity.MultipleChoiceQuestion;
import ir.maktabsharif.finalproject.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultipleChoiceQuestionRepository extends JpaRepository<MultipleChoiceQuestion, Long> {

}
