import { useState, useEffect } from "react";
import API from "../../services/API";
import ProfilePicture from "../ProfilePicture";
import { useNavigate } from "react-router"

type ProfileCardProps = {
    userId?: string
}

type ProfileCardInfo = {
    id: string,
    name: string,
    surname: string,
    login: string,
    followingCount: number,
    followersCount: number,
    workoutsCount: number
}

export default function ProfileCard() {
    const [cardInfo, setCardInfo] = useState<ProfileCardInfo>();
    const navigate = useNavigate();

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
            //console.log(response.data);
            setCardInfo(response.data)
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 403) {
                alert("Error");
            }
            else if(error.response.status != 200) {
                
            }
        })
    }

    function navigateToUserPage() {
        navigate(`/users/${cardInfo?.id}`);
    }

    if(!setCardInfo) {
        return(<></>)
    }

    return(
    <div className="flex flex-col items-center bg-block_background p-5 rounded shadow-md text-text mt-5">
        {cardInfo?.id && <ProfilePicture userId={cardInfo.id} size={75} clickable={true}/> }
        <div className="flex flex-row mt-3 font-semibold text-2xl hover:text-secondary cursor-pointer duration-100" onClick={() => navigateToUserPage()}>
            <p> {cardInfo?.name} </p>
            &nbsp;
            <p> {cardInfo?.surname} </p>
        </div>
        
        <div className="flex gap-10 mt-3">
            <div className="flex flex-col items-center">
                <p className="text-sm">Подписки</p>
                <p className="text-xl font-medium hover:text-secondary cursor-pointer duration-100"> {cardInfo?.followingCount} </p>
            </div>

            <div className="flex flex-col items-center">
                <p className="text-sm">Подписчики</p>
                <p className="text-xl font-medium hover:text-secondary cursor-pointer duration-100"> {cardInfo?.followersCount} </p>
            </div>

            <div className="flex flex-col items-center">
                <p className="text-sm">Тренировки</p>
                <p className="text-xl font-medium hover:text-secondary cursor-pointer duration-100"> {cardInfo?.workoutsCount} </p>
            </div>
        </div>
    </div>
    )
}