package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.entity.DescriptiveQuestion;

public interface DescriptiveQuestionService {
void save(DescriptiveQuestion question);
DescriptiveQuestion findById(Long id);
}
