import ProfilePicture from "../ProfilePicture"
import "./style.css"
import API from "../../services/API";
import { useState, useEffect, useRef } from "react";
import { ToastContainer, toast } from 'react-toastify';

type ChangeProfilePicture = {
    type?: boolean,
    size: number
}

export default function ChangeProfilePicture(props: ChangeProfilePicture) {
    const formData = new FormData();
    const fileInputRef = useRef<HTMLInputElement>(null);

    function changeProfilePicture() {
        API.post("/users/uploadProfilePicture", formData, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken'),
                'Content-Type': 'multipart/form-data',  
            }
        })
        .then(response => {
            //console.log(response.data.path);
            toast.success('Аватарка изменена!');
            //setPicture(response.data.path);
        })
        .catch(error => {
            console.error(error);
            toast.error('Произошла ошибка при смене аватарки!');
        })
    }

    function selectFile(event: React.ChangeEvent<HTMLInputElement>) {
        const selectedFile = event.target.files?.[0];
        if(selectedFile) {
            formData.append('file', selectedFile);
            changeProfilePicture();
        }
    }

    const handleImageClick = () => {
        fileInputRef.current?.click();
      };

    return(
    <div className="">
        <div onClick={handleImageClick} title="Изменить аватарку" className="cursor-pointer">
            <ProfilePicture size={props.size}/>
        </div>
        
        <input accept=".jpg,.jpeg,.png,.gif" type="file" ref={fileInputRef}  className="hidden" onChange={selectFile}/>
    </div>
    )
}