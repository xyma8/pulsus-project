import InputField from "../InputField"
import "./style.css"
import { useForm, SubmitHandler } from "react-hook-form"

enum Gender {
    male = "Мужской",
    female = "Женский"
}

type Inputs = {
    name: string,
    surname: string,
    email: string,
    login: string,
    password: string,
    passwordConfirm: string,
    source: string,
    gender: Gender
    acceptCB: boolean
}

export default function RegistrationForm() {
    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm<Inputs>()

    const onSubmit: SubmitHandler<Inputs> = (data) => console.log(data)

    return (
    <form onSubmit={handleSubmit(onSubmit)}>
        {errors?.name && (<div style={{ color: 'red', fontSize:'12px'}}>{errors.name.message}</div>)}
        <input type="text" placeholder="Имя" autoComplete="off"
            {
                ...register("name", {
                    required: "Обязательное поле",
                    minLength: {value:2, message:"Минимум 2" },
                    maxLength: {value:15, message:"Максимум 15"},
                    pattern: {value:/^[а-яА-ЯёЁ]+$/ , message:"Введите правильное имя"}
                })
            }
        />

        {errors?.surname && (<div style={{ color: 'red', fontSize:'12px'}}>{errors.surname.message}</div>)}
        <InputField 
            name="surname"
            type="text"
            placeholder="Фамилия"
            autoComplete="off"
            required="Обязательное поле"
            minLength={{ value:2, message:"Минимум 2" }}
            maxLength={{ value:20, message:"Максимум 20" }}
            pattern={{ value:/^[а-яА-ЯЁё]+(?:-[а-яА-ЯЁё]+)?$/ , message:"Введите правильную фамилию" }}
        />
    </form>
    )
}