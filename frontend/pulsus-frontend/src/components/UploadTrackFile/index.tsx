import "./style.css"
import API from "../../utils/API";
import { useState, useEffect } from "react";
import { Coordinates } from "../../utils/projectTypes";

export default function UploadTrackFile() {
    const formData = new FormData();
    const coordinates: Coordinates = [];

    function uploadTrackFile() {
        API.post("/users/uploadFITFile", formData, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken'),
                'Content-Type': 'multipart/form-data',  
            }
        })
        .then(response => {
            //console.log(response.data);
            for (let i = 0; i < response.data.fitTrackData.length; i++) {
                const item = response.data.fitTrackData[i];
                console.log(item);
                coordinates.push([item.positionLat, item.positionLong]);
            }
            console.log(coordinates);
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
            uploadTrackFile();
        }
    }

    return(
    <div className="upload-track-file">
        <input type="file" onChange={selectFile}/>
    </div>
    )
}