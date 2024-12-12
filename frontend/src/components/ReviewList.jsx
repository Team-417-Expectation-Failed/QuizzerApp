import { Box, Typography, Paper, Button, CircularProgress } from "@mui/material";
import { getQuizReviews, deleteReview as deleteReviewApi, getQuizById } from "../quizapi";
import { useState, useEffect } from "react";
import { useNavigate, useParams, Link as RouterLink } from "react-router-dom";


/**
 * ReviewList component fetches and displays the reviews for a specific quiz.
 * It also allows users to navigate to the edit review page and delete their reviews.
 *
 * @component
 * @example
 * return (
 *   <ReviewList />
 * )
 *
 * @returns {JSX.Element} The rendered component.
 *
 * @description
 * This component uses the `useParams` hook to get the quiz ID from the URL,
 * and the `useState` and `useEffect` hooks to manage state and side effects.
 * It fetches the quiz and its reviews from the server, calculates the average rating,
 * and displays the reviews in a list. Users can delete or edit their reviews.
 *
 * @function
 * @name ReviewList
 */

function ReviewList() {
    const { id: quizId } = useParams();
    const [reviews, setReviews] = useState([]);
    const [quiz, setQuiz] = useState(null);
    const [averageRating, setAverageRating] = useState(0);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const quizData = await getQuizById(quizId);
                setQuiz(quizData);

                const reviewsData = await getQuizReviews(quizId);
                setReviews(reviewsData);

                if (reviewsData.length > 0) {
                    const totalRating = reviewsData.reduce((sum, review) => sum + review.rating, 0);
                    setAverageRating((totalRating / reviewsData.length).toFixed(1));
                } else {
                    setAverageRating(0);
                }

                setLoading(false);
            } catch (error) {
                setError("Failed to load quiz and reviews data.");
                setLoading(false);
            }
        };

        fetchData();
    }, [quizId]);

    const deleteReview = async (id) => {
        try {
            await deleteReviewApi(id);
            const updatedReviews = reviews.filter(review => review.id !== id);
            setReviews(updatedReviews);

            if (updatedReviews.length > 0) {
                const totalRating = updatedReviews.reduce((sum, review) => sum + review.rating, 0);
                setAverageRating((totalRating / updatedReviews.length).toFixed(1));
            } else {
                setAverageRating(0);
            }
        } catch (error) {
            console.error("Error deleting review:", error);
        }
    };

    const editReview = (reviewId) => {
        navigate(`/reviews/${reviewId}/edit`);
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
            <Typography variant="h4" sx={{ marginBottom: 2 }}>Reviews of "{quiz.name}"</Typography>
            <Typography variant="h6" sx={{ marginBottom: 2 }}>
                {averageRating} rating average based on {reviews.length} reviews
            </Typography>
            <Typography sx={{ marginBottom: 3 }}>
                <Typography variant="body"><RouterLink to={`/quiz/${quizId}/review`}>Write your review</RouterLink></Typography>
            </Typography>
            {reviews.map((review) => (
                <Paper key={review.id} sx={{ padding: 3, marginBottom: 3 }}>
                    <Typography variant="h6">{review.nickname}</Typography>
                    <Typography>Rating: {review.rating}/5</Typography>
                    <Typography>{review.reviewText}</Typography>
                    <Typography>Written on: {new Intl.DateTimeFormat('fi-FI').format(new Date(review.reviewDate))}</Typography>
                    <Box sx={{ marginTop: 2 }}>
                        <Button onClick={() => deleteReview(review.id)}>Delete</Button>
                        <Button onClick={() => editReview(review.id)}>Edit</Button>
                    </Box>
                </Paper>
            ))}
        </Box>
    );
}

export default ReviewList;
