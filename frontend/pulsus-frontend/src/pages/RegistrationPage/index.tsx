import RegistrationForm from "../../components/RegistrationForm"
import "./style.css"

export default function RegistrationPage() {

    return(
    <div className="registration-page">
        <RegistrationForm/>
        <button>Уже зарегистрированы? Войти</button>
    </div>
    )
}