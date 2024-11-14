import { useState ,useEffect }  from "react";

import { getPublishedQuizes } from "../quizapi";

function QuizList() {
  const [quizes, setQuizes] = useState([]);

  useEffect(() => {
    getPublishedQuizes()
    .then((data) => setQuizes(data))
  }, []);

  return (
    <div>
      <h1>Quiz List</h1>
      <ul>
        {quizes.map((quiz) => (
          <li key={quiz.id}>{quiz.name}</li>
        ))}
      </ul>
    </div>
  );
}

export default QuizList;