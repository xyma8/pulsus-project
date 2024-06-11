import { useParams } from 'react-router-dom';
import { useEffect, useState, useRef } from "react";
import UserSubscribeButton from '../../components/UserSubscribeButton';
import API from "../../services/API";
import ProfilePicture from '../../components/ProfilePicture';
import WorkoutPostsFeed from '../../components/WorkoutPostsFeed';
import UserSubscriptionList from '../../components/UserSubscriptionList';
import ChangeProfilePicture from '../../components/ChangeProfilePicture';
import UserStatistic from '../../components/UserStatistic';


type UserInfo = {
    id: string,
    name: string,
    surname: string,
    login: string
}

type UserSubscribersCount = {
    following: string,
    followers: string
}

export default function UserPage() {
    const { userId } = useParams();
    const [userInfo, setUserInfo] = useState<UserInfo>();
    const [subscriptionCount, setSubscriptionCount] = useState<UserSubscribersCount>();
    const [isUserPage, setIsUserPage] = useState<boolean>(false);
    const [activeTab, setActiveTab] = useState('Тренировки');
    const tabs = ['Тренировки', 'Подписки', 'Подписчики'];

    const workoutRef = useRef<HTMLDivElement | null>(null);
    const followingRef = useRef<HTMLDivElement | null>(null);
    const followersRef = useRef<HTMLDivElement | null>(null);

    useEffect(() => {
        loadUserInfo();

    }, [userId]);

    function loadUserInfo() {
        
        API.get(`/users/profile/byId/${userId}`, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            //console.log(response.data)
            setUserInfo(response.data);
        })
        .catch(error => {
            
        })

        API.get(`/subscriptions/${userId}/count`, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            setSubscriptionCount(response.data);
        })
        .catch(error => {
            
        })
    }

    function userPage(isUser: boolean) {
        if(isUser == true) {
            setIsUserPage(true);
        }else{
            setIsUserPage(false)
        }
    }

    if(!setUserInfo && !setSubscriptionCount) {
        return(<></>)
    }

    return(
    <div className="flex flex-col">
        <div className='flex flex-col items-center'>
            <div className='flex flex-col items-center'>
                {(userInfo?.id && !isUserPage) && userInfo.id && (<ProfilePicture userId={userInfo?.id} size={130} clickable={false} />) }
                
                {(userInfo?.id && isUserPage) && (<ChangeProfilePicture size={130} />) }

                <div className='flex mt-3 text-2xl font-bold'>
                    <p>{userInfo?.name}</p>
                    &nbsp;
                    <p>{userInfo?.surname}</p>
                </div>
            </div>
            <div className='mt-5'>
            {userInfo?.id && <UserSubscribeButton userId={userInfo?.id} isUserPage={userPage} /> }
            </div>
        </div>

        <div className='flex mt-10'>
            <div className='flex-grow max-w-[70%] border rounded'>
                <ul className='flex space-x-1'>
                    {tabs.map((tab) => (
                    <li
                        key={tab}
                        className={`cursor-pointer py-2 px-6 rounded ${
                        activeTab === tab ? 'bg-primary text-main_text_button' : 'bg-main_background text-text hover:bg-gray-200 duration-100'
                        }`}
                        onClick={() => setActiveTab(tab)} >
                        {tab}
                    </li>
                    ))}
                </ul>

                <div className="p-2 border">
                    <h1 className="text-xl font-bold">{activeTab}</h1>

                    <div style={{ display: activeTab === "Тренировки" ? 'block' : 'none' }} ref={workoutRef}>
                        {isUserPage ?
                            <WorkoutPostsFeed size={3} loaderMessage="loading..." endMessage="" /> :
                            userInfo?.id && <WorkoutPostsFeed userId={userInfo?.id} size={3} loaderMessage="loading..." endMessage="" />
                        }
                    </div>
                    
                    <div style={{ display: activeTab === "Подписки" ? 'block' : 'none' }} ref={followingRef}>
                        {userInfo?.id && <UserSubscriptionList userId={userInfo.id} following={true} />}
                    </div>

                    <div style={{ display: activeTab === "Подписчики" ? 'block' : 'none' }} ref={followersRef}>
                        {userInfo?.id && <UserSubscriptionList userId={userInfo.id} following={false} />}
                    </div>
                </div>
            </div>
            
            <div className='flex-grow max-w-[30%] ml-4'>
                <div className='flex flex-col items-center bg-block_background py-5 shadow-md rounded'>
                    <p className="text-xl font-medium">Подписчики</p>
                    <div className="flex gap-10 mt-3">
                        <div className="flex flex-col items-center">
                            <p className="text-sm">Подписки</p>
                            <p className="text-xl font-medium cursor-pointer hover:text-secondary duration-100" onClick={() => setActiveTab('Подписки')}> {subscriptionCount?.following} </p>
                        </div>

                        <div className="flex flex-col items-center">
                            <p className="text-sm">Подписчики</p>
                            <p className="text-xl font-medium cursor-pointer hover:text-secondary duration-100" onClick={() => setActiveTab('Подписчики')}> {subscriptionCount?.followers} </p>
                        </div>
                    </div>
                </div>

                <div className='flex flex-col  bg-block_background py-5 shadow-md rounded mt-3'>
                    <p className="text-xl font-medium text-center">Статистика</p>
                    <UserStatistic userId={userId} allTime={true}/>
                    <UserStatistic userId={userId} allTime={false}/>
                </div>
            </div>

        </div>
    </div>
    )
}