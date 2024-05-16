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

    }, []);


    return(
    <div className="workout-page">
        <WorkoutInfo workoutId={workoutId} />
        <WorkoutTrackSummary workoutId={workoutId} />
        <WorkoutTrackContainer workoutId={workoutId}/>
    </div>
    )
}