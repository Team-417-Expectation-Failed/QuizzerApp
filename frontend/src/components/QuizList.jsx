import { useState ,useEffect }  from "react";

import { getPublishedQuizzes } from "../quizapi";
import  FetchQuizQuestions  from "./QuizQuestions";

function QuizList() {
  const [quizzes, setQuizzes] = useState([]);

  useEffect(() => {
    getPublishedQuizzes()
    .then((data) => setQuizzes(data))
  }, []);

  return (
    <div>
      <h1>Quiz List</h1>
      <ul>
        {quizzes.map((quiz) => (
          <li key={quiz.id}>
            {quiz.name}
            <FetchQuizQuestions quizId={quiz.id} />
          </li>
        ))}
      </ul>
    </div>
  );
}

export default QuizList;