import "./style.css"
import { useState, useEffect } from "react";
import API from "../../services/API";
import ProfilePicture from "../ProfilePicture";
import { useAuth } from "../../contexts/AuthContext";
import axios from "axios"

type HeaderProps = {

}

type HeaderData = {
    id: string
    name: string,
    surname: string,
    login: string
}

export default function Header(props: HeaderProps) {
    //const { isAuthenticated, setIsAuthenticated } = useAuth();
    const [headerData, setHeaderData] = useState<HeaderData | null>();
    const [isOpen, setIsOpen] = useState(false);

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

    function navigateToUserPage() {
        window.location.assign(`/users/${headerData?.id}`);
    }

    function navigateToUploadPage() {
        window.location.assign('/upload');
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

    const openMenu = () => {
        setIsOpen(true);
    };
    
    const closeMenu = () => {
        setIsOpen(false);
    };

    if(headerData != null) {
        return(
        <div className="bg-block_background h-[50px] rounded shadow-sm sticky top-0 z-[1001]">
            <div className="max-w-[1200px] ml-auto mr-auto flex ">
                <div className="flex flex-row justify-between w-[100%] items-center">
                    <img src={"https://upload.wikimedia.org/wikipedia/commons/6/60/Logo-logosu.png"} width={90} height={100} className="cursor-pointer" onClick={navigateToDashboard} />
                    
                    
                    <div className="relative inline-block text-left text-text" 
                    onMouseEnter={openMenu}
                    >
                        <div>
                            <button
                            type="button"
                            className="inline-flex justify-center w-full rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-sm font-medium hover:bg-gray-50 focus:outline-none"
                            onClick={navigateToUserPage}
                            >
                            Профиль
                            </button>
                        </div>

                        {isOpen && (
                            <div className="origin-top-right absolute right-0 mt-2 w-56 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5" onMouseLeave={closeMenu}>
                            <div className="py-1" role="menu" aria-orientation="vertical" aria-labelledby="options-menu">
                                <button
                                className="block px-4 py-2 text-sm hover:bg-gray-100 w-full text-left"
                                role="menuitem"
                                onClick={navigateToUserPage}
                                >
                                Мой профиль
                                </button>
                                <button
                                className="block px-4 py-2 text-sm hover:bg-gray-100 w-full text-left"
                                role="menuitem"
                                onClick={navigateToUploadPage}
                                >
                                Загрузить тренировку
                                </button>
                                <button
                                className="block px-4 py-2 text-sm hover:bg-gray-100 w-full text-left"
                                role="menuitem"
                                onClick={navigateToUploadPage}
                                >
                                Найти друзей
                                </button>
                                <button
                                className="block px-4 py-2 text-sm hover:bg-gray-100 w-full text-left"
                                role="menuitem"
                                onClick={exit}
                                >
                                Выход
                                </button>
                            </div>
                            </div>
                        )}
                    </div>
                    

                </div>

                <button className="text-4xl text-text hover:text-secondary_hover_button duration-100 ml-3 mb-1"onClick={navigateToAddWorkout}>+</button>
            </div>
        </div>
        )
    } else {
        return(
        <div className="bg-block_background h-[50px] rounded shadow-sm sticky top-0 z-[1001]">
            <div className="max-w-[1200px] ml-auto mr-auto flex">
                <div className="flex flex-row justify-between w-[100%] items-center">
                    <img src={"https://upload.wikimedia.org/wikipedia/commons/6/60/Logo-logosu.png"} width={90} height={100} className="cursor-pointer" />

                    <button className="bg-primary text-main_text_button py-1 px-4 rounded hover:bg-primary_hover_button duration-100 h-[35px]" onClick={navigateToLogin}>Войти</button>
                </div>
            </div>
        </div>
        )
    }
}