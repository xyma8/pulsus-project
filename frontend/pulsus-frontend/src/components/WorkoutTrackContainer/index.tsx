import "./style.css"
import { useState, useEffect } from "react";
import { getWorkoutTrack } from "../../services/workoutService";
import { Coordinates, InformationChart } from "../../utils/projectTypes";
import WorkoutTrackMap from "../WorkoutTrackMap";
import WorkoutInformationChart from "../WorkoutInformationChart";

type WorkoutTrackContainerProps = {
    workoutId: string | undefined
}

type TrackData = {
    coordinates: Coordinates,
    distance: number[],
    speed: number[],
}

export default function WorkoutTrackContainer(props: WorkoutTrackContainerProps) {
    const [trackData, setTrackData] = useState<TrackData>();

    useEffect(() => {
        loadData();

    }, []);

    function loadData() {
        getWorkoutTrack(props.workoutId)
        .then(response => {
            console.log(response.data);
            const initialCoordinates: Coordinates = [];
            const initialDistance: number[] = [];
            const initialSpeed: number[] = [];

            for (let i = 0; i < response.data.fitTrackData.length; i++) {
                const item = response.data.fitTrackData[i];
                //console.log(item);
                initialCoordinates.push([item.positionLat, item.positionLong]);
                initialDistance.push(item.distance);
                initialSpeed.push(item.enhancedSpeed);
            }
            //console.log(initialCoordinates);
            const initialDataTrack: TrackData = {
                coordinates: initialCoordinates,
                distance: initialDistance,
                speed: initialSpeed
            }
            setTrackData(initialDataTrack);
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

    function getDataForChart(xaxis: number[], yaxis: number[]): InformationChart {
        const informationChartData: InformationChart = xaxis.map((value, index) => ({
            XAxis: value,
            YAxis: yaxis[index]
        }));

        return informationChartData;
    }

    if(!setTrackData) {
        return(
        <div></div>
        )
    }

    return(
    <div className="workout-container">
        {trackData?.coordinates && <WorkoutTrackMap coordinates={trackData.coordinates} center={trackData.coordinates[0]} />}
        {trackData?.distance && trackData?.speed &&
         <WorkoutInformationChart data={getDataForChart(trackData.distance, trackData.speed)}
            width={1000} height={150} XLabel="Расстояние" YLabel="Скорость" colorStroke="#8884d8"/>}
    </div>
    )
}