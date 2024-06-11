import { useState, useEffect } from "react";
import API from "../../services/API";

type UserSubscribeButtonProps = {
    userId: string | undefined,
    isUserPage?: (isUser: boolean) => void
}

export default function UserSubscribeButton(props: UserSubscribeButtonProps) {
    const [subscription, setSubscription] = useState<boolean | null>();
    const [buttonText, setButtonText] = useState<string>("Вы подписаны");
    
    useEffect(() => {
        loadSubscriptionInfo();

    }, []);

    function loadSubscriptionInfo() {
        API.get(`/subscriptions/${props.userId}/check`, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            console.log(response.data);
            if(response.data.checkSubscription) {
                setSubscription(true);
            }
            else{
                setSubscription(false);
            }
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 404) {
                alert("Workout not found");
            }
            else if(error.response.status == 400) {
                console.log("400");
                setSubscription(null);
            }
            else if(error.response.status != 200) {
                
            }
        })
    }

    function changeSubscription() {
        API.post(`/subscriptions/${props.userId}/change`, null, { //при post запросе обязательно передавать тело вторым параметром, либо null если оно не принимается
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            console.log(response.data);
            if(response.data.changeSubscription) {
                setSubscription(true);
            }
            else{
                setSubscription(false);
            }
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 404) {
                alert("Workout not found");
            }
            else if(error.response.status != 200) {
                
            }
        })
    }

    function handleMouseEnter() {
        setButtonText("Отписаться");
    }

    function handleMouseLeave() {
        setButtonText("Вы подписаны");
    }

    if(subscription == null) {
        if(props.isUserPage) {
            props.isUserPage(true);
        }
        return(<></>)
    }
    else{
        if(props.isUserPage) {
            props.isUserPage(false);
        }
    }

    return(
    <div>
        {subscription ?
            <button className="bg-primary text-main_text_button font-bold py-2 px-4 rounded hover:bg-primary_hover_button duration-100"
                onClick={changeSubscription}
                onMouseEnter={handleMouseEnter}
                onMouseLeave={handleMouseLeave}>{buttonText}</button>  :
            <button className="bg-secondary text-main_text_button font-bold py-2 px-4 rounded hover:bg-secondary_hover_button duration-100" onClick={changeSubscription}>Подписаться</button>
        }
    </div>
    )
}