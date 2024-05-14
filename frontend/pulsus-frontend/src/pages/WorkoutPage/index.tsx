import InformationChart from "../../components/InformationChart";
import LandscapeChart from "../../components/LandscapeChart";
import WorkoutInfo from "../../components/WorkoutInfo";
import "./style.css"
import { useParams } from 'react-router-dom';
import { useEffect } from "react";
import API from "../../utils/API";

export default function WorkoutPage() {
    const { workoutId } = useParams();

    useEffect(() => {
        getTrackInfo();

    }, []);

    function getTrackInfo() {
        API.post("/users/addNewWorkout", workoutId, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken'),
                'Content-Type': 'multipart/form-data',  
            }
        })
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
                alert("Internal error");
            }
        })
    }

    return(
    <div className="workout-page">
        <WorkoutInfo id={workoutId}/>
        <InformationChart />
    </div>
    )
}