import "./style.css"
import "leaflet/dist/leaflet.css"
import { MapContainer, Marker, Polyline, Popup, TileLayer, } from "react-leaflet"
import L from 'leaflet';
import { Coordinates, Coordinate } from "../../utils/projectTypes";

type TrackMapProps = {
    coordinates: Coordinates,
    center: Coordinate,
    point: Coordinate | undefined
}

const customIcon = new L.Icon({
    iconUrl: 'https://i.pinimg.com/originals/7a/80/c9/7a80c9fbeb2158487b68c827a17bbbea.png',
    iconSize: [32, 32],
    iconAnchor: [16, 32],
    popupAnchor: [0, -32]
});

export default function WorkoutTrackMap(props: TrackMapProps) {

    return(
    <div className="track-map">
        <MapContainer center={props.center} zoom={13}>
            <TileLayer 
                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />

            <Polyline
                pathOptions={{ fillColor: 'darkred', color: 'blue' }}
                positions={props.coordinates}
            />

        {(props.point !== undefined) &&
        <Marker position={props.point}>
                <Popup>

                </Popup>
        </Marker> }
        </MapContainer>
    </div>
    )
}