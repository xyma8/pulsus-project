import "./style.css"
import API from "../../utils/API";
import { useState, useEffect } from "react";

interface ListTypesSportProps {
    workoutId: string | undefined
}

type ListItem = {
    name: string
}

export default function ListTypesSport(props: ListTypesSportProps) {
    const [listTypesSport, setListTypesSport] = useState<ListItem[]>([]);

    useEffect(() => {
        getListTypesSport();

    }, []);

    function getListTypesSport() {
        API.get(`/users/workouts/typesSport`, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
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
            setListTypesSport({name: response.data.name})
            */
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 404) {
                alert("Not found");
            }
            else if(error.response.status != 200) {
                alert("Internal error");
            }
        })
    }

    // return <div/>??????
    
    return(
    <div className="list-types-sport">
        
    </div>
    )
}