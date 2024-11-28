import { FormControl, Radio, RadioGroup, FormControlLabel } from '@mui/material';

function QuizAnswerOptions({answerOptions}) {

    const handleSubmit = (event) => {
        event.preventDefault();
    }

    console.log(answerOptions);

    return (
    <form onSubmit={handleSubmit}>
        <FormControl>
            {answerOptions.map((answerOption) => (
                <RadioGroup
                key={answerOption.id}
            >
                <FormControlLabel  value={answerOption.id} control={<Radio />} label={answerOption.answerOptionBody} />
            </RadioGroup>
            ))}
        </FormControl>
    </form>
    )
}

export default QuizAnswerOptions;