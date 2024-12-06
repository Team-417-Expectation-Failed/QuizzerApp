import { Box, Typography, Paper, Button } from "@mui/material";
import { getQuizReviews } from "../quizapi";
import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import { getQuizById } from "../quizapi";
import { Link as RouterLink } from "react-router-dom";


function ReviewList() {
    const { id: quizId } = useParams();
    const [reviews, setReviews] = useState([]);
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

        getQuizReviews(quizId)
            .then((data) => setReviews(data));
    }, [quizId]);

    if (!quiz) { // This has to be here if the fetch is slower than the rendering
        return <Typography variant="h6">Loading...</Typography>;
    }

    return (
        <Box sx={{ margin: 5 }}>
            <Typography variant="h4" sx={{ marginBottom: 2 }}>Reviews of "{quiz.name}"</Typography>
            <Typography variant="h6" sx={{ marginBottom: 2 }}>X rating average based on {reviews.length}</Typography>
            <Typography sx={{ marginBottom: 3 }}>
                <Typography variant="body"><RouterLink to={`#`}>Write you review</RouterLink></Typography>
            </Typography>
            {reviews.map((review) => (
                <Paper key={review.id} sx={{ padding: 3, marginBottom: 3 }}>
                    <Typography variant="h6">{review.nickname}</Typography>
                    <Typography>Rating: {review.rating}/5</Typography>
                    <Typography>{review.reviewText}</Typography>
                    <Typography>Written on: {new Intl.DateTimeFormat('fi-FI').format(new Date(review.reviewDate))}</Typography>
                    <Box sx={{ marginTop: 2 }}>
                        <Button>Delete</Button>
                        <Button>Edit</Button>
                    </Box>
                </Paper>
            ))}
        </Box>
    );
}

export default ReviewList;