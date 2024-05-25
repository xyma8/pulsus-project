export type Coordinate = [number, number]

export type Coordinates = Coordinate[];

//export function extractCoordinates(coordinate: Coordinate[])

export type TrackData = {
    id: number,
    coordinate: Coordinate,
    distance: number,
    speed: number,
    cadence: number,
    temperature: number
}

interface DataPoint {
    XAxis: number;
    YAxis: number;
}

export type InformationChart = DataPoint[]