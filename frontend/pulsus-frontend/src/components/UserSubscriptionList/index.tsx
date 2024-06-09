import { useState, useEffect } from "react";
import API from "../../services/API";
import ProfilePicture from "../ProfilePicture";
import { useNavigate } from "react-router"
import { userInfo } from "os";

type UserSubscriptionList = {
    userId: string,
    following: boolean
}

type UserInfoDto = {
    id: string,
    name: string,
    surname: string,
    login: string
}

export default function UserSubscriptionList(props: UserSubscriptionList) {
    const [users, setUsers] = useState<UserInfoDto[]>([]);
    const navigate = useNavigate();

    useEffect(() => {
        fetchSubscriptionList();

    }, []);

    function fetchSubscriptionList() {
        var request = `/subscriptions/${props.userId}/following`

        if(!props.following) request = `/subscriptions/${props.userId}/followers`

        API.get(request, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            console.log(response.data);
            setUsers(response.data);
        })
        .catch(error =>{
            console.error(error);
            
        })
    }

    function navigateToUserPage(userId: string) {
        window.location.assign(`/users/${userId}`);
    }
    if(!setUsers) {
        return(<></>)
    }

    return(
    <div className="mt-5 bg-block_background shadow-md  pl-2 pb-2 pt-1">
        <ul>
            {users.map((user) => (
                <li key={user.id}>
                    <div className="flex flex-row items-center text-text text-m mt-4">
                    <ProfilePicture userId={user.id.toString()} size={40} clickable={true} />
                    <div className="cursor-pointer ml-3 hover:text-secondary duration-100" onClick={() => navigateToUserPage(user.id)}>
                        {user.name} {user.surname}
                    </div>
                    </div>
                </li>
            ))}
        </ul>
    </div>
    )
}