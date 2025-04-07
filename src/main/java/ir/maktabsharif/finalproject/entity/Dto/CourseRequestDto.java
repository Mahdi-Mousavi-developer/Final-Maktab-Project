package ir.maktabsharif.finalproject.entity.Dto;

import ir.maktabsharif.finalproject.entity.User;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;

@Data
@Builder
public class CourseRequestDto {
    private String title;
    private String UniqueIdentifier;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private User teacher;
}
