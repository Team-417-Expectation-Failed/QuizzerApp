export function getPublishedQuizes() {
  return fetch('/api/quizzes')
  .then(response =>{
    if (!response.ok) 
        throw new Error("Error in fetch" + response.statusText);
    
        return response.json();
    
 })  
}