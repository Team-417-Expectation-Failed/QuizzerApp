import { useState } from 'react';
import dayjs from 'dayjs';
import {
    Box,
    Button,
    FormControl,
    InputLabel,
    MenuItem,
    Select,
    TextField,
    Typography,
} from '@mui/material';

const ReviewForm = ({ onSubmitReview }) => {
    const [nickname, setNickname] = useState('');
    const [rating, setRating] = useState('');
    const [reviewMessage, setReviewMessage] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();

        if (!nickname || !rating || !reviewMessage) {
            alert("All fields are required.");
            return;
        }

        const datestamp = dayjs().format('DD-MM-YYYY');

        const reviewData = {
            nickname,
            rating,
            reviewMessage,
            datestamp,
        };

        onSubmitReview(reviewData);

        setNickname('');
        setRating('');
        setReviewMessage('');
    };

    return (
        <Box
            component="form"
            onSubmit={handleSubmit}
            sx={{
                display: 'flex',
                flexDirection: 'column',
                gap: 2,
                maxWidth: 400,
                margin: 'auto',
                padding: 2,
                boxShadow: 3,
                borderRadius: 2,
            }}
        >
            <Typography variant="h5" component="h1" textAlign="center">
                Submit Your Review
            </Typography>

            <TextField
                label="Nickname"
                value={nickname}
                onChange={(e) => setNickname(e.target.value)}
                required
                fullWidth
            />

            <FormControl fullWidth required>
                <InputLabel id="rating-label">Rating</InputLabel>
                <Select
                    labelId="rating-label"
                    value={rating}
                    onChange={(e) => setRating(e.target.value)}
                >
                    <MenuItem value={1}>1 - Useless</MenuItem>
                    <MenuItem value={2}>2 - Poor</MenuItem>
                    <MenuItem value={3}>3 - Ok</MenuItem>
                    <MenuItem value={4}>4 - Good</MenuItem>
                    <MenuItem value={5}>5 - Excellent</MenuItem>
                </Select>
            </FormControl>

            <TextField
                label="Review"
                value={reviewMessage}
                onChange={(e) => setReviewMessage(e.target.value)}
                required
                multiline
                rows={4}
                fullWidth
            />

            <Button type="submit" variant="contained" color="primary" fullWidth>
                Submit Your Review
            </Button>
        </Box>
    );
};

export default ReviewForm;
