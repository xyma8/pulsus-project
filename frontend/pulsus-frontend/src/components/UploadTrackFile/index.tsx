import "./style.css"
import API from "../../services/API";
import { useState, useEffect } from "react";
import { Coordinates } from "../../utils/projectTypes";
import { ToastContainer, toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';

export default function UploadTrackFile() {
    const formData = new FormData();
    const coordinates: Coordinates = [];
    const navigate = useNavigate();


    function uploadTrackFile() {
        API.post("/workouts/addNewWorkout", formData, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken'),
                'Content-Type': 'multipart/form-data',  
            }
        })
        .then(response => {
            console.log(response.data);

            toast.success('Тренировка успешно добавлена!');
            navigate(`/workouts/${response.data.id}/edit`)

        })
        .catch(error => {
            console.error(error);

            if(error.response.status == 409) {
                toast.error('Эта тренировка уже была загружена ранее!');
            }
            else if(error.response.status == 400) {
                toast.error('Этот тип спорта пока что не поддерживается');
            }
            else if(error.response.status != 200) {
                
            }
        })
    }

    function selectFile(event: React.ChangeEvent<HTMLInputElement>) {
        const selectedFile = event.target.files?.[0];
        if(selectedFile) {
            formData.append('file', selectedFile);
            uploadTrackFile();
        }
    }

    return(
    <div className="upload-track-file">
        <input type="file" accept=".fit,.gpx" onChange={selectFile}/>
    </div>
    )
}