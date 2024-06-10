import "./style.css"
import { useState, useEffect } from "react";
import { getWorkoutTrackSummary } from "../../services/workoutService";
import { parseISO, format, formatDistanceToNow, isToday, isYesterday } from 'date-fns';
import { ru } from 'date-fns/locale';
import API from "../../services/API";
import { dateToFormatted } from "../../utils/usualTime";

type WorkoutTrackSummaryProps = {
    workoutId: string | undefined
}

type WorkoutSummaryInfo = {
    id: string,
    totalDistance: string,
    totalEllapsedTime: string,
    totalTimerTime: string,
    totalAscent: string,
    startTime: string
}

export default function WorkoutTrackSummary(props: WorkoutTrackSummaryProps) {
    const [workoutSummary, setWorkoutSummary] = useState<WorkoutSummaryInfo>();
    const [workoutTrackSummary, setWorkoutTrackSummary] = useState<WorkoutSummaryInfo>();

    useEffect(() => {
        fetchSummary();
        fetchTrackSummary();

    }, []);


    function fetchSummary() {
        API.get(`workouts/${props.workoutId}/summary`, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            
            const workoutSummaryInfo: WorkoutSummaryInfo = {
                id: response.data.id,
                totalDistance: response.data.totalDistance,
                totalEllapsedTime: response.data.totalEllapsedTime,
                totalTimerTime: response.data.totalTimerTime,
                totalAscent: response.data.totalAscent,
                startTime: dateToFormatted(response.data.startTime)
            }

            setWorkoutSummary(workoutSummaryInfo);
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 403) {
                alert("Error");
            }
            else if(error.response.status != 200) {
                
            }
        })
    }

    function fetchTrackSummary() {
        getWorkoutTrackSummary(props.workoutId)
        .then(response => {
            console.log(response.data.fitSessionData)
            //setWorkoutSummaryInfo(response.data.fitSessionData);
        })
        .catch(error => {
            console.error(error);

            if(error.response.status == 409) {
                //alert("This track file already exists");
            }
            else if(error.response.status != 200) {
                
            }
        })
    }

    if(!setWorkoutSummary && !setWorkoutTrackSummary) {
        return(<></>)
    }

    return(
    <div className="flex flex-col text-text items-start">
        <p className="">{workoutSummary?.startTime}</p>

        <div className="flex space-x-8 mt-2">
            <div className="flex flex-col">
                <p className="text-2xl">{workoutSummary?.totalDistance} км</p>
                <p>Дистанция</p>
            </div>

            <div className="flex flex-col">
                <p className="text-2xl">{workoutSummary?.totalEllapsedTime}</p>
                <p>Общее время</p>
            </div>

            <div className="flex flex-col">
                <p className="text-2xl">{workoutSummary?.totalAscent} м</p>
                <p>Высота</p>
            </div>
        </div>
    </div>
    )
}