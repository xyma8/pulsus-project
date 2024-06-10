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
    <div className="flex flex-row">
        <div className="flex h-[100%] mt-3">
            <ProfileCard/>
        </div>
        
        <div className="ml-6 w-[100%]">
            <WorkoutPostsFeed size={5} loaderMessage="loading" endMessage="За последнее время больше не было тренировок." feed={true} />
        </div>
    </div>
    )
}