import { useState } from 'react'
import './App.css'
import QuestionList from './components/QuestionList'

function App() {

  const questions = [
    {
      id: 1,
      questionBody: "fdajfldköafj"
    }
  ]

  return (
    <>
      <h1>Quizzer App</h1>
      <QuestionList questions={questions} />
    </>
  )
}

export default App
