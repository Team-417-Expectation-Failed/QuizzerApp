import { useState } from 'react'
import './App.css'
import QuizList from './components/QuizList'
import QuestionList from './components/QuestionList'

function App() {

  const questions = [
    {
      id: 1,
      questionBody: "first question"
    },
    {
      id: 2,
      questionBody: "second question"
    },
  ]

  return (
    <>
      <QuizList />
      <QuestionList questions={questions} />
    </>
  )
}

export default App
