import { useEffect, useState } from 'react';
import { useParams, Link as RouterLink } from "react-router-dom";
import { getQuizById, getQuizResults } from '../quizapi';
import { Box, Typography, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";

function QuizResults() {
    const { id: quizId } = useParams();
    const [quiz, setQuiz] = useState(null);
    const [results, setResults] = useState([]);


    useEffect(() => {
        // fetch quiz data
        getQuizById(quizId)
            .then((quizData) => {
                setQuiz(quizData); // set quiz data
            })
            .catch((error) => {
                console.error("Error fetching quiz data:", error);
            });

           getQuizResults(quizId)
            .then((data) => setResults(data))
         
                
    }, [quizId]);

    let totalAnswers = 0;
   
        for (let i = 0; i < results.length; i++) {
            totalAnswers += (results[i].correctAnswers + results[i].wrongAnswers);
        }
    

    if (!quiz) {
        return <Typography variant="h6">Loading...</Typography>;
    }

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