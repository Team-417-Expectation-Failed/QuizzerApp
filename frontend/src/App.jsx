import { useState } from 'react'
import './App.css'
import Menu from './components/Menu'
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import QuizList from './components/QuizList';
import QuizQuestions from './components/QuizQuestions';
import CategoriesList from './components/CategoriesList';
import QuestionsByCategory from './components/QuestionsByCategory';

function App() {

  const router = createBrowserRouter([
    {
      element: <Menu />,
      children: [
        {
          path: "/",
          element: <QuizList />
        },
        {
          path: "/quiz/:id/questions",
          element: <QuizQuestions />
        },
        {
          path: "/categories",
          element: <CategoriesList />,
        },
        {
          path: "/categories/:id/quizzes",
          element: <QuestionsByCategory />,
        },
      ]
    },
  ]);

  return (
    <>
      <RouterProvider router={router} />
    </>
  )
}

export default App
