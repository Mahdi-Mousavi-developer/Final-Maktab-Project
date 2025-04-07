package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.entity.Course;
import ir.maktabsharif.finalproject.entity.Dto.CourseRequestDto;
import ir.maktabsharif.finalproject.entity.User;
import ir.maktabsharif.finalproject.exception.EntityNotFoundException;

import java.util.List;


public interface CourseService {
    List<Course> findAllCourse();

    Course findCourseById(Long id);

    void saveCourse(CourseRequestDto course) throws EntityNotFoundException;

    void deleteCourseById(Long id) throws EntityNotFoundException;

    void updateCourse(Long id, CourseRequestDto courseRequestDto) throws EntityNotFoundException;

    void addStudentToCourse(Long courseId, Long studentId) throws EntityNotFoundException;

    void removeStudentFromCourse(Long courseId, Long studentId) throws EntityNotFoundException;
//SECOND PORT METHOD
    List<Course> findCourseByTeacherId(User user);
}
