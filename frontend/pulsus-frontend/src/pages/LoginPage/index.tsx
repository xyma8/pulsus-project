import "./style.css"
import Cookies from 'js-cookie'
import { useNavigate } from 'react-router-dom';
import LoginForm from "../../components/LoginForm"

export default function LoginPage() {
    const navigate = useNavigate();

    function successLogin(token: string) {
        console.log(token); //delete
        localStorage.setItem('jwtToken', token);
        navigate('/dashboard');
    }

    return(
        <div className="login-page">
            <LoginForm onSuccessLogin={successLogin}/>
            <button>Нет аккаунта? Создать</button>
        </div>
    )
}
