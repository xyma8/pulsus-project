import { useState, useEffect } from "react";
import API from "../../services/API";
import ProfilePicture from "../ProfilePicture";

type ProfileCardProps = {
    userId?: string
}

type ProfileCardInfo = {
    userId: string,
    name: string,
    surname: string,
    login: string,
    followingCount: number,
    followersCount: number,
    workoutsCount: number
}

export default function ProfileCard(props: ProfileCardProps) {
    const [cardInfo, setCardInfo] = useState<ProfileCardInfo>();

    useEffect(() => {
        loadCardInfo();

    }, []);

    function loadCardInfo() {
        API.get('/users/profileCard', {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            console.log(response.data);
            setCardInfo(response.data)
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 403) {
                alert("Error");
            }
            else if(error.response.status != 200) {
                alert("Internal error");
            }
        })
    }

    return(
    <div className="flex flex-col items-center bg-block_background p-5 rounded shadow-md text-text mt-5">
        <ProfilePicture userId={cardInfo?.userId} size={75}/>
        <div className="flex flex-row mt-3 font-semibold text-2xl">
            <p> {cardInfo?.name} </p>
            &nbsp;
            <p> {cardInfo?.surname} </p>
        </div>
        
        <div className="flex gap-10 mt-3">
            <div className="flex flex-col items-center">
                <p className="text-sm">Подписки</p>
                <p className="text-xl font-medium"> {cardInfo?.followingCount} </p>
            </div>

            <div className="flex flex-col items-center">
                <p className="text-sm">Подписчики</p>
                <p className="text-xl font-medium"> {cardInfo?.followersCount} </p>
            </div>

            <div className="flex flex-col items-center">
                <p className="text-sm">Тренировки</p>
                <p className="text-xl font-medium"> {cardInfo?.workoutsCount} </p>
            </div>
        </div>
    </div>
    )
}