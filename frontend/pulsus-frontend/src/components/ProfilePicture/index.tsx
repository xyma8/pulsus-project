import "./style.css"
import API from "../../services/API";
import { useNavigate } from "react-router"
import { useState, useEffect } from "react";

type ProfilePictureProps = {
    userId?: string
    rounded?: boolean,
    clickable?: boolean,
    size: number
}

export default function ProfilePicture(props: ProfilePictureProps) {
    const [ picture, setPicture ]= useState("");
    const navigate = useNavigate();

    useEffect(() => {

        //изменить так не работает
        if(!props.userId) {
            loadProfilePic();
        }
        else{
            loadProfilePicById();
        }

    }, []);

    function loadProfilePic() {
        console.log("load");
        API.get("/users/profilePicture", {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            setPicture(response.data.path);
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 403) {
                alert("Error");
            }
            else if(error.response.status != 200) {
                alert("Internal error");
            }
        })
    }

    function loadProfilePicById() {
        API.get(`/users/profilePicture/${props.userId}`, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            setPicture(response.data.path);
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 403) {
                alert("Error");
            }
            else if(error.response.status != 200) {
                alert("Internal error");
            }
        })
    }
    
    function navigateToUserPage() {
        navigate(`/users/${props.userId}`);
    }

    if (!picture) {
        return ( 
        <div>

        </div>)
    }

    return(
    <div className="">
        {props.clickable ?
         <img src={picture} width={props.size} height={props.size} className="rounded-full cursor-pointer" onClick={navigateToUserPage}/> :
         <img src={picture} width={props.size} height={props.size} className="rounded-full"/> }
    </div>
    )
}