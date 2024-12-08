import { Box, Typography, Paper, Button } from "@mui/material";
import { getQuizReviews, deleteReview as deleteReviewApi, getQuizById } from "../quizapi";
import { useState, useEffect } from "react";
import { useParams, Link as RouterLink } from "react-router-dom";

function ReviewList() {
    const { id: quizId } = useParams();
    const [reviews, setReviews] = useState([]);
    const [quiz, setQuiz] = useState(null);
    const [averageRating, setAverageRating] = useState(0); // State for average rating

    useEffect(() => {
        // fetch quiz data
        getQuizById(quizId)
            .then((quizData) => {
                setQuiz(quizData); // set quiz data
            })
            .catch((error) => {
                console.error("Error fetching quiz data:", error);
            });

        // fetch reviews and calculate average rating
        getQuizReviews(quizId)
            .then((data) => {
                setReviews(data);
                // Calculate average rating
                if (data.length > 0) {
                    const totalRating = data.reduce((sum, review) => sum + review.rating, 0);
                    setAverageRating((totalRating / data.length).toFixed(1)); // Round to 1 decimal
                } else {
                    setAverageRating(0);
                }
            })
            .catch((error) => {
                console.error("Error fetching reviews:", error);
            });
    }, [quizId]);

    if (!quiz) { // This has to be here if the fetch is slower than the rendering
        return <Typography variant="h6">Loading...</Typography>;
    }

    const deleteReview = (id) => {
        deleteReviewApi(id)
            .then(() => {
                const updatedReviews = reviews.filter(review => review.id !== id);
                setReviews(updatedReviews);

                // Recalculate average rating
                if (updatedReviews.length > 0) {
                    const totalRating = updatedReviews.reduce((sum, review) => sum + review.rating, 0);
                    setAverageRating((totalRating / updatedReviews.length).toFixed(1)); // Round to 1 decimal
                } else {
                    setAverageRating(0);
                }
            })
            .catch((error) => {
                console.error("Error deleting review:", error);
            });
    };

    return (
        <Box sx={{ margin: 5 }}>
            <Typography variant="h4" sx={{ marginBottom: 2 }}>Reviews of "{quiz.name}"</Typography>
            <Typography variant="h6" sx={{ marginBottom: 2 }}>
                {averageRating} rating average based on {reviews.length} reviews
            </Typography>
            <Typography sx={{ marginBottom: 3 }}>
                <Typography variant="body"><RouterLink to={"/quiz/:id/review"}>Write your review</RouterLink></Typography>
            </Typography>
            {reviews.map((review) => (
                <Paper key={review.id} sx={{ padding: 3, marginBottom: 3 }}>
                    <Typography variant="h6">{review.nickname}</Typography>
                    <Typography>Rating: {review.rating}/5</Typography>
                    <Typography>{review.reviewText}</Typography>
                    <Typography>Written on: {new Intl.DateTimeFormat('fi-FI').format(new Date(review.reviewDate))}</Typography>
                    <Box sx={{ marginTop: 2 }}>
                        <Button onClick={() => deleteReview(review.id)}>Delete</Button>
                        <Button>Edit</Button>
                    </Box>
                </Paper>
            ))}
        </Box>
    );
}

export default ReviewList;
