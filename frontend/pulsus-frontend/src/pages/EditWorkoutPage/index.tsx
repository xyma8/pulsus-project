import "./style.css"
import { useState, useEffect } from "react"
import { useParams } from 'react-router-dom';
import API from "../../utils/API";
import EditInfoWorkoutForm from "../../components/EditInfoWorkoutForm";

type Inputs = {
    name: string,
    description: string,
    accessType: number
}

export default function EditWorkoutPage() {
    const { workoutId } = useParams();
    const [inputs, setInputs] = useState<Inputs>()

    useEffect(() => {
        loadWorkoutInfo();

    }, []);

    function loadWorkoutInfo() {
        API.get(`/users/workouts/${workoutId}`, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            //console.log(response.data);
            setInputs({name: response.data.name, description: response.data.description, accessType: response.data.accessType})
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

    function handleInputChange(newInputs: Inputs) {
        setInputs(newInputs);
        console.log(inputs);
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
    </div>
    )
}