import { useState, useEffect } from "react";
import { getPublishedQuizzes } from "../quizapi";
import { Box, Typography, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { Link as RouterLink } from "react-router-dom";

function QuizList() {
  const [quizzes, setQuizzes] = useState([]);

  useEffect(() => {
    getPublishedQuizzes()
      .then((data) => {
        console.log(data); // Checking the data on the console
        setQuizzes(data);
      })
  }, []);

  const formatDate = (dateString) => {
    const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
    return new Intl.DateTimeFormat('fi-FI', options).format(new Date(dateString));
  };

  return (
    <Box sx={{ margin: 5 }}>
      <Typography variant="h4">Quizzes</Typography>
      <TableContainer component={Paper} sx={{ marginTop: 2 }}>
        <Table aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell variant="head" sx={{ fontWeight: 'bold' }}>Name</TableCell>
              <TableCell variant="head" sx={{ fontWeight: 'bold' }}>Description</TableCell>
              <TableCell variant="head" sx={{ fontWeight: 'bold' }}>Category</TableCell>
              <TableCell variant="head" sx={{ fontWeight: 'bold' }}>Added on</TableCell>
              <TableCell></TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {quizzes.map((quiz) => (
              <TableRow key={quiz.id}>
                <TableCell variant="body"><RouterLink to={`/quiz/${quiz.id}/questions`}>{quiz.name}</RouterLink></TableCell>
                <TableCell variant="body">{quiz.description}</TableCell>
                <TableCell variant="body">{quiz.quizCategoryName}</TableCell>
                <TableCell variant="body">{formatDate(quiz.createdDate)}</TableCell>
                <TableCell variant="body"><RouterLink to={`/quiz/${quiz.id}/results`}>See results</RouterLink></TableCell>
                {/* <RouterLink to={`/quiz/${quiz.id}/results`}>See results</RouterLink> Replace the above RouterLink with this when fetching 
                results is ready */}

              </TableRow>
            ))}

          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
}

export default QuizList;