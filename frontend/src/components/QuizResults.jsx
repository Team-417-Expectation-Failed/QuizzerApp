import React, { useEffect, useState } from 'react';
import { useParams } from "react-router-dom";
import { getQuizById } from '../quizapi';
import { Box, Typography, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";

function QuizResults() {
    const { id: quizId } = useParams();
    const [quiz, setQuiz] = useState(null);

    useEffect(() => {

        // fetch quiz data
        getQuizById(quizId)
            .then((quizData) => {
                setQuiz(quizData); // set quiz data
            })
            .catch((error) => {
                console.error("Error fetching quiz data:", error);
            });

        //     getQuizResults(quizId)
        //         .then((data) => setResults(data));
    }, [quizId]);


    if (!quiz) {
        return <Typography variant="h6">Loading...</Typography>;
    }

    return (
        <Box sx={{ margin: 5 }}>
            <Typography variant="h4">Results of "{quiz.name}"</Typography>
            <Typography variant="h6" sx={{ marginTop: 2 }}>x answers to {quiz.questions.length} questions</Typography>
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
                    {/* <TableBody>
                        {results.map((result) => (
                            <TableRow key={result.id}>
                                <TableCell variant="body"><RouterLink to={`#`}></RouterLink></TableCell>
                                <TableCell variant="body"><RouterLink to={`#`}></RouterLink></TableCell>
                                <TableCell variant="body"><RouterLink to={`#`}></RouterLink></TableCell>
                                <TableCell variant="body"><RouterLink to={`#`}></RouterLink></TableCell>
                                <TableCell variant="body"><RouterLink to={`#`}></RouterLink></TableCell>
                                <TableCell variant="body"><RouterLink to={`#`}></RouterLink></TableCell>
                            </TableRow>
                        ))}

                    </TableBody> */}
                </Table>
            </TableContainer>
        </Box>
    );
}

export default QuizResults;