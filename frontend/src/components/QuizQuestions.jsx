import { useEffect, useState, usetate } from 'react';

import { getQuizQuestions } from '../quizapi';

function FetchQuizQuestions({ quizId }) {
    const [questions, setQuestions] = useState([]);

    useEffect(() => {
        getQuizQuestions(quizId)
            .then((data) => setQuestions(data));
    }, [quizId]);

    return (
        <div>
            <ul>
                {questions.map((question) => (
                    <li key={question.id}>{question.questionBody}</li>
                ))}
            </ul>
        </div>
    );
}

export default FetchQuizQuestions;