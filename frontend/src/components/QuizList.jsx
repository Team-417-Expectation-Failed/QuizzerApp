import { useState, useEffect } from "react";
import { getPublishedQuizzes } from "../quizapi";
import { Box, Typography, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, CircularProgress } from "@mui/material";
import { Link as RouterLink } from "react-router-dom";

function QuizList() {
  const [quizzes, setQuizzes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    getPublishedQuizzes()
      .then((data) => {
        setQuizzes(data);
        setLoading(false);
      })
      .catch((error) => {
        setError("Error fetching published quizzes.");
        setLoading(false);
      });
  }, []);

  const formatDate = (dateString) => {
    const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
    return new Intl.DateTimeFormat('fi-FI', options).format(new Date(dateString));
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
                <TableCell variant="body">{quiz.quizCategoryName ? quiz.quizCategoryName : "Uncategorized"}</TableCell>
                <TableCell variant="body">{formatDate(quiz.createdDate)}</TableCell>
                <TableCell variant="body"><RouterLink to={`/quiz/${quiz.id}/results`}>See results</RouterLink></TableCell>
                <TableCell variant="body"><RouterLink to={`quiz/${quiz.id}/reviews`}>See reviews</RouterLink></TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
}

export default QuizList;