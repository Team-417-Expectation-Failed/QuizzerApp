export function getPublishedQuizzes() {
  return fetch(`${import.meta.env.VITE_API_URL}/quizzes`)
  .then(response =>{
    if (!response.ok) 
        throw new Error("Error in fetch" + response.statusText);
        return response.json();
    
 })  
}

export function getQuizQuestions(quizId) {
  return fetch(`${import.meta.env.VITE_API_URL}/quizzes/${quizId}/questions`)
  .then(response =>{
    if (!response.ok) 
        throw new Error("Error in fetch" + response.statusText);
        console.log(response);
        return response.json();
    
 })  
}