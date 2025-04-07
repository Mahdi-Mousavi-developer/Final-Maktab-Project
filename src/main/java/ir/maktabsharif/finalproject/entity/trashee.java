//package ir.maktabsharif.finalproject.entity;
//
//import ir.maktabsharif.finalproject.exception.EntityNotFoundException;
//import ir.maktabsharif.finalproject.service.*;
//import ir.maktabsharif.finalproject.service.password.CustomUserDetails;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
public class trashee {
//    @Autowired
//    public CourseStudentController(UserService userService, CourseService courseService, ExamService examService, QuestionService questionService, ExamStudentService examStudentService, MultipleChoiceService multipleChoiceService, AnswerService answerService) {
//        this.userService = userService;
//        this.courseService = courseService;
//        this.examService = examService;
//        this.questionService = questionService;
//        this.examStudentService = examStudentService;
//        this.multipleChoiceService = multipleChoiceService;
//        this.answerService = answerService;
//    }
//
//    @GetMapping("/course/{id}/{studentId}")
//    public String ShowCoursePage(@PathVariable Long id,
//                                 @PathVariable Long studentId,
//                                 Model model) throws EntityNotFoundException {
//        User user = userService.findUserById(studentId);
//        model.addAttribute("user", user);
//        Course course = courseService.findCourseById(id);
//        model.addAttribute("course", course);
//        List<Exam> examList = course.getExamList();
//        model.addAttribute("exams", examList);
//        return "/student/course";
//    }
//
//    @GetMapping("/exam/{examId}/{studentId}")
//    public String startExam(@PathVariable Long examId,
//                            @PathVariable Long studentId,
//                            RedirectAttributes redirectAttributes
//    ) throws EntityNotFoundException {
//
//        // دریافت اطلاعات کاربر و امتحان
//        User user = userService.findUserById(studentId);
//        Exam exam = examService.getExamById(examId);
//
////        // بررسی زمان امتحان
////        LocalDateTime now = LocalDateTime.now();
////        if (now.isBefore(exam.getStartDate())) {
////            model.addAttribute("message", "Exam has not started yet.");
////            model.addAttribute("courseId", exam.getCourse().getId()); // اضافه کردن courseId برای دکمه بازگشت
////            model.addAttribute("studentId", studentId); // اضافه کردن studentId برای دکمه بازگشت
////            return "/student/exam-not-started"; // صفحه‌ای که نشان می‌دهد امتحان شروع نشده است
////        } else if (now.isAfter(exam.getEndDate())) {
////            model.addAttribute("message", "Exam has ended.");
////            model.addAttribute("courseId", exam.getCourse().getId()); // اضافه کردن courseId برای دکمه بازگشت
////            model.addAttribute("studentId", studentId); // اضافه کردن studentId برای دکمه بازگشت
////            return "/student/exam-ended"; // صفحه‌ای که نشان می‌دهد امتحان به پایان رسیده است
////        }
//        // اگر زمان امتحان فعال باشد، اطلاعات را به مدل اضافه کنید
//        if (examStudentService.findByStudentIdAndExamId(studentId, examId) == null) {
//
//            ExamStudent examStudent = new ExamStudent();
//            examStudent.setExam(exam);
//            examStudent.setStudent(user);
//            examStudent.setStartDate(LocalDateTime.now());
//            examStudent.setEndDate(LocalDateTime.now().plusMinutes(exam.getDuration()));
//            examStudentService.create(examStudent);
//        }
//        int questionIndex = 0;
//        redirectAttributes.addAttribute("questionIndex", questionIndex);
//
//        return "redirect:/studentsController/start-exam/" + examId;
//    }
//
//    @GetMapping("/start-exam/{examId}")
//    public String doingExam(@AuthenticationPrincipal UserDetails userDetails,
//                            @PathVariable Long examId,
//                            @RequestParam int questionIndex,
//                            @RequestParam(required = false) String answer,
//                            @RequestParam(required = false) String massage,
//                            Model model) throws EntityNotFoundException {
//
//        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
//        Long studentId = customUserDetails.getWebappUser().getId();
//        Exam exam = examService.getExamById(examId);
//        User student = userService.findUserById(studentId);
//        List<Question> questions = exam.getQuestions();
//        Question question = questions.get(questionIndex);
//        if (question.getQuestionType().equals(QuestionType.MULTIPLE_CHOICE)) {
//            MultipleChoiceQuestion multipleChoiceQuestion = multipleChoiceService.findById(question.getId());
//            model.addAttribute("multipleChoiceQuestion", multipleChoiceQuestion);
//        } else if (question.getQuestionType().equals(QuestionType.DESCRIPTIVE)) {
//            model.addAttribute("question", question);
//        }
//        model.addAttribute("massage", massage);
//        model.addAttribute("questionIndex", questionIndex);
//        model.addAttribute("studentId", studentId);
//        model.addAttribute("exam", exam);
//        model.addAttribute("countOfQuestions", questions.size());
//        return "/student/doingExam";
//    }
//
//    @PostMapping("/start-exam/{examId}/{studentId}")
//    public String submitAnswer(@PathVariable Long examId,
//                               @PathVariable Long studentId,
//                               @RequestParam int questionIndex,
//                               @RequestParam(required = false) String answer,
//                               RedirectAttributes redirectAttributes) throws EntityNotFoundException {
//        Exam exam = examService.getExamById(examId);
//        User student = userService.findUserById(studentId);
//        List<Question> questions = exam.getQuestions();
//        Question question = questions.get(questionIndex);
//        Long questionId = question.getId();
//
//        Boolean checkAnswer = false;
//        if (question.getQuestionType().equals(QuestionType.MULTIPLE_CHOICE)) {
//            checkAnswer = multipleChoiceService.checkAnswer(questionId, answer);
//        }
//        if (answerService.findByStudentIdAndQuestionId(studentId, questionId) == null) {
//            Answer answerEntity = new Answer();
//            answerEntity.setQuestion(question);
//            answerEntity.setStudent(student);
//            answerEntity.setStudentAnswer(answer);
//            if (checkAnswer) {
//                answerEntity.setStudentScore(question.getQuestionScore());
//            } else {
//                answerEntity.setStudentScore(0);
//            }
//            answerService.createAnswer(answerEntity);
//        } else {
//            Answer answerEntity = answerService.findByStudentIdAndQuestionId(studentId, questionId);
//            answerEntity.setStudentAnswer(answer);
//            if (checkAnswer) {
//                answerEntity.setStudentScore(question.getQuestionScore());
//            } else {
//                answerEntity.setStudentScore(0);
//            }
//            answerService.updateAnswer(answerEntity);
//        }
//        // پردازش پاسخ کاربر (ذخیره در دیتابیس یا بررسی پاسخ)
//        // ...
//
//        // ریدایرکت به سوال بعدی یا قبلی
//        redirectAttributes.addAttribute("massage", "پاسخ شما ثبت شد");
//        redirectAttributes.addAttribute("examId", examId);
//        redirectAttributes.addAttribute("questionIndex", questionIndex);
//        return "redirect:/studentsController/start-exam/{examId}";
//    }
//
//    @PostMapping("/finish-exam/{examId}/{studentId}")
//    public String finishExam(@PathVariable Long examId,
//                             @PathVariable Long studentId,
//                             RedirectAttributes redirectAttributes) throws EntityNotFoundException {
//        // منطق پایان آزمون (مانند محاسبه نمره نهایی، ذخیره وضعیت آزمون و ...)
//        // ...
//        ExamStudent examStudent = examStudentService.findByStudentIdAndExamId(studentId, examId);
//        examStudent.setExamStudentState(ExamStudentState.FINISHED);
//        examStudentService.changeState(examStudent);
//        int totalStudentMultipleScore = examStudentService.getStudentMultipleScore(examStudent);
//        // اضافه کردن هر دو متغیر examId و studentId به redirectAttributes
//        Exam exam = examService.getExamById(examId);
//        redirectAttributes.addAttribute("examStudent",examStudent);
//        redirectAttributes.addAttribute("totalStudentMultipleScore",totalStudentMultipleScore);
//        redirectAttributes.addAttribute("id", exam.getCourse().getId());
//        redirectAttributes.addAttribute("studentId", studentId);
//
//        // ریدایرکت به صفحه‌ی اصلی آزمون‌ها
//        return "redirect:/studentsController/course/{id}/{studentId}";
//    }
//}
//

}
