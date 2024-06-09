import "./style.css"
import { useState, useEffect } from "react";
import API from "../../services/API";
import ProfilePicture from "../ProfilePicture";
import { useAuth } from "../../contexts/AuthContext";

type HeaderProps = {

}

type HeaderData = {
    userId: string
    name: string,
    surname: string,
    login: string
}

export default function Header(props: HeaderProps) {
    const { isAuthenticated, setIsAuthenticated } = useAuth();
    const [headerData, setHeaderData] = useState<HeaderData | null>();

    useEffect(() => {
        //fetchHeaderData();

    }, []);

    function fetchHeaderData() {
        API.get(`/users/profile`, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            setHeaderData(response.data)
            
        })
        .catch(error =>{
            console.error(error);
            setHeaderData(null)
        })
    }

    function navigateToDashboard() {
        window.location.assign(`/dashboard`);
    }

    function exit() {
        localStorage.removeItem('jwtToken');
        setIsAuthenticated(true);
        window.location.assign(`/`);
    }

    return(
    <div className="bg-block_background h-[50px] rounded shadow-sm sticky top-0">
        <div className="max-w-[1200px] ml-auto mr-auto flex">
            <img src={"https://free-png.ru/wp-content/uploads/2021/07/free-png.ru-53.png"} width={50} height={50} className="cursor-pointer" onClick={navigateToDashboard} />

            { isAuthenticated ? <></> : <div>hi</div>  }
            <button onClick={exit}>Exit</button>
        </div>
    </div>
    )
}