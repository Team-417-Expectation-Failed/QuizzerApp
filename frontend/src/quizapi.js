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

export function getCategoryById(categoryId) {
  return fetch(`${apiUrl}/categories/${categoryId}`)
    .then(response => {
      if (!response.ok)
        throw new Error("Error in fetch" + response.statusText);
      return response.json();

    })
}

// Add answer to answerOption by answerOptionId
export function addAnswer(answerOptionId) {
  return fetch(`${apiUrl}/answers`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ answerOptionId }),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Failed to submit answer: " + response.statusText);
      }
      return response.json();
    });
}

// Fetch and return all reviews for a specific quiz by quizId
export function getQuizReviews(quizId) {
  return fetch(`${apiUrl}/quizzes/${quizId}/reviews`)
    .then(response => {
      if (!response.ok)
        throw new Error("Error in fetch" + response.statusText);
      console.log(response);
      return response.json();

    })
}

// Post a new review with reviewData to backend
export function createReview(reviewData) {
  return fetch(`${apiUrl}/reviews`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(reviewData),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Failed to submit review: " + response.statusText);
      }
      return response.json();
    });
}

// Fetch review data by reviewId
// Get the details of a review for editing
export function getReviewById(reviewId) {
  return fetch(`${apiUrl}/reviews/${reviewId}`)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Error fetching review");
      }
      return response.json();
    });
}

// Update review with reviewData by reviewId
export function updateReview(updatedReview) {
  return fetch(`${apiUrl}/reviews/${updatedReview.id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(updatedReview),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Error updating review");
      }
      return response.json();
    });
}

// Delete review by reviewId
export function deleteReview(reviewId) {
  return fetch(`${apiUrl}/reviews/${reviewId}`, {
    method: 'DELETE',
  })
    .then(response => {
      if (!response.ok)
        throw new Error("Error in fetch: " + response.statusText);
    })
}

export function getQuizResults(quizId) {
  return fetch(`${apiUrl}/quizzes/${quizId}/results`)
    .then(response => {
      if (!response.ok)
        throw new Error("Error in fetch" + response.statusText);
      return response.json();

    })
}