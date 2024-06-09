import RegistrationForm from "../../components/RegistrationForm"
import "./style.css"
import { useEffect } from "react"
import { useNavigate } from "react-router"

export default function RegistrationPage() {
    //container mx-auto flex flex-col items-center h-screen
    const navigate = useNavigate();

    useEffect(() => {
        if(localStorage.getItem('jwtToken')) {
            window.location.assign(`/dashboard`);
        }

    }, []);
    
    function navigateLoginPage() {
        navigate('/login');
    }

    return(
    <div className="flex flex-col items-center h-screen py-20">
        <RegistrationForm/>
        <div className="flex flex-row">
            <p className="text-text px-1">Уже зарегистрированы?</p>
            <p className="underline text-text cursor-pointer hover:text-secondary duration-100" onClick={navigateLoginPage}>Войти</p>
        </div>
    </div>
    )
    //<button className="bg-primary text-main_text_button font-bold py-2 px-4 rounded">Уже зарегистрированы? Войти</button>
}