package ir.maktabsharif.finalproject.entity.Dto;

import ir.maktabsharif.finalproject.entity.Course;
import ir.maktabsharif.finalproject.entity.User;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ExamRequestDto {
    private String name;
    private String description;
    private int duration;
    private Course course;
    private User Teacher;
    private LocalDateTime StartDate;
    private LocalDateTime EndDate;
}
