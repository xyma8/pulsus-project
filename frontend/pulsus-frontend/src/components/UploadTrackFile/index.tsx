import "./style.css"
import API from "../../utils/API";
import { useState, useEffect } from "react";

export default function UploadTrackFile() {
    const formData = new FormData();

    function uploadTrackFile() {
        API.post("/users/uploadGPXFile", formData, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken'),
                'Content-Type': 'multipart/form-data',  
            }
        })
        .then(response => {
            //console.log(response.data.path);
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