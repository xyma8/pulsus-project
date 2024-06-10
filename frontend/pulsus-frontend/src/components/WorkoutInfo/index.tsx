import "./style.css"
import API from "../../services/API";
import { useState, useEffect } from "react";
import ProfilePicture from "../ProfilePicture";
import { useNavigate } from 'react-router-dom';

type WorkoutInfoProps = {
    workoutId: string | undefined,
    isEdit?: boolean
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
        {workoutInfo?.userId && <ProfilePicture userId={workoutInfo.userId} size={90} clickable={true} /> }
        <div className="flex flex-row justify-between w-[100%]">
            <div className="flex flex-col items-start ml-5">
                <div className="flex flex-row  items-center">
                    <p className="text-2xl font-bold">{workoutInfo?.name} </p>
                    {workoutInfo?.accessType === 2 && <p className="ml-1 text-xl" title="Только вы можете просматривать данные этой тренировки">🔒</p> }
                </div>
                <p className="mt-2">{workoutInfo?.description}</p>
            </div>
            {props.isEdit 
            && <button className="bg-primary text-main_text_button font-bold py-1 px-4 rounded hover:bg-primary_hover_button text-lduration-100 h-[40px]" onClick={() => navigate(`/workouts/${props.workoutId}/edit`)}>✏️</button> }
        </div>
    </div>
    )
}