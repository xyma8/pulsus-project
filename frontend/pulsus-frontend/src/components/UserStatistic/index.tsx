import { useState, useEffect } from "react";
import API from "../../services/API";


type UserStatisticProps = {
    userId?: string | undefined,
    allTime: boolean
}

type UserStatistic = {
    countWorkouts: number,
    distance: string,
    ascent: string,
    time: string
}

export default function UserStatistic(props: UserStatisticProps) {
    const [userStatistic, setUserStatistic] = useState<UserStatistic>();

    useEffect(() => {
        fetchUserStatistic();

    }, []);

    function fetchUserStatistic() {
        var request = `/statistics/all`;
        if(!props.allTime && !props.userId) request = `/statistics/best`;
        if(props.allTime && props.userId) request = `/statistics/${props.userId}/all`;
        if(!props.allTime && props.userId) request = `/statistics/${props.userId}/best`;

        API.get(request, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            setUserStatistic(response.data);

        })
        .catch(error =>{
            console.error(error);
        })
    }

    if(!setUserStatistic) {
        return(<></>)
    }

    return(
    <div className="text-text p-1 mb-4">
        {props.allTime && <p className="font-bold mb-3">За все время</p>}
        {!props.allTime && <p className="font-bold mb-3">Лучшее время</p>}
        {props.allTime && <div className="flex justify-between mt-2 border-t">
            <p className="flex-grow max-w-[60%]">Тренировки</p>
            <p className="flex-grow max-w-[40%]">{userStatistic?.countWorkouts}</p>
        </div> }

        <div className="flex justify-between mt-2 border-t">
            {props.allTime && <p className="flex-grow max-w-[60%]">Расстояние</p>}
            {!props.allTime && <p className="flex-grow max-w-[60%]">Самый длинный заезд</p>}
            <p className="flex-grow max-w-[40%]">{userStatistic?.distance} км</p>
        </div>

        <div className="flex justify-between mt-2 border-t">
            <p className="flex-grow max-w-[60%]">Набор высоты</p>
            <p className="flex-grow max-w-[40%]">{userStatistic?.ascent} м</p>
        </div>

        <div className="flex justify-between mt-2 border-t">
            {props.allTime && <p className="flex-grow max-w-[60%]">Время</p> }
            {!props.allTime && <p className="flex-grow max-w-[60%]">Самый долгий заезд</p> }
            <p className="flex-grow max-w-[40%]">{userStatistic?.time}</p>
        </div>
    </div>
    )
}