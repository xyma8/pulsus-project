import "./style.css"
import API from "../../services/API";
import { useState, useEffect } from "react";
import { Coordinates } from "../../utils/projectTypes";

export default function UploadTrackFile() {
    const formData = new FormData();
    const coordinates: Coordinates = [];

    function uploadTrackFile() {
        API.post("/workouts/addNewWorkout", formData, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken'),
                'Content-Type': 'multipart/form-data',  
            }
        })
        .then(response => {
            console.log(response.data);
            /*
            for (let i = 0; i < response.data.fitTrackData.length; i++) {
                const item = response.data.fitTrackData[i];
                console.log(item);
                coordinates.push([item.positionLat, item.positionLong]);
            }
            console.log(coordinates);
            */

        })
        .catch(error => {
            console.error(error);

            if(error.response.status == 409) {
                alert("This track file already exists");
            }
            else if(error.response.status == 400) {
                alert("This type sport not allowed");
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