import ProfilePicture from "../ProfilePicture"
import "./style.css"
import API from "../../utils/API";
import { useState, useEffect } from "react";

type ChangeProfilePicture = {
    type: boolean,
    size: number
}

export default function ChangeProfilePicture(props: ChangeProfilePicture) {
    const formData = new FormData();

    function changeProfilePicture() {
        API.post("/users/uploadProfilePicture", formData, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken'),
                'Content-Type': 'multipart/form-data',  
            }
        })
        .then(response => {
            console.log(response.data.path);
            //setPicture(response.data.path);
        })
        .catch(error => {
            console.error(error);
            if(error.response.status == 403) {
                alert("Error");
            }
            else if(error.response.status != 200) {
                alert("Internal error");
            }
        })
    }

    function selectFile(event: React.ChangeEvent<HTMLInputElement>) {
        const selectedFile = event.target.files?.[0];
        if(selectedFile) {
            formData.append('file', selectedFile);
        }
    }

    return(
    <div className="change-profile-picture">
        <ProfilePicture type={props.type} size={props.size}/>
        <button onClick={changeProfilePicture}>Изменить фото профиля</button>
        <input accept="image/*" type="file" onChange={selectFile}/>
    </div>
    )
}