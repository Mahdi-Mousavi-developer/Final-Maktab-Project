package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entity.DescriptiveQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptiveQuestionRepository extends JpaRepository<DescriptiveQuestion, Long>{
}
