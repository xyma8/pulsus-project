import "./style.css"
import { useState, useEffect } from "react";
import { getWorkoutTrack } from "../../services/workoutService";
import { Coordinates, InformationChart, TrackData, Coordinate } from "../../utils/projectTypes";
import WorkoutTrackMap from "../WorkoutTrackMap";
import WorkoutInformationChart from "../WorkoutInformationChart";

type WorkoutTrackContainerProps = {
    workoutId: string | undefined
}


type TrackDataArrays = {
    id: TrackData["id"][],
    coordinates: Coordinates,
    distance: TrackData["distance"][],
    speed: TrackData["speed"][],
    cadence: TrackData["cadence"][],
    temperature: TrackData["temperature"][]
}


export default function WorkoutTrackContainer(props: WorkoutTrackContainerProps) {
    const [trackDataArrays, setTrackDataArrays] = useState<TrackDataArrays>();
    const [currentChartCoordindate, setCurrentChartCoordinate] = useState<Coordinate | undefined>();
    const [tooltipChartsActive, setTooltipChartsActive] = useState<boolean>(false);

    useEffect(() => {
        loadData();

    }, []);

    function loadData() {
        getWorkoutTrack(props.workoutId)
        .then(response => {
            //console.log(response.data);
            //const trackData: TrackData[] = [];
            /*
            const initialId: TrackDataArrays["id"] = []
            const initialCoordinates: TrackDataArrays["coordinates"] = []
            const initialDistance: TrackDataArrays["distance"] = []
            const initialSpeed: TrackDataArrays["speed"] = []
            const initialCadence: TrackDataArrays["cadence"] = []
            */

            const initialTrackData: TrackDataArrays = {
                id: [],
                coordinates: [],
                distance: [],
                speed: [],
                cadence: [],
                temperature: []
            }

            for (let i = 0; i < response.data.fitTrackData.length; i++) {
                const item = response.data.fitTrackData[i];
                //console.log(item);
                initialTrackData.id.push(i)
                initialTrackData.coordinates.push([item.positionLat, item.positionLong])
                initialTrackData.distance.push(item.distance)
                initialTrackData.speed.push(item.enhancedSpeed)
                initialTrackData.cadence.push(item.cadence)
                initialTrackData.temperature.push(item.temperature)

                /*
                initialId.push(i)
                initialCoordinates.push([item.positionLat, item.positionLong]);
                initialDistance.push(item.distance);
                initialSpeed.push(item.enhancedSpeed);
                initialCadence.push(item.cadence);
                /*
                const initialDataTrack: TrackData = {
                    id: i,
                    coordinate: [item.positionLat, item.positionLong],
                    distance: item.distance,
                    speed: item.speed
                }
                */
                //trackData.push(initialDataTrack)
            }

            setTrackDataArrays(initialTrackData);
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

    function getCoordinateById(id: string | undefined) {
        //console.log(trackDataArrays?.coordinates[Number(id)])
        const point: Coordinate | undefined = trackDataArrays?.coordinates[Number(id)]
        setCurrentChartCoordinate(point)
    }

    function handleValueChange(id: string | undefined) {
        getCoordinateById(id)
    }

    function handleMouseEnter() {
        setTooltipChartsActive(true);
    }

    function handleMouseLeave() {
        setTooltipChartsActive(false);
        getCoordinateById(undefined);
    }

    if(!setTrackDataArrays) {
        return(
        <div></div>
        )
    }

    return(
    <div className="workout-container">
        {trackDataArrays?.coordinates && <WorkoutTrackMap coordinates={trackDataArrays.coordinates} center={trackDataArrays.coordinates[0]} point={currentChartCoordindate}/>}
        <div className="workout-container-charts" onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
            {trackDataArrays?.id && trackDataArrays?.speed &&
            <WorkoutInformationChart
                data={getDataForChart(trackDataArrays.id, trackDataArrays.speed)}
                width={1000}
                height={150}
                XLabel="Расстояние"
                YLabel="Скорость"
                colorStroke="#8884d8"
                syncId="trackChart"
                unit="км/ч"
                tooltipActive = {tooltipChartsActive}
                onValueChange={handleValueChange}/>}

            {trackDataArrays?.id && trackDataArrays?.temperature &&
            <WorkoutInformationChart
                data={getDataForChart(trackDataArrays.id, trackDataArrays.temperature)}
                width={1000}
                height={150}
                XLabel="Расстояние"
                YLabel="Температура"
                colorStroke="#008000"
                syncId="trackChart"
                unit="℃"
                tooltipActive = {tooltipChartsActive}
                onValueChange={handleValueChange}/>}
        </div>
    </div>
    )
}