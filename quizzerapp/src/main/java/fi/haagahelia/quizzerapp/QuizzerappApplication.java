package fi.haagahelia.quizzerapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.quizzerapp.domain.Answer;
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
import fi.haagahelia.quizzerapp.repositories.AnswerRepository;

@SpringBootApplication
public class QuizzerappApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizzerappApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(
            QuizCategoryRepository quizCategoryRepository,
            QuizRepository quizRepository,
            QuestionRepository questionRepository,
            AnswerOptionRepository answerOptionRepository,
            ReviewRepository reviewRepository,
            AnswerRepository answerRepository) {
        return args -> {
            // Helper to create and save an Answer
            java.util.function.BiConsumer<AnswerOption, Boolean> createAndSaveAnswer = (option, isCorrect) -> {
                Answer answer = new Answer(option, isCorrect);
                answerRepository.save(answer);
            };

            // Create categories
            QuizCategory geography = new QuizCategory("Geography", "Learning capital cities");
            QuizCategory history = new QuizCategory("History", "Learning about the past");
            QuizCategory science = new QuizCategory("Science", "Understanding the world around us");

            quizCategoryRepository.saveAll(java.util.List.of(geography, history, science));

            // Create quizzes
            Quiz geoQuiz = new Quiz("Geography Quiz", "Quiz on world capitals", true, geography);
            Quiz historyQuiz = new Quiz("History Quiz", "Quiz about major historical events", true, history);
            Quiz scienceQuiz = new Quiz("Science Quiz", "Basic science quiz", true, science);

            quizRepository.saveAll(java.util.List.of(geoQuiz, historyQuiz, scienceQuiz));

            // Create and save questions
            Question geoQ1 = new Question("What is the capital of France?", geoQuiz);
            Question geoQ2 = new Question("Which country is Berlin the capital of?", geoQuiz);
            Question historyQ1 = new Question("Who was the first president of the USA?", historyQuiz);
            Question historyQ2 = new Question("In which year did World War II end?", historyQuiz);
            Question scienceQ1 = new Question("What is the chemical symbol for water?", scienceQuiz);
            Question scienceQ2 = new Question("How many planets are in the Solar System?", scienceQuiz);

            questionRepository.saveAll(java.util.List.of(geoQ1, geoQ2, historyQ1, historyQ2, scienceQ1, scienceQ2));

            // Create and save answer options
            AnswerOption geoA1 = new AnswerOption("Paris", true, geoQ1);
            AnswerOption geoA2 = new AnswerOption("London", false, geoQ1);
            AnswerOption geoA3 = new AnswerOption("Berlin", true, geoQ2);
            AnswerOption geoA4 = new AnswerOption("Madrid", false, geoQ2);

            AnswerOption histA1 = new AnswerOption("George Washington", true, historyQ1);
            AnswerOption histA2 = new AnswerOption("Abraham Lincoln", false, historyQ1);
            AnswerOption histA3 = new AnswerOption("1945", true, historyQ2);
            AnswerOption histA4 = new AnswerOption("1939", false, historyQ2);

            AnswerOption sciA1 = new AnswerOption("H2O", true, scienceQ1);
            AnswerOption sciA2 = new AnswerOption("CO2", false, scienceQ1);
            AnswerOption sciA3 = new AnswerOption("8", true, scienceQ2);
            AnswerOption sciA4 = new AnswerOption("9", false, scienceQ2);

            answerOptionRepository.saveAll(java.util.List.of(
                    geoA1, geoA2, geoA3, geoA4, histA1, histA2, histA3, histA4, sciA1, sciA2, sciA3, sciA4));

            // Save answers using the helper
            createAndSaveAnswer.accept(geoA1, true);
            createAndSaveAnswer.accept(geoA2, false);
            createAndSaveAnswer.accept(geoA3, true);
            createAndSaveAnswer.accept(geoA4, false);
            createAndSaveAnswer.accept(histA1, true);
            createAndSaveAnswer.accept(histA2, false);
            createAndSaveAnswer.accept(histA3, true);
            createAndSaveAnswer.accept(histA4, false);
            createAndSaveAnswer.accept(sciA1, true);
            createAndSaveAnswer.accept(sciA2, false);
            createAndSaveAnswer.accept(sciA3, true);
            createAndSaveAnswer.accept(sciA4, false);

            // Create and save reviews
            reviewRepository.saveAll(java.util.List.of(
                    new Review("Jane Doe", 4, "Good quiz!", geoQuiz),
                    new Review("John Smith", 3, "Not bad", geoQuiz),
                    new Review("Janet Doe", 5, "Great quiz!", historyQuiz),
                    new Review("John Doe", 2, "Not good", historyQuiz),
                    new Review("Jane Smith", 4, "Good quiz!", scienceQuiz),
                    new Review("John Doe", 3, "Not bad", scienceQuiz)));
        };
    }
}
