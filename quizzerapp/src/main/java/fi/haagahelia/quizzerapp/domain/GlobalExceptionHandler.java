// package fi.haagahelia.quizzerapp.domain;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.context.request.WebRequest;

// @ControllerAdvice
// public class GlobalExceptionHandler {

// @ExceptionHandler(IllegalArgumentException.class)
// public ResponseEntity<?>
// handleIllegalArgumentException(IllegalArgumentException ex, WebRequest
// request) {
// return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not
// found");
// }

// @ExceptionHandler(Exception.class)
// public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest
// request) {
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An
// unexpected error occurred");
// }
// }
