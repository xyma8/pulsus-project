import "./style.css"
import API from "../../services/API";
import { useState, useEffect } from "react";

type ProfilePictureProps = {
    userId?: string
    rounded?: boolean,
    clickable?: boolean,
    size: number
}

export default function ProfilePicture(props: ProfilePictureProps) {
    const [ picture, setPicture ]= useState("");

    useEffect(() => {

        if(!props.userId) {
            loadProfilePic();
        }
        else{
            loadProfilePicById();
        }

    }, []);

    function loadProfilePic() {
        API.get("/users/profilePicture", {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            console.log(response.data.path);
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
            console.log(response.data.path);
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