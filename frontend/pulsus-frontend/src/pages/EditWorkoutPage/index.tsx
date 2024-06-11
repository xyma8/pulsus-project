import "./style.css"
import { useState, useEffect } from "react"
import { useParams } from 'react-router-dom';
import API from "../../services/API";
import EditInfoWorkoutForm from "../../components/EditInfoWorkoutForm";
import ListTypesSport from "../../components/ListTypesSport";
import UploadImage from "../../components/UploadImage";
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';

type Inputs = {
    name: string,
    description: string,
    accessType: number,
    typeSport: string
}

export default function EditWorkoutPage() {
    const { workoutId } = useParams();
    const [inputs, setInputs] = useState<Partial<Inputs>>();
    const [deleteButton, setDeleteButton] = useState<boolean>();
    const navigate = useNavigate();
    //const [workoutData, setWorkoutData] = useState<FormData>();
    const workoutPhotos = new FormData();

    useEffect(() => {
        checkAccessForPage();

    }, []);

    useEffect(() => {
        console.log("Inputs changed:", inputs);

    }, [inputs]); // Этот эффект сработает каждый раз, когда изменится inputs


    function checkAccessForPage() {
        API.get(`/workouts/${workoutId}/accessEditPage`, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            if(response.data.access == true)
                loadWorkoutInfo();
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 404) {
                navigate("*")
            }
            else if(error.response.status != 200) {
                
            }
        })
    }

    function loadWorkoutInfo() {
        API.get(`/workouts/${workoutId}`, {
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
                navigate("*")
            }
            else if(error.response.status != 200) {
                
            }
        })
    }

    function updateWorkoutInfo() {
        API.post(`/workouts/${workoutId}/edit`, inputs, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken'),
            }
        })
        .then(response => {
            console.log(response.data);
            toast.success('Информация успешно изменена!');
            navigate(`/workouts/${workoutId}`)
            //uploadWorkoutPhotos();
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 404) {
                navigate("*")
            }
            if(error.response.status == 403) {
                //alert("Access denied");
            }
            else if(error.response.status != 200) {
                
            }
        })
    }

    function uploadWorkoutPhotos() {
        if(!workoutPhotos.has("images")) return;

        console.log(workoutPhotos?.get("images"));
        API.post(`/workouts/${workoutId}/uploadPhotos`, workoutPhotos, {
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

    function deleteWorkout() {
        API.post(`/workouts/${workoutId}/delete`, null, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            //console.log(response.data);

            if(response.data.deletedWorkout) {
                toast.success('Тренировка удалена!');
                navigate('/dashboard')
            }
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 404) {
                navigate("*")
            }
            else{
                toast.error('Произошла ошибка при удалении!');
            }
        })
    }

    if (!inputs) {
        return ( 
        <div>
        </div>)
    }

    return(
    <div className="edit-workout-page items-center">
        <div className="flex justify-between">
            <p className="text-2xl font-bold mb-5">Редактировать тренировку</p>
            <div className="flex flex-col items-center">
                <button className="bg-danger text-main_text_button font-bold py-1 px-3 rounded hover:bg-danger_hover duration-100 text-s" onClick={() => setDeleteButton(true)}>
                    Удалить тренировку
                </button>
                {deleteButton ?
                <div className="flex flex-row">
                    <p>Удалить?</p>&nbsp;
                    <p className="underline text-text cursor-pointer hover:text-danger duration-100" onClick={deleteWorkout}>Да</p> &nbsp;
                    <p className="underline text-text cursor-pointer hover:text-secondary duration-100" onClick={() => setDeleteButton(false)}>Нет</p> 
                 </div>:
                  <></> }
            </div>
        </div>
        {workoutId && <EditInfoWorkoutForm workoutId={workoutId} inputs={inputs} onInputChange={handleInputChange}/> }
        
    </div>
    )
}