import "./style.css"
import LoginForm from "../../components/LoginForm"

export default function LoginPage() {

    function successLogin(token: string) {

    }

    return(
        <div className="login-page">
            <LoginForm onSuccessLogin={successLogin}/>
            <button>Нет аккаунта? Создать</button>
        </div>
    )
}
