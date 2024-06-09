import { useState, useEffect } from "react";
import API from "../../services/API";
import ProfilePicture from "../ProfilePicture";

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

    if(!setUsers) {
        return(<></>)
    }

    return(
    <div className="mt-5 ml-2">
        <ul>
            {users.map((user) => (
                <li key={user.id}>
                    <div className="flex flex-row items-center text-text text-m mt-3">
                    <ProfilePicture userId={user.id.toString()} size={40} clickable={true} />
                    <div className="cursor-pointer ml-3 hover:text-secondary">
                        {user.name} {user.surname}
                    </div>
                    </div>
                </li>
            ))}
        </ul>
    </div>
    )
}