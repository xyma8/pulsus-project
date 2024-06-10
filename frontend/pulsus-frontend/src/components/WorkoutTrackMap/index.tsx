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
    iconUrl: '/uploads/assets/location.png',
    iconSize: [32, 32],
    iconAnchor: [16, 32],
    popupAnchor: [0, -32]
});

const finishIcon = new L.Icon({
    iconUrl: '/uploads/assets/finish.png',
    iconSize: [32, 32],
    iconAnchor: [0, 32],
    popupAnchor: [0, -32]
});

export default function WorkoutTrackMap(props: TrackMapProps) {

    return(
    <div className="w-100% h-[400px]">
        <MapContainer center={props.center} zoom={13} scrollWheelZoom={false}>
            <TileLayer 
                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />

            <Polyline
                pathOptions={{ fillColor: 'darkred', color: 'blue' }}
                positions={props.coordinates}
            />

        {(props.point !== undefined) &&
        <Marker position={props.point} icon={customIcon}>
                <Popup>

                </Popup>
        </Marker> }

        <Marker position={props.coordinates[props.coordinates.length - 1]} icon={finishIcon}>
                <Popup>

                </Popup>
        </Marker>
        </MapContainer>
    </div>
    )
}