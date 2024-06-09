import "./style.css"
import Cookies from 'js-cookie'
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import API from "../../services/API";
import ProfilePicture from "../../components/ProfilePicture";
import ChangeProfilePicture from "../../components/ChangeProfilePicture";
import UploadTrackFile from "../../components/UploadTrackFile";
import TrackMap from "../../components/WorkoutTrackMap";
import WorkoutPost from "../../components/WorkoutPost";
import ProfileCard from "../../components/ProfileCard";
import WorkoutPostsFeed from "../../components/WorkoutPostsFeed";

interface UserData {
    name: string,
    login: string
}

export default function DashboardPage() {
    //const navigate = useNavigate();
    //const [userData, setUserData] = useState<boolean>();
    
    useEffect(() => {
        //getUserData();
        //checkToken();

    }, []); 

    function exit() {
        localStorage.removeItem('jwtToken');
        window.location.assign(`/login`);
    }

    /*
    function checkToken() {
        if(!localStorage.getItem('jwtToken')) {
            console.log("not token")
            window.location.assign(`/login`);
        }
    }

    function getUserData() {
        checkToken();

        API.get("/users/profile", {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            setUserData(true);
        })
        .catch(error => {
            setUserData(false);
            exit();
        })
    }

    if(!userData) {
        return(<></>)
    }
    */

    return(
    <div className="dashboard-page">
        hello in your account on dashboard
        <ProfilePicture size={50} />
        <ProfilePicture userId={"15"} size={50}/>
        <ChangeProfilePicture type={false} size={50} />
        <UploadTrackFile />
        <WorkoutPost workoutId={"2"}/>
        <ProfileCard/>
        <WorkoutPostsFeed size={5} loaderMessage="loading" endMessage="За последнее время больше не было тренировок."/>
        <button onClick={exit}>Exit</button>
    </div>
    )
}