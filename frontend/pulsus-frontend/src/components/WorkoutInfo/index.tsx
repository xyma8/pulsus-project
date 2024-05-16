import "./style.css"
import API from "../../services/API";
import { useEffect } from "react";

type WorkoutInfoProps = {
    workoutId: string | undefined
}

export default function WorkoutInfo(props: WorkoutInfoProps) {
    useEffect(() => {
        loadWorkoutInfo();

    }, []);

    function loadWorkoutInfo() {
        API.get(`/workouts/${props.workoutId}`, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
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
            else if(error.response.status != 200) {
                alert("Internal error");
            }
        })
    }

    //return <div/>

    
    return(
    <div className="workout-info">
        
    </div>
    )
}