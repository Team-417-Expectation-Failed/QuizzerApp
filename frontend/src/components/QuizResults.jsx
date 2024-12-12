import { useEffect, useState } from 'react';
import { useParams, Link as RouterLink } from "react-router-dom";
import { getQuizById, getQuizResults } from '../quizapi';
import { Box, Typography, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, CircularProgress } from "@mui/material";

function QuizResults() {
    const { id: quizId } = useParams();
    const [quiz, setQuiz] = useState(null);
    const [results, setResults] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchQuizData = async () => {
            try {
                const quizData = await getQuizById(quizId);
                setQuiz(quizData);

                const resultsData = await getQuizResults(quizId);
                setResults(resultsData);

                setLoading(false);
            } catch (error) {
                setError("Failed to load quiz result data.");
                setLoading(false);
            }
        };

        fetchQuizData();
    }, [quizId]);

    const calculateTotalAnswers = () => {
        return results.reduce((total, result) => total + result.correctAnswers + result.wrongAnswers, 0);
    };

    if (loading) {
        return (
            <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
                <CircularProgress />
            </Box>
        );
    }

    if (error) {
        return <Typography color="error">{error}</Typography>;
    }

    const totalAnswers = calculateTotalAnswers();

    return (
        <Box sx={{ margin: 5 }}>
            <Typography variant="h4">Results of "{quiz.name}"</Typography>
            <Typography variant="h6" sx={{ marginTop: 2 }}>{totalAnswers} answers to {quiz.questions.length} questions</Typography>
            {/* Create logic to display the number of answers */}
            <TableContainer component={Paper} sx={{ marginTop: 2 }}>
                <Table aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell variant="head" sx={{ fontWeight: 'bold' }}>Question</TableCell>
                            <TableCell variant="head" sx={{ fontWeight: 'bold' }}>Difficulty</TableCell>
                            <TableCell variant="head" sx={{ fontWeight: 'bold' }}>Total answers</TableCell>
                            <TableCell variant="head" sx={{ fontWeight: 'bold' }}>Correct answer %</TableCell>
                            <TableCell variant="head" sx={{ fontWeight: 'bold' }}>Correct answers</TableCell>
                            <TableCell variant="head" sx={{ fontWeight: 'bold' }}>Wrong answers</TableCell>
                        </TableRow>
                    </TableHead>
                    {<TableBody>
                        {results.map((result) => (
                            <TableRow key={result.questionId}>
                                <TableCell variant="body"><RouterLink to={`#`}></RouterLink>{result.questionText}</TableCell>
                                <TableCell variant="body"><RouterLink to={`#`}></RouterLink>{result.difficultyLevel}</TableCell>
                                <TableCell variant="body"><RouterLink to={`#`}></RouterLink>{result.correctAnswers + result.wrongAnswers}</TableCell>
                                <TableCell variant="body"><RouterLink to={`#`}></RouterLink>{Math.round((result.correctAnswers / (result.correctAnswers + result.wrongAnswers)) * 100) + "%"}</TableCell>
                                <TableCell variant="body"><RouterLink to={`#`}></RouterLink>{result.correctAnswers}</TableCell>
                                <TableCell variant="body"><RouterLink to={`#`}></RouterLink>{result.wrongAnswers}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody> }
                </Table>
            </TableContainer>
        </Box>
    );
}

export default QuizResults;