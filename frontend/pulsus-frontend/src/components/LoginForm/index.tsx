import "./style.css"
import InputField from "../InputField"
import { useForm, SubmitHandler } from "react-hook-form"
import API from "../../services/API";
import axios from "axios"
import { ToastContainer, toast } from 'react-toastify';

type Inputs = {
    login: string,
    password: string
}

type LoginFormProps = {
    onSuccessLogin: (token: string) => void;
}

export default function LoginForm(props: LoginFormProps) {
    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm<Inputs>()

    const onSubmit: SubmitHandler<Inputs> = (data) => {
        console.log(data)
        sendDataLogin(data);
    }

    //function onSubmit(data: Inputs): void {
     //   console.log(data);
     // }


    function sendDataLogin(data: Inputs) {
        axios.create({
            baseURL: 'http://localhost:8080/api',
            headers: {
              'Content-Type': 'application/json',
            },
            responseType: "json"
        }).post("/auth/generateToken", data)
        .then(response => {
            props.onSuccessLogin(response.data.token);
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 401) {
                toast.error('Неправильный логин или пароль!');

            }
            else if(error.response.status != 200) {
                
            }
        })
    }

    return(
    <form className="flex flex-col items-center bg-block_background p-8 rounded shadow-md space-y-5 min-w-[320px] max-w-[320px]" onSubmit={handleSubmit(onSubmit)}>
        <div className="w-full">
            <InputField 
                register={register}
                name="login"
                type="text"
                placeholder="Логин"
                required="Введите логин"
                //minLength={{ value:4, message:"Минимум 4 символа" }}
                //maxLength={{ value:24, message:"Максимум 24 символа" }}
                //pattern={{ value:/^[a-zA-Z0-9_]+$/ , message:"Неправильный логин" }}
            />
            {errors.login && <div className="text-error text-[14px]">{errors.login.message}</div>}
        </div>

        <div className="w-full">
            <InputField 
                register={register}
                name="password"
                type="password"
                placeholder="Пароль"
                required="Обязательное поле"
                //minLength={{ value:8, message:"Минимум 8 символов" }}
                //pattern={{ value:/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+\\|[\]{};:'",./?]).{8,}$/ , message:"Необходимо прописные, заглавные буквы, цифры и разрешенные символы" }}
            />
            {errors.password && <div className="text-error text-[14px]">{errors.password.message}</div>}
        </div>

        <button type="submit" className="bg-secondary text-main_text_button font-bold py-2 px-4 rounded hover:bg-secondary_hover_button duration-100">Войти</button>
    </form>
    
    )
}