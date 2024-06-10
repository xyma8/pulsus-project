import "./style.css"
import API from "../../services/API";
import { useState, useEffect } from "react";
import ProfilePicture from "../ProfilePicture";
import { useNavigate } from 'react-router-dom';

type WorkoutInfoProps = {
    workoutId: string | undefined
}

type WorkoutInfo = {
    id: string,
    userId: string,
    name: string,
    description: string,
    typeSport: string,
    accessType: number,
    timestamp: Date
}

export default function WorkoutInfo(props: WorkoutInfoProps) {
    const [workoutInfo, setWorkoutInfo] = useState<WorkoutInfo>();
    const navigate = useNavigate();

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
            //console.log(response.data);
            setWorkoutInfo(response.data);
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

    //return <div/>
    if(!setWorkoutInfo) {
        return(<></>)
    }
    
    return(
    <div className="flex flex-row text-text items-center">
        {workoutInfo?.userId && <ProfilePicture userId={workoutInfo.userId} size={95} clickable={true} /> }
        <div className="flex flex-col items-start ml-6">
            <p className="text-3xl font-bold">{workoutInfo?.name}</p>
            <p className="mt-2">{workoutInfo?.description}</p>
        </div>
    </div>
    )
}