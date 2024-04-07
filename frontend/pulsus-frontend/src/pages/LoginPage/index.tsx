import "./style.css"
import LoginForm from "../../components/LoginForm"

export default function LoginPage() {
    return(
        <div className="login-page">
            <LoginForm/>
            <button>Нет аккаунта? Создать</button>
        </div>
    )
}
