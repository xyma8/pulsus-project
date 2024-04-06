import "./style.css"
import InputField from "../InputField"
import { useForm, SubmitHandler } from "react-hook-form"

type Inputs = {
    login: string,
    password: string
}

export default function AuthForm() {
    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm<Inputs>()

    const onSubmit: SubmitHandler<Inputs> = (data) => console.log(data)

    return(
    <form className="auth-form" onSubmit={handleSubmit(onSubmit)}>
        <div className="auth-input">
            <InputField 
                register={register}
                name="login"
                type="text"
                placeholder="Логин"
                required="Введите логин"
                minLength={{ value:2, message:"Минимум 4 символа" }}
                maxLength={{ value:24, message:"Максимум 24 символа" }}
                pattern={{ value:/^[a-zA-Z0-9_]+$/ , message:"Неправильный логин" }}
            />
            {errors.login && <div className="error-message">{errors.login.message}</div>}
        </div>

        <div className="auth-input">
            <InputField 
                register={register}
                name="password"
                type="password"
                placeholder="Пароль"
                required="Обязательное поле"
                minLength={{ value:8, message:"Минимум 8 символов" }}
                //pattern={{ value:/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+\\|[\]{};:'",.<>/?]).{8,}$/ , message:"Необходимо прописные, заглавные буквы, цифры и разрешенные символы" }}
            />
            {errors.password && <div className="error-message">{errors.password.message}</div>}
        </div>
    </form>
    
    )
}