import { useEffect, useState } from 'react';
import { useParams } from "react-router-dom";
import { getCategoryById, getQuizzesByCategory } from '../quizapi';
import { Box, Typography, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { Link as RouterLink } from "react-router-dom";

function QuestionsByCategory() {
    const { id: categoryId } = useParams();
    const [quizzes, setQuizzes] = useState([]);
    const [category, setCategory] = useState(null);

    useEffect(() => {

        // fetch category data
        getCategoryById(categoryId)
            .then((data) => {
                setCategory(data);
            })
            .catch((error) => {
                console.error("Error fetching category data:", error);
            });
        getQuizzesByCategory(categoryId)
            .then((data) => setQuizzes(data));
    }, [categoryId]);
    if (!category) {
        return <Typography variant="h6">Loading...</Typography>;
    }

    const formatDate = (dateString) => {
        const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
        return new Intl.DateTimeFormat('fi-FI', options).format(new Date(dateString));
    };

    return (
        <Box sx={{ margin: 5 }}>
            <Typography variant="h4">{category.name}</Typography>
            <TableContainer component={Paper} sx={{ marginTop: 2 }}>
                <Table aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell variant="head" sx={{ fontWeight: 'bold' }}>Name</TableCell>
                            <TableCell variant="head" sx={{ fontWeight: 'bold' }}>Description</TableCell>
                            <TableCell variant="head" sx={{ fontWeight: 'bold' }}>Added on</TableCell>
                            <TableCell></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {quizzes.map((quiz) => (
                            <TableRow key={quiz.id}>
                                <TableCell variant="body"><RouterLink to={`/quiz/${quiz.id}/questions`}>{quiz.name}</RouterLink></TableCell>
                                <TableCell variant="body">{quiz.description}</TableCell>
                                <TableCell variant="body">{formatDate(quiz.createdDate)}</TableCell>
                                <TableCell variant="body"><RouterLink to={`/quiz/${quiz.id}/results`}>See results</RouterLink></TableCell>
                            </TableRow>
                        ))}

                    </TableBody>
                </Table>
            </TableContainer>
        </Box>
    );
}

export default QuestionsByCategory;