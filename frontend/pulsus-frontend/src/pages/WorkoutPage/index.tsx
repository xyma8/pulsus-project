import WorkoutInfo from "../../components/WorkoutInfo";
import "./style.css"
import { useParams } from 'react-router-dom';

export default function WorkoutPage() {
    const { workoutId } = useParams();

    return(
    <div className="workout-page">
        <WorkoutInfo id={workoutId}/>
    </div>
    )
}