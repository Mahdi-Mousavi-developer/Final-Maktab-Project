package ir.maktabsharif.finalproject.controller.teacherControl;

import ir.maktabsharif.finalproject.entity.*;
import ir.maktabsharif.finalproject.exception.EntityNotFoundException;
import ir.maktabsharif.finalproject.service.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {
    private final MultipleChoiceService multipleChoiceService;
    private final DescriptiveQuestionService descriptiveQuestionService;
    private final ExamService examService;
    private final QuestionService questionService;

    public QuestionController(MultipleChoiceService multipleChoiceService, DescriptiveQuestionService descriptiveQuestionService, ExamService examService, QuestionService questionService) {
        this.multipleChoiceService = multipleChoiceService;
        this.descriptiveQuestionService = descriptiveQuestionService;
        this.examService = examService;
        this.questionService = questionService;

    }

    @GetMapping("/{id}")
    public String ShowQuestion(@PathVariable Long id, Model model) throws EntityNotFoundException {
        Exam exam = examService.getExamById(id);
        model.addAttribute("exam", exam);
        model.addAttribute("questions", exam.getQuestions());
        return "/teacher/questionPage";
    }

    @GetMapping("/createDescrptive/{id}")
    public String ShowCreateDescrptiveQuestionPage(@PathVariable Long id, Model model) throws EntityNotFoundException {
        Exam exam = examService.getExamById(id);
        DescriptiveQuestion descriptiveQuestion = new DescriptiveQuestion();
        model.addAttribute("descriptiveQuestion", descriptiveQuestion);
        model.addAttribute("exam", exam);
        return "/teacher/createDescrptiveQuestionPage";
    }

    @PostMapping("/createDescriptive/{id}")
    public String createDescriptiveQuestion(
            @ModelAttribute DescriptiveQuestion descriptiveQuestion,
            @PathVariable Long id
    ) throws EntityNotFoundException {
        Exam exam = examService.getExamById(id);
        descriptiveQuestion.setExam(exam);
        descriptiveQuestion.setCourse(exam.getCourse());
        descriptiveQuestion.setTeacher(exam.getTeacher());
        descriptiveQuestionService.save(descriptiveQuestion);
        return "redirect:/question/" + id;
    }

    @GetMapping("/createMultiple/{id}")
    public String showMultipleChoiceQuestionPage(@PathVariable Long id, Model model) throws EntityNotFoundException {
        Exam exam = examService.getExamById(id);
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
        model.addAttribute("exam", exam);
        model.addAttribute("multipleChoiceQuestion", multipleChoiceQuestion);
        return "/teacher/createMultipleChoiceQuestionPage";
    }

    @PostMapping("/saveMultipleChoiceQuestion/{id}")
    public String saveMultipleChoiceQuestion(
            @ModelAttribute("multipleChoiceQuestion") MultipleChoiceQuestion multipleChoiceQuestion,
            @RequestParam("choices") List<String> choices,
            @PathVariable Long id,
            Model model) throws EntityNotFoundException {

        Exam exam = examService.getExamById(id);

        multipleChoiceQuestion.setExam(exam);
        multipleChoiceQuestion.setTeacher(exam.getTeacher());
        multipleChoiceQuestion.setCourse(exam.getCourse());

        multipleChoiceQuestion.setChoices(choices);

        multipleChoiceService.Save(multipleChoiceQuestion);

        return "redirect:/question/" + id;
    }

    @GetMapping("/question-bank/{id}")
    public String ShowQuestionBankPage(@PathVariable Long id, Model model) throws EntityNotFoundException {
        Exam exam = examService.getExamById(id);
        model.addAttribute("exam", exam);
        model.addAttribute("questions", questionService.findByCourseAndTeacherId(exam.getCourse().getId(), exam.getTeacher().getId(), exam.getId()));
        return "/teacher/questionBank";
    }

    @PostMapping("/addToExam/{examId}/{questionId}")
    @Transactional
    public String addQuestionToExam(
            @PathVariable Long examId,
            @PathVariable Long questionId,
            @RequestParam("score") int score) throws EntityNotFoundException { // دریافت score از کاربر به صورت int
        Exam exam = examService.getExamById(examId);
        Question question = questionService.findById(questionId);

        if (question.getQuestionType().equals(QuestionType.DESCRIPTIVE)) {
            DescriptiveQuestion descriptiveQuestion = descriptiveQuestionService.findById(questionId);
            DescriptiveQuestion copy = descriptiveQuestion.copy(); // ایجاد کپی
            copy.setExam(exam); // تنظیم exam جدید
            copy.setQuestionScore(score); // تنظیم score جدید
            descriptiveQuestionService.save(copy); // ذخیره کپی
        } else {
            MultipleChoiceQuestion multipleChoiceQuestion = multipleChoiceService.findById(questionId);
            MultipleChoiceQuestion copy = multipleChoiceQuestion.copy(); // ایجاد کپی
            copy.setExam(exam); // تنظیم exam جدید
            copy.setQuestionScore(score); // تنظیم score جدید
            multipleChoiceService.Save(copy); // ذخیره کپی
        }

        return "redirect:/question/" + examId;
    }

    @PostMapping("/delete")
    public String deleteQuestion(@RequestParam("id") Long id) {
        Question question = questionService.findById(id);
        Long examId = question.getExam().getId();
        questionService.deleteById(id);
        return "redirect:/question/" + examId;
    }


    @GetMapping("/edit/{id}")
    public String ShowEditePage(@PathVariable Long id, Model model) {
        Question question = questionService.findById(id);

        if (question.getQuestionType().equals(QuestionType.DESCRIPTIVE)) {
            model.addAttribute("question", question);
            return "/teacher/editDescriptivePage";
        }
        MultipleChoiceQuestion question1 = multipleChoiceService.findById(id);
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
        model.addAttribute("question1", question1);
        model.addAttribute("multipleChoiceQuestion", multipleChoiceQuestion);

        return "/teacher/editeMultiplePage";
    }


    @PostMapping("/update-multiple/{id}")
    public String updateMultipleChoiceQuestion(
            @PathVariable Long id,
            @RequestParam("choices") List<String> choices,
            @ModelAttribute("question") MultipleChoiceQuestion updatedQuestion) {
        MultipleChoiceQuestion question = multipleChoiceService.findById(id);
        updatedQuestion.setChoices(choices);
        multipleChoiceService.update(id, updatedQuestion);
        return "redirect:/question/" + question.getExam().getId();

    }

    @PostMapping("/update-descriptive/{id}")
    public String updateDescriptiveQuestion(@PathVariable Long id, @ModelAttribute("question") DescriptiveQuestion updatedQuestion) {
        Question question = questionService.findById(id);
        if (question instanceof DescriptiveQuestion) {
            questionService.updateQuestion(id, updatedQuestion);
            return "redirect:/question/" + question.getExam().getId();
        }
        throw new IllegalArgumentException("Invalid question type");
    }
    

}

