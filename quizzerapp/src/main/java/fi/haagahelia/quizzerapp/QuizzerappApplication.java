package fi.haagahelia.quizzerapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.quizzerapp.domain.AnswerOption;
import fi.haagahelia.quizzerapp.domain.AnswerOptionRepository;
import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.domain.QuestionRepository;
import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.domain.QuizRepository;

@SpringBootApplication
public class QuizzerappApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizzerappApplication.class, args);
	}

	@Bean
    public CommandLineRunner loadData(QuizRepository quizRepository, QuestionRepository questionRepository, AnswerOptionRepository answerOptionRepository) {
        return args -> {
            // Create an example quiz
            Quiz quiz = new Quiz();
            quiz.setName("Example Quiz");
            quiz.setDescription("This is an example quiz.");

            // Create example questions
            Question question1 = new Question();
            question1.setQuestionBody("What is the capital of France?");
            question1.setQuiz(quiz);

            Question question2 = new Question();
            question2.setQuestionBody("What is 2 + 2?");
            question2.setQuiz(quiz);

            // Add questions to the quiz
            quiz.getQuestions().add(question1);
            quiz.getQuestions().add(question2);

            // Save the quiz (cascades to save questions)
            quizRepository.save(quiz);

            // Create example answer options for question1
            AnswerOption answerOption1 = new AnswerOption();
            answerOption1.setAnswerOptionBody("Paris");
            answerOption1.setCorrect(true);
            answerOption1.setQuestion(question1);

            AnswerOption answerOption2 = new AnswerOption();
            answerOption2.setAnswerOptionBody("London");
            answerOption2.setCorrect(false);
            answerOption2.setQuestion(question1);

            // Create example answer options for question2
            AnswerOption answerOption3 = new AnswerOption();
            answerOption3.setAnswerOptionBody("4");
            answerOption3.setCorrect(true);
            answerOption3.setQuestion(question2);

            AnswerOption answerOption4 = new AnswerOption();
            answerOption4.setAnswerOptionBody("5");
            answerOption4.setCorrect(false);
            answerOption4.setQuestion(question2);

            // Save answer options
            answerOptionRepository.save(answerOption1);
            answerOptionRepository.save(answerOption2);
            answerOptionRepository.save(answerOption3);
            answerOptionRepository.save(answerOption4);
        };
	}
}
