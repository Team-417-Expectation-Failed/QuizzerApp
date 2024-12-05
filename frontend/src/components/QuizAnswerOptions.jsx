import { useState } from 'react';
import { FormControl, Radio, RadioGroup, FormControlLabel, Snackbar, Button } from '@mui/material';
import { addQuizAnswer } from '../quizapi';

function QuizAnswerOptions({questionId, answerOptions, quizId}) {
    const [value, setValue] = useState(0);
    const [open, setOpen] = useState(false);
    const [message, setMessage] = useState('');
    const [disabled, setDisabled] = useState(true);
    const [answerObject, setAnswerObject] = useState({
        questionId: questionId,
        answerOptionId: value,
        quizId: parseInt(quizId, 10),
        correct: false,
    });

    const handleRadioChange = (event) => {
        setValue(event.target.value);
        setDisabled(false);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        setDisabled(true);
        if (value === '') {
            console.log("No value selected");
        }
        const selectedOption = answerOptions.find(option => option.id === parseInt(value, 10)).correct;
        if (selectedOption) {
            setMessage("That is correct, good job!");
        } else {
            setMessage("That is not correct, try again!");
        }
        setOpen(true);
        setAnswerObject({...answerObject, answerOptionId: parseInt(value, 10), correct: selectedOption});
        addQuizAnswer(answerObject)
            .then((data) => {
                console.log(data);
            })
            .catch((error) => {
                console.error("Error adding answer:", error);
            });
    };

    const handleClose = () => {
        setOpen(false);
    };


    return (
        <>
            <form onSubmit={handleSubmit}>
                <FormControl variant='standard'>
                    <RadioGroup disabled={false} value={value} onChange={handleRadioChange}>
                        {answerOptions.map((answerOption) => (
                            <FormControlLabel
                                key={answerOption.id}
                                value={answerOption.id}
                                control={<Radio />}
                                label={answerOption.answerOptionBody}
                            />
                        ))}
                    </RadioGroup>
                    <Button disabled={disabled} type="submit" variant="contained" color="primary">
                        Submit
                    </Button>
                </FormControl>
            </form>
            <Snackbar
                open={open}
                message={message}
                autoHideDuration={3000}
                onClose={handleClose}
            />
        </>
    )
}

export default QuizAnswerOptions;