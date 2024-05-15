import "./style.css"
import API from "../../services/API";
import { useState, useEffect } from "react";

interface ListTypesSportProps {
    defaultTypeSport: string | undefined,
    onSelectChange: (selectedTypeSport: string) => void
}

type ListItem = {
    id: number,
    name: string
}

export default function ListTypesSport(props: ListTypesSportProps) {
    const [listTypesSport, setListTypesSport] = useState<ListItem[]>([]);

    useEffect(() => {
        getListTypesSport();

    }, []);

    function getListTypesSport() {
        API.get(`/workouts/typesSport`, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            const list: ListItem[] = response.data;
            console.log(list);
            setListTypesSport(list);

            for (let i = 0; i < list.length; i++) {
                const item = list[i];
                console.log(item);
            }
            
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

    function formatName(name: string): string {
        if(name == "CYCLING") {
            return "Заезд";
        }
        if(name == "RUNNING") {
            return "Забег"
        }
        else{
            return ""
        }
    }

    function handleSelectChange(selectedValue: string) {
        props.onSelectChange(selectedValue);
    }

    if (!setListTypesSport) {
        return ( 
        <div>
        </div>)
    }
    
    return(
    <div className="list-types-sport">
       Вид спорта
        <select defaultValue={props.defaultTypeSport} onChange={(e) => handleSelectChange(e.target.value)}>
        {listTypesSport.map((item, index) => (
          <option key={index} value={item.name}>
             {item.name}
          </option>
        ))}
      </select>
    </div>
    )
}