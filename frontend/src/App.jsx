import { useState } from 'react'
import './App.css'
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
      <QuestionList questions={questions} />
    </>
  )
}

export default App
