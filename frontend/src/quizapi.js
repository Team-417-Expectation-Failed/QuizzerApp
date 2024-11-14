export function getPublishedQuizes() {
  return fetch(`${import.meta.env.VITE_API_URL}/quizzes`)
  .then(response =>{
    if (!response.ok) 
        throw new Error("Error in fetch" + response.statusText);
        console.log(response);
        return response.json();
    
 })  
}