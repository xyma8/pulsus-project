import InformationChart from "../../components/WorkoutInformationChart";
import LandscapeChart from "../../components/WorkoutLandscapeChart";
import WorkoutInfo from "../../components/WorkoutInfo";
import "./style.css"
import { useParams } from 'react-router-dom';
import { useEffect } from "react";
import { getWorkoutTrack } from "../../services/workoutService";
import WorkoutTrackSummary from "../../components/WorkoutTrackSummary";
import WorkoutTrackContainer from "../../components/WorkoutTrackContainer";

export default function WorkoutPage() {
    const { workoutId } = useParams();

    useEffect(() => {
        //getTrackInfo();

    }, [workoutId]);


    return(
    <div className="workout-page">
        <div className="flex bg-block_background rounded shadow-md">
            <div className="flex-grow max-w-[50%] border p-3">
                <WorkoutInfo workoutId={workoutId} />
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