import "./style.css"
import Cookies from 'js-cookie'
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import API from "../../services/API";
import ProfilePicture from "../../components/ProfilePicture";
import ChangeProfilePicture from "../../components/ChangeProfilePicture";
import UploadTrackFile from "../../components/UploadTrackFile";
import TrackMap from "../../components/TrackMap";

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
        localStorage.removeItem('jwtToken');
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
        <UploadTrackFile />
        <button onClick={exit}>Exit</button>
    </div>
    )
}