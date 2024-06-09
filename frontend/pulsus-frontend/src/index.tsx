import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './components/App/App';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import reportWebVitals from './reportWebVitals';
import RegistrationPage from './pages/RegistrationPage';
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import WorkoutPage from './pages/WorkoutPage';
import EditWorkoutPage from './pages/EditWorkoutPage';
import Header from './components/Header';
import UserPage from './pages/UserPage';
import { AuthProvider } from './contexts/AuthContext';

const router = createBrowserRouter([
  {
    path: '/',
    element: <RegistrationPage/>,
  },
  {
    path: '/registration',
    element: <RegistrationPage/>
  },
  {
    path: '/login',
    element: <LoginPage/>
  },
  {
    path: '/dashboard',
    element: <DashboardPage/>
  },
  {
    path: '/workouts/:workoutId',
    element: <WorkoutPage/>
  },
  {
    path: '/workouts/:workoutId/edit',
    element: <EditWorkoutPage/>
  },
  {
    path: '/users/:userId',
    element: <UserPage />
  }
]);

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <AuthProvider>
      <Header/>
      <div className='max-w-[1200px] h-screen ml-auto mr-auto py-5'>
        <RouterProvider router={router}/>
      </div>
    </AuthProvider>
  </React.StrictMode>
);

reportWebVitals();
