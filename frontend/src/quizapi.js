const apiUrl = import.meta.env.VITE_API_URL;

export function getPublishedQuizzes() {
  return fetch(`${apiUrl}/quizzes`)
    .then(response => {
      if (!response.ok)
        throw new Error("Error in fetch" + response.statusText);
      return response.json();

    })
}

export function getQuizById(quizId) {
  return fetch(`${apiUrl}/quizzes/${quizId}`)
    .then(response => {
      if (!response.ok)
        throw new Error("Error in fetch" + response.statusText);
      return response.json();

    })
}

export function getQuizQuestions(quizId) {
  return fetch(`${apiUrl}/quizzes/${quizId}/questions`)
    .then(response => {
      if (!response.ok)
        throw new Error("Error in fetch" + response.statusText);
      console.log(response);
      return response.json();

    })
}