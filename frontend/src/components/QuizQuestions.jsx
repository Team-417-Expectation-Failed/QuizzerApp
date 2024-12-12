import { useEffect, useState } from 'react';
import { useParams } from "react-router-dom";
import { getQuizQuestions } from '../quizapi';
import { getQuizById } from '../quizapi';
import { Box, Typography, Paper } from "@mui/material";
import QuizAnswerOptions from './QuizAnswerOptions';

function FetchQuizQuestions() {
    const { id: quizId } = useParams();
    const [questions, setQuestions] = useState([]);
    const [quiz, setQuiz] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');


    useEffect(() => {
        const fetchQuizData = async () => {
            try {
                const quizData = await getQuizById(quizId);
                setQuiz(quizData);

                const questionsData = await getQuizQuestions(quizId);
                setQuestions(questionsData);

                setLoading(false);
            } catch (error) {
                setError("Failed to load quiz data.");
                setLoading(false);
            }
        };

        fetchQuizData();
    }, [quizId]);

    if (!quiz) { // This has to be here if the fetch is slower than the rendering
        return <Typography variant="h6">Loading...</Typography>;
    }

    const formatDifficulty = (str) => {
        return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
    };

    return (
        <Box sx={{ margin: 5 }}>
            <Typography variant="h4" sx={{ marginBottom: 2 }}>{quiz.name}</Typography>
            <Typography variant="h6" sx={{ marginBottom: 2 }}>{quiz.description}</Typography>
            <Typography sx={{ marginBottom: 3 }}>
                Added on: {new Intl.DateTimeFormat('fi-FI').format(new Date(quiz.createdDate))} -
                Questions: {questions.length} -
                Category: {quiz.quizCategoryName}
            </Typography>
            {questions.map((question, index) => (
                <Paper key={question.id} sx={{ padding: 3, marginBottom: 3 }}>
                    <Typography variant="h6">{question.questionBody}</Typography>
                    <Typography variant="subtitle1">Question {index + 1} of {questions.length} - Difficulty: {formatDifficulty(question.difficultyLevel)}</Typography>
                    <QuizAnswerOptions answerOptions={question.answerOptions} />
                </Paper>
            ))}
        </Box>
    );
}

export default FetchQuizQuestions;