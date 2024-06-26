import "./style.css"
import { useState, useEffect } from "react";
import { getWorkoutTrack } from "../../services/workoutService";
import { Coordinates, InformationChart, TrackData, Coordinate } from "../../utils/projectTypes";
import WorkoutTrackMap from "../WorkoutTrackMap";
import WorkoutInformationChart from "../WorkoutInformationChart";
import CustomTooltipChart from "../CustomTooltipChart";

type WorkoutTrackContainerProps = {
    workoutId: string | undefined
}

type TrackDataArrays = {
    id: TrackData["id"][],
    coordinates: Coordinates,
    distance: TrackData["distance"][],
    speed: TrackData["speed"][],
    cadence: TrackData["cadence"][],
    temperature: TrackData["temperature"][],
    altitude: TrackData["altitude"][],
    grade: TrackData["grade"][]
}

type MainChartInfo = {
    distance: number | undefined,
    altitude: number | undefined,
    grade?: number | undefined
}


export default function WorkoutTrackContainer(props: WorkoutTrackContainerProps) {
    const [trackDataArrays, setTrackDataArrays] = useState<TrackDataArrays>();
    const [currentChartCoordindate, setCurrentChartCoordinate] = useState<Coordinate | undefined>();
    const [tooltipChartsActive, setTooltipChartsActive] = useState<boolean>(false);
    const [mainChartInfo, setMainChartInfo] = useState<MainChartInfo>();

    useEffect(() => {
        loadData();

    }, []);

    function loadData() {
        getWorkoutTrack(props.workoutId)
        .then(response => {
            const initialTrackData: TrackDataArrays = {
                id: [],
                coordinates: [],
                distance: [],
                speed: [],
                cadence: [],
                temperature: [],
                altitude: [],
                grade: []
            }

            for (let i = 0; i < response.data.fitTrackData.length; i++) {
                const item = response.data.fitTrackData[i];
                //console.log(item);
                initialTrackData.id.push(i)
                if (item.positionLat !== null && item.positionLong !== null) {
                    initialTrackData.coordinates.push([item.positionLat, item.positionLong])
                }
                
                initialTrackData.distance.push(item.distance)
                initialTrackData.speed.push(item.enhancedSpeed)
                initialTrackData.cadence.push(item.cadence)
                initialTrackData.temperature.push(item.temperature)
                initialTrackData.altitude.push(item.enhancedAltitude)
                initialTrackData.grade.push(item.grade)
            }

            setTrackDataArrays(initialTrackData);
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

    function getDataForChart(xaxis: number[], yaxis: number[]): InformationChart {
        const informationChartData: InformationChart = xaxis.map((value, index) => ({
            XAxis: value,
            YAxis: yaxis[index]
        }));

        return informationChartData;
    }

    function getCoordinateById(id: string | undefined) {
        const point: Coordinate | undefined = trackDataArrays?.coordinates[Number(id)]
        setCurrentChartCoordinate(point)
    }

    function handleValueChange(id: string | undefined) {
        if(id == undefined) {
            setTooltipChartsActive(false);
        }
        else if(id === "on") {
            setTooltipChartsActive(true);
        }
        getCoordinateById(id);
        updateMainChartInfo(id);
    }

    function updateMainChartInfo(id: string | undefined) {
        const mainChartInfo: MainChartInfo = {
            distance: trackDataArrays?.distance[Number(id)],
            altitude: trackDataArrays?.altitude[Number(id)],
            grade: trackDataArrays?.grade[Number(id)]

        }

        setMainChartInfo(mainChartInfo)
    }

    function findMaxArray(arr: number[]): number {
        if (arr.length === 0) {
          throw new Error("The array should not be empty.");
        }
      
        let max = 0
      
        for (let i = 1; i < arr.length; i++) {
          if (arr[i] > max) {
            max = arr[i];
          }
        }
      
        return max;
      }

    if(!setTrackDataArrays) {
        return(
        <div></div>
        )
    }

    return(
    <div className="flex flex-col">
        {trackDataArrays?.coordinates && <WorkoutTrackMap coordinates={trackDataArrays.coordinates} center={trackDataArrays.coordinates[0]} point={currentChartCoordindate}/>}
        <div className="flex flex-col items-center mt-5">
            <div className="flex justify-between">
                    <div className="font-semibold">
                        {"Дистанция"}
                    </div>
                    &nbsp;
                    <div className="font-semibold">
                        <CustomTooltipChart value={mainChartInfo?.distance?.toString()} label={"м"}/>
                    </div>
            </div>
            {trackDataArrays?.id && trackDataArrays?.altitude && trackDataArrays.altitude.length > 0 &&
            <WorkoutInformationChart
                type="area"
                data={getDataForChart(trackDataArrays.id, trackDataArrays.altitude)}
                width={1000}
                height={150}
                YLabel="Высота"
                colorStroke="#808080"
                syncId="trackChart"
                yDomain={[0, findMaxArray(trackDataArrays.altitude) * 2]}
                unit="м"
                tooltipActive = {tooltipChartsActive}
                onValueChange={handleValueChange}/>}

            {trackDataArrays?.id && trackDataArrays?.speed && trackDataArrays.speed.length > 0 &&
            <WorkoutInformationChart
                type="line"
                data={getDataForChart(trackDataArrays.id, trackDataArrays.speed)}
                width={1000}
                height={150}
                YLabel="Скорость"
                colorStroke="#8884d8"
                syncId="trackChart"
                unit="км/ч"
                tooltipActive = {tooltipChartsActive}
                onValueChange={handleValueChange}/>}

            {trackDataArrays?.id && trackDataArrays?.temperature && trackDataArrays.temperature.length > 0 &&
            <WorkoutInformationChart
                type="line"
                data={getDataForChart(trackDataArrays.id, trackDataArrays.temperature)}
                width={1000}
                height={150}
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