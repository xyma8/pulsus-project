import "./style.css"
import Cookies from 'js-cookie'
import { useNavigate } from 'react-router-dom';
import LoginForm from "../../components/LoginForm"
import { useState, useEffect } from "react";

export default function LoginPage() {
    const navigate = useNavigate();

    useEffect(() => {
        if(localStorage.getItem('jwtToken')) {
            window.location.assign(`/dashboard`);
        }

    }, []);

    function successLogin(token: string) {
        console.log(token); //delete
        localStorage.setItem('jwtToken', token);
        navigate('/dashboard');
    }

    function navigateRegistrationPage() {
        navigate('/registration');
    }

    return(
        <div className="flex flex-col items-center h-screen py-20">
            <LoginForm onSuccessLogin={successLogin}/>

            <div className="flex flex-row">
                <p className="text-text px-1">Нет аккаунта?</p>
                <p className="underline text-text cursor-pointer hover:text-secondary duration-100" onClick={navigateRegistrationPage}>Создать</p>
            </div>
        </div>
    )
}
