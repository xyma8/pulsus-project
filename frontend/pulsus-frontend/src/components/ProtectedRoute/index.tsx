import React, { useEffect, useState } from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import API from "../../services/API";

const checkTokenValidity = async (token: string): Promise<boolean> => {

    API.get("/auth/checkToken", {
        headers: {
            Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
        }
    })
    .then(response => {
        console.log(response);
        return true;
    })
    .catch(error => {
        return false;
    })

    return false;
};


const ProtectedRoute: React.FC<{ element: JSX.Element }> = ({ element }) => {
    const location = useLocation();
    const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null);
  
    useEffect(() => {
      const jwtToken = localStorage.getItem('jwtToken');
  
      if (!jwtToken) {
        setIsAuthenticated(false);
        return;
      }
      
      setIsAuthenticated(true);
      /*
      checkTokenValidity(jwtToken).then(isValid => {
        setIsAuthenticated(isValid);
      });
      */
    }, [location]);
  
    if (isAuthenticated === null) {
        return <></>; // Или любой индикатор загрузки
    }
  
    return isAuthenticated ? element : <Navigate to="/login" />;
  };
  
  export default ProtectedRoute;