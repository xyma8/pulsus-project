import { useParams } from 'react-router-dom';
import { useEffect, useState } from "react";
import UserSubscribeButton from '../../components/UserSubscribeButton';
import API from "../../services/API";
import ProfilePicture from '../../components/ProfilePicture';
import WorkoutPostsFeed from '../../components/WorkoutPostsFeed';


type userInfo = {
    id: string,
    name: string,
    surname: string,
    login: string
}

export default function UserPage() {
    const { userId } = useParams();
    const [userInfo, setUserInfo] = useState<userInfo>();
    const [isUserPage, setIsUserPage] = useState<boolean>();
    const [activeTab, setActiveTab] = useState('Тренировки');
    const tabs = ['Тренировки', 'Подписки', 'Подписчики'];

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
            console.log(response.data)
            setUserInfo(response.data);
        })
        .catch(error => {
            
        })
    }

    function userPage(isUser: boolean) {
        if(isUser) {
            setIsUserPage(true);
        }
    }

    if(!setUserInfo) {
        return(<></>)
    }

    return(
    <div className="flex flex-col">
        <div className='flex flex-col items-center'>
            <div className='flex flex-col items-center'>
                {userInfo?.id && <ProfilePicture userId={userInfo?.id} size={130} clickable={false} /> }
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
            <div className='flex-grow max-w-70'>
                <ul className='flex space-x-4'>
                    {tabs.map((tab) => (
                    <li
                        key={tab}
                        className={`cursor-pointer py-2 px-4 rounded ${
                        activeTab === tab ? 'bg-primary text-main_text_button' : 'bg-main_background text-text hover:bg-gray-200 duration-100'
                        }`}
                        onClick={() => setActiveTab(tab)}
                    >
                        {tab}
                    </li>
                    ))}
                </ul>

                <div className="mt-4 p-4 border rounded">
                    <h1 className="text-xl font-bold">{activeTab}</h1>
                    {isUserPage ? <WorkoutPostsFeed size={3}/> : userInfo?.id && <WorkoutPostsFeed userId={userInfo?.id} size={3}/>}
                </div>
            </div>
            
            <div className='flex-grow max-w-30'>
                
            </div>
        </div>
    </div>
    )
}