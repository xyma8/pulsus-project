import "./style.css"
import { useState, useEffect } from "react"
import { useParams } from 'react-router-dom';
import API from "../../utils/API";
import EditInfoWorkoutForm from "../../components/EditInfoWorkoutForm";
import ListTypesSport from "../../components/ListTypesSport";
import UploadImage from "../../components/UploadImage";
import { workerData } from "worker_threads";

type Inputs = {
    name: string,
    description: string,
    accessType: number,
    typeSport: string
}

export default function EditWorkoutPage() {
    const { workoutId } = useParams();
    const [inputs, setInputs] = useState<Partial<Inputs>>();
    //const [workoutData, setWorkoutData] = useState<FormData>();
    const workoutPhotos = new FormData();

    useEffect(() => {
        loadWorkoutInfo();

    }, []);

    useEffect(() => {
        console.log("Inputs changed:", inputs);
    }, [inputs]); // Этот эффект сработает каждый раз, когда изменится inputs


    function loadWorkoutInfo() {
        API.get(`/users/workouts/${workoutId}`, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            //console.log(response.data);
            setInputs({name: response.data.name, description: response.data.description, accessType: response.data.accessType, typeSport: response.data.typeSport})
            //setWorkoutInfo({inputs: inputs, typeSport: response.data.typeSport})
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 404) {
                alert("Workout not found");
            }
            else if(error.response.status != 200) {
                alert("Internal error");
            }
        })
    }

    function updateWorkoutInfo() {
        API.post(`/users/workouts/${workoutId}/edit`, inputs, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken'),
            }
        })
        .then(response => {
            console.log(response.data);
            uploadWorkoutPhotos();
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 404) {
                alert("Workout not found");
            }
            if(error.response.status == 403) {
                alert("Access denied");
            }
            else if(error.response.status != 200) {
                alert("Internal error");
            }
        })
    }

    function uploadWorkoutPhotos() {
        if(!workoutPhotos.has("images")) return;

        console.log(workoutPhotos?.get("images"));
        API.post(`/users/workouts/${workoutId}/uploadPhotos`, workoutPhotos, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken'),
                'Content-Type': 'multipart/form-data',
            }
        })
        .then(response => {
            console.log(response.data);
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 404) {
                alert("Workout not found");
            }
            if(error.response.status == 403) {
                alert("Access denied");
            }
            else if(error.response.status != 200) {
                alert("Internal error");
            }
        })
    }

    function handleInputChange(newInputs: Partial<Inputs>) {
        setInputs(prevInputs => ({ ...prevInputs, ...newInputs }));
    }

    function handleTypeSportChange(typeSport: string) {
        setInputs(prevInputs => ({ ...prevInputs, typeSport }));
    }

    function handleImagesChange(images: FileList) {
        for (let i = 0; i < images.length; i++) {
            workoutPhotos.append('images', images[i]);
        }
       
    }

    if (!inputs) {
        return ( 
        <div>
        </div>)
    }

    return(
    <div className="edit-workout-page">
        Редактировать тренировку
        <EditInfoWorkoutForm inputs={inputs} onInputChange={handleInputChange}/>
        <ListTypesSport defaultTypeSport={inputs.typeSport} onSelectChange={handleTypeSportChange}/>
        <UploadImage onDropChange={handleImagesChange}/>
        <button onClick={updateWorkoutInfo}>Сохранить</button>
    </div>
    )
}