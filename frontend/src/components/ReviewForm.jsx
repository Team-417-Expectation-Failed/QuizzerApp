import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getQuizById, createReview } from "../quizapi";
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

const ReviewForm = () => {
  const { id } = useParams(); // Get the quizId from the URL
  const navigate = useNavigate();
  
  const [quiz, setQuiz] = useState(null);
  const [nickname, setNickname] = useState('');
  const [rating, setRating] = useState('');
  const [reviewMessage, setReviewMessage] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    // Fetch the quiz data using the quiz ID from the URL
    getQuizById(id)
      .then((data) => {
        setQuiz(data);
        setLoading(false);
      })
      .catch((err) => {
        setError("Failed to load quiz data.");
        setLoading(false);
      });
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!nickname || !rating || !reviewMessage) {
      setError("All fields are required.");
      return;
    }

    try {
      
      const newReview = {
        nickname,
        rating: parseInt(rating),
        reviewText: reviewMessage,
        quizId: id,
      };

      await createReview(newReview); // API call to create the review

      // Navigate to the reviews page for this quiz after submission
      navigate(`/quiz/${id}/reviews`);
    } catch (err) {
      setError("Failed to submit review. Please try again.");
    }
  };

  if (loading) {
    return <Typography>Loading quiz data...</Typography>;
  }

  if (error) {
    return <Typography color="error">{error}</Typography>;
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
        Add a review for &quot;{quiz ? quiz.name : 'Loading...'}&quot;
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
        Submit Your Review
      </Button>
    </Box>
  );
};

export default ReviewForm;
