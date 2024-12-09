package fi.haagahelia.quizzerapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import fi.haagahelia.quizzerapp.domain.Answer;
import fi.haagahelia.quizzerapp.domain.AnswerOption;
import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.dto.AnswerDTO;
import fi.haagahelia.quizzerapp.dto.QuestionStatsDTO;
import fi.haagahelia.quizzerapp.repositories.AnswerOptionRepository;
import fi.haagahelia.quizzerapp.repositories.AnswerRepository;
import fi.haagahelia.quizzerapp.repositories.QuestionRepository;
import fi.haagahelia.quizzerapp.repositories.QuizRepository;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    @Autowired
    private QuizService quizService;

    public Question findQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));
    }

    public Quiz findQuizById(Long quizId) {
        return quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    public Answer findAnswerById(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(() -> new RuntimeException("Answer not found"));
    }

    public List<Answer> findAnswersByQuizId(Long quizId) {
        return answerRepository.findByQuizId(quizId);
    }

    public void submitAnswer(AnswerDTO answerDTO) {
        // Validate that answerOptionId is not null (handled by @Valid and @NotNull)
        Long answerOptionId = answerDTO.getAnswerOptionId();

        // Fetch the AnswerOption and validate it exists
        AnswerOption answerOption = answerOptionRepository.findById(answerOptionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "AnswerOption not found with ID: " + answerOptionId));

        // Check if the quiz is published
        Question question = answerOption.getQuestion();
        Quiz quiz = question.getQuiz();
        if (!quizService.isQuizPublished(quiz.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Quiz is not published");
        }

        // Save the Answer
        Answer answer = new Answer(answerOption, answerOption.isCorrect());
        answerRepository.save(answer);
    }

    public List<QuestionStatsDTO> getQuizResults(Long quizId) {
        List<Object[]> stats = answerRepository.findAnswerStatsByQuizId(quizId);
        return stats.stream().map(stat -> new QuestionStatsDTO(
                (Long) stat[0], // questionId
                questionRepository.findById((Long) stat[0]).get().getQuestionBody(),
                questionRepository.findById((Long) stat[0]).get().getDifficultyLevel(),
                ((Number) stat[1]).intValue(), // correctAnswers
                ((Number) stat[2]).intValue() // wrongAnswers
        )).collect(Collectors.toList());
    }

}