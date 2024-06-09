import "./style.css"
import { useEffect } from "react";
import { getWorkoutTrackSummary } from "../../services/workoutService";

type WorkoutTrackSummaryProps = {
    workoutId: string | undefined
}
export default function WorkoutTrackSummary(props: WorkoutTrackSummaryProps) {

    useEffect(() => {
        getSummary();

    }, []);

    function getSummary() {
        getWorkoutTrackSummary(props.workoutId)
        .then(response => {
            console.log(response.data);
            /*
            for (let i = 0; i < response.data.fitTrackData.length; i++) {
                const item = response.data.fitTrackData[i];
                console.log(item);
                coordinates.push([item.positionLat, item.positionLong]);
            }
            console.log(coordinates);
            */

        })
        .catch(error => {
            console.error(error);

            if(error.response.status == 409) {
                alert("This track file already exists");
            }
            else if(error.response.status != 200) {
                
            }
        })
    }

    return(
    <div className="workout-track-summmary">

    </div>
    )
}