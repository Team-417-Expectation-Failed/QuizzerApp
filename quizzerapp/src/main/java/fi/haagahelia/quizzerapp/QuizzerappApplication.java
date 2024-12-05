package fi.haagahelia.quizzerapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.quizzerapp.domain.AnswerOption;
import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.domain.QuizCategory;
import fi.haagahelia.quizzerapp.domain.Review;
import fi.haagahelia.quizzerapp.repositories.AnswerOptionRepository;
import fi.haagahelia.quizzerapp.repositories.QuestionRepository;
import fi.haagahelia.quizzerapp.repositories.QuizCategoryRepository;
import fi.haagahelia.quizzerapp.repositories.QuizRepository;
import fi.haagahelia.quizzerapp.repositories.ReviewRepository;

@SpringBootApplication
public class QuizzerappApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizzerappApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(QuizCategoryRepository quizCategoryRepository, QuizRepository quizRepository,
            QuestionRepository questionRepository, AnswerOptionRepository answerOptionRepository,
            ReviewRepository reviewRepository) {
        return args -> {
            // Create categories
            QuizCategory geography = new QuizCategory("Geography", "Learning capital cities");
            QuizCategory history = new QuizCategory("History", "Learning about the past");
            QuizCategory science = new QuizCategory("Science", "Understanding the world around us");

            quizCategoryRepository.save(geography);
            quizCategoryRepository.save(history);
            quizCategoryRepository.save(science);

            // Create quizzes
            Quiz geoQuiz = new Quiz("Geography Quiz", "Quiz on world capitals", true, geography);
            Quiz historyQuiz = new Quiz("History Quiz", "Quiz about major historical events", true, history);
            Quiz scienceQuiz = new Quiz("Science Quiz", "Basic science quiz", true, science);

            quizRepository.save(geoQuiz);
            quizRepository.save(historyQuiz);
            quizRepository.save(scienceQuiz);

            // Create questions for geoQuiz
            Question geoQ1 = new Question("What is the capital of France?", geoQuiz);
            Question geoQ2 = new Question("Which country is Berlin the capital of?", geoQuiz);

            questionRepository.save(geoQ1);
            questionRepository.save(geoQ2);

            // Create questions for historyQuiz
            Question historyQ1 = new Question("Who was the first president of the USA?", historyQuiz);
            Question historyQ2 = new Question("In which year did World War II end?", historyQuiz);

            questionRepository.save(historyQ1);
            questionRepository.save(historyQ2);

            // Create questions for scienceQuiz
            Question scienceQ1 = new Question("What is the chemical symbol for water?", scienceQuiz);
            Question scienceQ2 = new Question("How many planets are in the Solar System?", scienceQuiz);

            questionRepository.save(scienceQ1);
            questionRepository.save(scienceQ2);

            // Create answer options for geoQuiz
            answerOptionRepository.save(new AnswerOption("Paris", true, geoQ1));
            answerOptionRepository.save(new AnswerOption("London", false, geoQ1));
            answerOptionRepository.save(new AnswerOption("Berlin", true, geoQ2));
            answerOptionRepository.save(new AnswerOption("Madrid", false, geoQ2));

            // Create answer options for historyQuiz
            answerOptionRepository.save(new AnswerOption("George Washington", true, historyQ1));
            answerOptionRepository.save(new AnswerOption("Abraham Lincoln", false, historyQ1));
            answerOptionRepository.save(new AnswerOption("1945", true, historyQ2));
            answerOptionRepository.save(new AnswerOption("1939", false, historyQ2));

            // Create answer options for scienceQuiz
            answerOptionRepository.save(new AnswerOption("H2O", true, scienceQ1));
            answerOptionRepository.save(new AnswerOption("CO2", false, scienceQ1));
            answerOptionRepository.save(new AnswerOption("8", true, scienceQ2));
            answerOptionRepository.save(new AnswerOption("9", false, scienceQ2));

            // Create reviews for quizzes
            Review review = new Review("Jane Doe", 4, "Good quiz!", geoQuiz);
            Review review2 = new Review("John Smith", 3, "Not bad", geoQuiz);
            Review review3 = new Review("Janet Doe", 5, "Great quiz!", historyQuiz);
            Review review4 = new Review("John Doe", 2, "Not good", historyQuiz);
            Review review5 = new Review("Jane Smith", 4, "Good quiz!", scienceQuiz);
            Review review6 = new Review("John Doe", 3, "Not bad", scienceQuiz);
            reviewRepository.save(review);
            reviewRepository.save(review2);
            reviewRepository.save(review3);
            reviewRepository.save(review4);
            reviewRepository.save(review5);
            reviewRepository.save(review6);
        };
    }
}
