package ir.maktabsharif.finalproject.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
//@ControllerAdvice
//public class CustomExceptionHandler {
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public String handleResourceNotFoundException(ResourceNotFoundException e, Model model) {
//        model.addAttribute("errorMessage", e.getMessage());
//        return "error-page"; // اسم صفحة Thymeleaf التي تريد عرض الرسالة فيها
//    }
//}
//<!DOCTYPE html>
//<html xmlns:th="http://www.thymeleaf.org">
//<head>
//<title>صفحه خطا</title>
//</head>
//<body>
//<h1>خطا</h1>
//<p th:text="${errorMessage}">پیام خطای پیش‌فرض</p>
//</body>
//</html>