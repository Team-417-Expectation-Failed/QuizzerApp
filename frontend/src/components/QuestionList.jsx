import { Box, Typography } from '@mui/material';

function QuestionList({ questions }) {

    if (questions.length == 0) {
        return (
            <Box>
                <Typography>There are no questions</Typography>
            </Box>
        )
    }
    return (
        <Box>
            <Typography variant='h3'>All questions</Typography>

            {
                questions.map(question => {
                    return (
                        <Typography key={question.id}>{question.questionBody}</Typography>
                    );
                })
            }
        </Box>
    );
}

export default QuestionList;