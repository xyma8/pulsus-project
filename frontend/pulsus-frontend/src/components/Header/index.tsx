import "./style.css"
import { useState, useEffect } from "react";
import API from "../../services/API";
import ProfilePicture from "../ProfilePicture";
import { useAuth } from "../../contexts/AuthContext";
import axios from "axios"

type HeaderProps = {

}

type HeaderData = {
    userId: string
    name: string,
    surname: string,
    login: string
}

export default function Header(props: HeaderProps) {
    //const { isAuthenticated, setIsAuthenticated } = useAuth();
    const [headerData, setHeaderData] = useState<HeaderData | null>();
    
    useEffect(() => {
        fetchHeaderData();

    }, []);

    function fetchHeaderData() {
        axios.create({
            baseURL: 'http://localhost:8080/api',
            headers: {
              'Content-Type': 'application/json',
            },
            responseType: "json"
        }).get(`/users/profile`, {
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

    function navigateToLogin() {
        window.location.assign(`/login`);
    }

    function navigateToAddWorkout() {
        window.location.assign(`/upload`);
    }



    function exit() {
        localStorage.removeItem('jwtToken');
        //setIsAuthenticated(true);
        window.location.assign(`/`);
    }

    if(headerData != null) {
        return(
        <div className="bg-block_background h-[50px] rounded shadow-sm sticky top-0 z-[1001]">
            <div className="max-w-[1200px] ml-auto mr-auto flex">
                <div className="flex flex-row justify-between w-[100%] items-center">
                    <img src={"https://free-png.ru/wp-content/uploads/2021/07/free-png.ru-53.png"} width={50} height={50} className="cursor-pointer" onClick={navigateToDashboard} />
                    
                    
                        <ul className="flex items-center space-x-4">
                        <li className="text-white">
                            <div className="relative group">
                                <button className="py-2 px-4 bg-gray-700 rounded-md">
                                    Меню
                                </button>
                                <ul className="absolute hidden group-hover:block mt-2 w-48 bg-white shadow-lg rounded-md">
                                    <li className="border-b">
                                    <a href="/profile" className="block px-4 py-2 hover:bg-gray-100">Мой профиль</a>
                                    </li>
                                    <li className="border-b">
                                    <a href="/settings" className="block px-4 py-2 hover:bg-gray-100">Настройки</a>
                                    </li>
                                    <li>
                                    <a href="/logout" className="block px-4 py-2 hover:bg-gray-100">Выйти</a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        </ul>
                    

                </div>
                <button onClick={exit}>Exit</button>
                <button className="text-4xl text-text hover:text-secondary_hover_button duration-100"onClick={navigateToAddWorkout}>+</button>
            </div>
        </div>
        )
    } else {
        return(
        <div className="bg-block_background h-[50px] rounded shadow-sm sticky top-0 z-[1001]">
            <div className="max-w-[1200px] ml-auto mr-auto flex">
                <div className="flex flex-row justify-between w-[100%] items-center">
                    <img src={"https://free-png.ru/wp-content/uploads/2021/07/free-png.ru-53.png"} width={50} height={50} className="cursor-pointer" />

                    <button className="bg-primary text-main_text_button py-1 px-4 rounded hover:bg-primary_hover_button duration-100 h-[35px]" onClick={navigateToLogin}>Войти</button>
                </div>
            </div>
        </div>
        )
    }
}