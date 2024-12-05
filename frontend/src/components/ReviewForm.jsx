import { useState, useEffect } from 'react';
import dayjs from 'dayjs';
import { useParams } from "react-router-dom";
import { getQuizById } from '../quizapi';
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
} from '@mui/material';

const ReviewForm = () => {

    const { id: quizId } = useParams();
    const [quiz, setQuiz] = useState(null);
    const [nickname, setNickname] = useState('');
    const [rating, setRating] = useState('');
    const [reviewMessage, setReviewMessage] = useState('');

    useEffect(() => {
        getQuizById(quizId)
            .then((quizData) => {
                setQuiz(quizData);
            })
            .catch((error) => {
                console.error("Error fetching quiz data:", error); 
            });
    }, [quizId]);

    const handleSubmit = (e) => { 
        console.log("Submit");
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

        console.log(reviewData);

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

            <Button type="submit" variant="contained" color="primary" fullWidth>
                Submit Your Review
            </Button>
        </Box>
    );
};

export default ReviewForm;
