function QuestionList({ questions }) {

    if (questions.length == 0) {
        return (<p>There are no questions</p>)
    }
    return (
        <div>
            <h2>All questions</h2>

            {
                questions.map(question => {
                    return (
                        <p key={question.id}>
                            Question: {question.questionBody} <br />
                        </p>
                    );
                })
            }
        </div>
    );
}

export default QuestionList;