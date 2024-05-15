import "./style.css"
import API from "../../services/API";
import { useState, useEffect } from "react";

type WorkoutId = {
    id: string | undefined
}

export default function WorkoutInfo(props: WorkoutId) {
    useEffect(() => {
        loadWorkoutInfo();

    }, []);

    function loadWorkoutInfo() {
        API.get(`/workouts/${props.id}`, {
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