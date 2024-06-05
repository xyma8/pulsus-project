import { useParams } from 'react-router-dom';
import { useEffect, useState } from "react";
import UserSubscribeButton from '../../components/UserSubscribeButton';

type userInfo = {
    userId: string,
    name: string,
    surname: string,
    login: string,
    followingCount: number,
    followersCount: number,
    workoutsCount: number
}

export default function UserPage() {
    const { userId } = useParams();
    const [userInfo, setUserInfo] = useState<userInfo>();

    useEffect(() => {
        loadUserInfo();

    }, []);

    function loadUserInfo() {

    }
    return(
    <div className="">
        <UserSubscribeButton userId={userId} />
    </div>
    )
}