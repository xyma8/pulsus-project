import "./style.css"
import API from "../../utils/API";
import { useState, useEffect } from "react";

type WorkoutId = {
    id: bigint
}

export default function WorkoutInfo(props: WorkoutId) {
    useEffect(() => {
        loadWorkoutInfo();

    }, []);

    function loadWorkoutInfo() {
        API.get("/users/workouts/${WorkoutId.id}", {
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

    return(
    <div className="workout-info">
        
    </div>
    )
}