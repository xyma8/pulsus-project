import "./style.css"
import "leaflet/dist/leaflet.css"
import { MapContainer, Polyline, TileLayer } from "react-leaflet"
import { Coordinates } from "../../utils/projectTypes";

type TrackMapProps = {
    coordinates: Coordinates;
}

export default function TrackMap(props: TrackMapProps) {

    return(
    <div className="track-map">
        <MapContainer center={[57.767918, 40.926894]} zoom={13}>
            <TileLayer 
                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />

            <Polyline
                pathOptions={{ fillColor: 'red', color: 'blue' }}
                positions={props.coordinates}
            />
        </MapContainer>
    </div>
    )
}