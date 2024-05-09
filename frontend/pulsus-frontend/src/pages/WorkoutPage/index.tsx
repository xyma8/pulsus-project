import "./style.css"
import { useParams } from 'react-router-dom';

export default function WorkoutPage() {
    const { workoutId } = useParams();

    return(
    <div className="workout-page">
        console.log({workoutId});
    </div>
    )
}