import "./style.css"
import Cookies from 'js-cookie'
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import API from "../../utils/API";
import ProfilePicture from "../../components/ProfilePicture";
import ChangeProfilePicture from "../../components/ChangeProfilePicture";

interface UserData {
    name: string,
    login: string
}

export default function DashboardPage() {
    const navigate = useNavigate();
    const [userData, setUserData] = useState<UserData | null>(null);
    useEffect(() => {
        //getUserData();

    }, []); 

    function exit() {
        Cookies.remove('token');
        navigate('/login');
    }

    function checkCookie() {
        if (!Cookies.get('token')) { 
            navigate('/login');
        }
    }

    function getUserData() {
        //checkCookie();

        API.get("/users/profile", {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            setUserData(response.data);
        })
        .catch(error => {
            exit();
        })
    }

    return(
    <div className="dashboard-page">
        hello in your account on dashboard
        <ProfilePicture type={false} size={50} />
        <ChangeProfilePicture type={false} size={50} />
        <button onClick={exit}>Exit</button>
    </div>
    )
}