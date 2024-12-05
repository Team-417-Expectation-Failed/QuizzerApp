import './App.css'
import Menu from './components/Menu'
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import QuizList from './components/QuizList';
import QuizQuestions from './components/QuizQuestions';
import CategoriesList from './components/CategoriesList';
import QuestionsByCategory from './components/QuestionsByCategory';
import QuizResults from './components/QuizResults';
<<<<<<< HEAD
import ReviewList from './components/ReviewList';
=======
import ReviewForm from './components/ReviewForm';
>>>>>>> 45d387a056d490dabc24b547bcee4eea3bad32a0

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
        {
          path: "/quiz/:id/results",
          element: <QuizResults />,
        },
        {
<<<<<<< HEAD
          path: "/quiz/:id/reviews",
          element: <ReviewList />,
        },

=======
          path: "/quiz/:id/review",
          element: <ReviewForm />,
        },
>>>>>>> 45d387a056d490dabc24b547bcee4eea3bad32a0
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
