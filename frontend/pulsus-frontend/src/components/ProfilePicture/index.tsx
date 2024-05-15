import "./style.css"
import API from "../../services/API";
import { useState, useEffect } from "react";

type ProfilePictureProps = {
    type: boolean,
    size: number
}

export default function ProfilePicture(props: ProfilePictureProps) {
    const [ picture, setPicture ]= useState("");

    useEffect(() => {
        loadProfilePicture();

    }, []);

    function loadProfilePicture() {
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
    
    if (!picture) {
        return ( 
        <div>

        </div>)
    }

    return(
    <div className="profile-picture">
        <img src={picture} width={props.size} height={props.size} />
    </div>
    )
}