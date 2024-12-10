import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getReviewById, updateReview } from "../quizapi";
import {
  Box,
  Button,
  FormControl,
  RadioGroup,
  FormLabel,
  FormControlLabel,
  TextField,
  Typography,
  Radio,
} from "@mui/material";

const ReviewEdit = () => {
  const { reviewId } = useParams();
  const navigate = useNavigate();

  const [review, setReview] = useState(null);
  const [nickname, setNickname] = useState('');
  const [rating, setRating] = useState('');
  const [reviewMessage, setReviewMessage] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    // Fetch the review data using the reviewId
    getReviewById(reviewId)
      .then((data) => {
        setReview(data);
        setNickname(data.nickname);
        setRating(data.rating.toString());
        setReviewMessage(data.reviewText);
        setLoading(false);
      })
      .catch((err) => {
        setError("Failed to load review data.");
        setLoading(false);
      });
  }, [reviewId]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!nickname || !rating || !reviewMessage) {
      setError("All fields are required.");
      return;
    }

    try {
      const updatedReview = {
        id: review.id, // Keep the original review ID
        nickname,
        rating: parseInt(rating),
        reviewText: reviewMessage,
        quizId: review.quiz ? review.quiz.id : null, // Retain the original quizId if available
      };

      // Update the review in the backend
      await updateReview(updatedReview);

      // Navigate to the reviews page for the quiz
      navigate(`/quiz/${review.quiz.id}/reviews`);
    } catch (err) {
      setError("Failed to update review. Please try again.");
    }
  };

  if (loading) {
    return <Typography>Loading review data...</Typography>;
  }

  if (error) {
    return <Typography color="error">{error}</Typography>;
  }

  if (!review.quiz) {
    return <Typography color="error">Quiz data is missing for this review.</Typography>;
  }

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      sx={{
        display: 'flex',
        flexDirection: 'column',
        gap: 2,
        maxWidth: 1200,
        margin: 'auto',
        padding: 2,
        boxShadow: 3,
        borderRadius: 2,
      }}
    >
      <Typography variant="h5" component="h1">
        Edit your review for &quot;{review.quiz.name}&quot;
      </Typography>

      <TextField
        label="Nickname"
        value={nickname}
        onChange={(e) => setNickname(e.target.value)}
        required
      />

      <FormControl fullWidth required>
        <FormLabel id="rating-radio-buttons-group-label">Rating</FormLabel>
        <RadioGroup
          aria-labelledby="rating-radio-buttons-group-label"
          value={rating}
          onChange={(e) => setRating(e.target.value)}
          name="radio-buttons-group"
        >
          <FormControlLabel value="1" control={<Radio />} label="1 - Useless" />
          <FormControlLabel value="2" control={<Radio />} label="2 - Poor" />
          <FormControlLabel value="3" control={<Radio />} label="3 - Ok" />
          <FormControlLabel value="4" control={<Radio />} label="4 - Good" />
          <FormControlLabel value="5" control={<Radio />} label="5 - Excellent" />
        </RadioGroup>
      </FormControl>

      <TextField
        label="Review"
        value={reviewMessage}
        onChange={(e) => setReviewMessage(e.target.value)}
        required
        multiline
        fullWidth
      />

      {error && <Typography color="error">{error}</Typography>}

      <Button type="submit" variant="contained" color="primary" fullWidth>
        Update Your Review
      </Button>
    </Box>
  );
};

export default ReviewEdit;