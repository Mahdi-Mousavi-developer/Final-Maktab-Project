package ir.maktabsharif.finalproject.service.Impl;

import ir.maktabsharif.finalproject.entity.Course;
import ir.maktabsharif.finalproject.entity.Dto.CourseRequestDto;
import ir.maktabsharif.finalproject.entity.User;
import ir.maktabsharif.finalproject.exception.EntityNotFoundException;
import ir.maktabsharif.finalproject.repository.CourseRepository;
import ir.maktabsharif.finalproject.service.CourseService;
import ir.maktabsharif.finalproject.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private UserService userService;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, UserService userService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
    }


    @Override
    public List<Course> findAllCourse() {
        return courseRepository.findAll();
    }

    @Override
    public Course findCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course Not Found"));
    }

    @Override
    public void saveCourse(CourseRequestDto course) throws EntityNotFoundException {
        Course courseEntity = new Course();
        courseEntity.setTitle(course.getTitle());
        courseEntity.setEndDate(course.getEndDate());
        courseEntity.setStartDate(course.getStartDate());
        User user = userService.findUserById(course.getTeacher().getId());
        courseEntity.setTeacher(user);
        courseRepository.save(courseEntity);
    }

    @Override
    public void updateCourse(Long id, CourseRequestDto course) throws EntityNotFoundException {
        Course courseEntity = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course Not Found"));
        courseEntity.setStudents(courseEntity.getStudents());
        courseEntity.setTitle(course.getTitle());
        courseEntity.setEndDate(course.getEndDate());
        courseEntity.setStartDate(course.getStartDate());
        courseEntity.setUniqueIdentifier(course.getUniqueIdentifier());
        User user = userService.findUserById(course.getTeacher().getId());
        courseEntity.setTeacher(user);
        courseRepository.save(courseEntity);
    }

    @Transactional
    @Override
    public void deleteCourseById(Long id) throws EntityNotFoundException {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        course.getStudents().clear();
        course.setTeacher(null);

        courseRepository.save(course);
        courseRepository.delete(course);

    }

    @Override
    public void addStudentToCourse(Long courseId, Long studentId) throws EntityNotFoundException {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        User student = userService.findUserById(studentId);
        course.getStudents().add(student);
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void removeStudentFromCourse(Long courseId, Long studentId) throws EntityNotFoundException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        User student = userService.findUserById(studentId);

        course.getStudents().remove(student);
        courseRepository.save(course);
    }

    @Override
    public List<Course> findCourseByTeacherId(User user) {
        return courseRepository.findByTeacher(user);
    }
}
