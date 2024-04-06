import AuthForm from "../../components/AuthForm"
import "./style.css"

export default function AuthPage() {
    return(
        <div className="auth-page">
            <AuthForm/>
            <button>Нет аккаунта? Создать</button>
        </div>
    )
}
