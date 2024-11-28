import { useState } from 'react';
import { FormControl, Radio, RadioGroup, FormControlLabel } from '@mui/material';

function QuizAnswerOptions({answerOptions}) {
    const [value, setValue] = useState(''); 

    const handleRadioChange = (event) => {
        setValue(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
    };

    console.log(answerOptions);

    return (
    <form onSubmit={handleSubmit}>
        <FormControl variant='standard'>
                <RadioGroup value={value} onChange={handleRadioChange}>
                    {answerOptions.map((answerOption) => (
                        <FormControlLabel
                            key={answerOption.id}
                            value={answerOption.id}
                            control={<Radio />}
                            label={answerOption.answerOptionBody}
                        />
                    ))}
                </RadioGroup>
            </FormControl>
    </form>
    )
}

export default QuizAnswerOptions;