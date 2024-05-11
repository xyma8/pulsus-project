import "./style.css"
import API from "../../utils/API";
import React, { useState } from 'react';

interface UploadImageProps {
    onDropChange: (formData: FormData) => void
}

export default function UploadImage(props: UploadImageProps) {
    const [image, setImage] = useState<File | null>(null);

    function handleDrop(event: React.DragEvent) {
        event.preventDefault();
        const file: File = event.dataTransfer.files[0];
        setImage(file);
    };

    function handleUpload(event: React.ChangeEvent<HTMLInputElement>) {
        const files: FileList | null = event.target.files;
        if (files && files.length > 10) {
            alert('Максимальное количество файлов: ' + 10);
            return;
        }
        
        if(!files) return;

        const formData = new FormData();
        for (let i = 0; i < files.length; i++) {
            formData.append('files', files[i]);
        }
        
        props.onDropChange(formData);
    }
    
    return(
    <div className="upload-image" onDrop={handleDrop}>
        <p className="upload-image-text">Перетащите фото или видео сюда</p>
        {image && <p>Загружено: {image.name}</p>}
        <input type="file" multiple className="upload-image-input" accept=".fit" onChange={handleUpload} />
    </div>
    )
}