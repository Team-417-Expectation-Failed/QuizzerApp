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
  return fetch(`${apiUrl}/quizzes/${quizId}/full`)
    .then(response => {
      if (!response.ok)
        throw new Error("Error in fetch" + response.statusText);
      return response.json();

    })
}

export function getQuizzesByCategory(categoryId) {
  return fetch(`${apiUrl}/categories/${categoryId}/quizzes`)
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

export function getCategories() {
  return fetch(`${apiUrl}/categories`)
    .then(response => {
      if (!response.ok)
        throw new Error("Error in fetch" + response.statusText);
      return response.json();

    })
}

<<<<<<< HEAD
export function getQuizReviews(quizId) {
  return fetch (`${apiUrl}/quizzes/${quizId}/reviews`)
  .then(response => {
    if (!response.ok)
      throw new Error("Error in fetch" + response.statusText);
    console.log(response);
    return response.json();

  })
=======
export function addQuizAnswer(answerObject) {
  console.log(answerObject);
  return fetch(`${apiUrl}/answers`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(answerObject)
  })
    .then(response => {
      if (!response.ok)
        throw new Error("Error in fetch" + response.statusText);
      return response.json();

    })
>>>>>>> 45d387a056d490dabc24b547bcee4eea3bad32a0
}