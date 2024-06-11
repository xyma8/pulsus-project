import InformationChart from "../../components/WorkoutInformationChart";
import LandscapeChart from "../../components/WorkoutLandscapeChart";
import WorkoutInfo from "../../components/WorkoutInfo";
import "./style.css"
import { useParams } from 'react-router-dom';
import { useState, useEffect } from "react";
import { getWorkoutTrack } from "../../services/workoutService";
import WorkoutTrackSummary from "../../components/WorkoutTrackSummary";
import WorkoutTrackContainer from "../../components/WorkoutTrackContainer";
import API from "../../services/API";

export default function WorkoutPage() {
    const { workoutId } = useParams();
    const [isEdit, setIsEdit] = useState<boolean>(false)

    useEffect(() => {
        //getTrackInfo();
        checkAccessForPage();
    }, [workoutId]);

    function checkAccessForPage() {
        API.get(`/workouts/${workoutId}/accessEditPage`, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            if(response.data.access == true)
                setIsEdit(true);
            else
                setIsEdit(false);
        })
        .catch(error =>{
            setIsEdit(false);
        })
    }

    if(!setIsEdit) {
        return(<></>)
    }

    return(
    <div className="workout-page">
        <div className="flex bg-block_background rounded shadow-md">
            <div className="flex-grow max-w-[50%] border p-3">
                <WorkoutInfo workoutId={workoutId} isEdit={isEdit}/>
            </div>

            <div className="flex-grow max-w-[50%] border p-3">
                <WorkoutTrackSummary workoutId={workoutId} />
            </div>
        </div>
        
        <div className="flex flex-col bg-block_background rounded shadow-md mt-3 border">
            <WorkoutTrackContainer workoutId={workoutId}/>
        </div>
    </div>
    )
}