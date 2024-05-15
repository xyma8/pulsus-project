import "./style.css"
import InputField from "../InputField"
import { useForm, SubmitHandler } from "react-hook-form"
import API from "../../services/API";

type Inputs = {
    name: string,
    surname: string,
    email: string,
    login: string,
    password: string,
    passwordConfirm: string,
    source: string,
    acceptCB: boolean
}

type UserData = {
    name: string,
    surname: string,
    email: string,
    login: string,
    password: string
}

export default function RegistrationForm() {
    const {
        register,
        handleSubmit,
        formState: { errors },
        getValues
    } = useForm<Inputs>({mode: 'onBlur'})

    const onSubmit: SubmitHandler<Inputs> = (data) => {
        console.log(data);
        const user_data = {
            name: data.name,
            surname: data.surname,
            email: data.email,
            login: data.login,
            password: data.password
        }
        sendDataRegistration(user_data);
    }

    function sendDataRegistration(data: UserData) {
        API.post("/users/signup", data)
        .then(response => {
            alert('Registration success!');
        })
        .catch(error => {
            if(error.response.status==409) {
                alert("This login or email is already taken!");
            }
            else if(error.response.status != 200) {
                alert("Error!");
            }
        })
    }

    return (
    <form className="registration-form" onSubmit={handleSubmit(onSubmit)}>
        <div className="registration-input">
            <input type="text" placeholder="Имя" autoComplete="off"
                {
                    ...register("name", {
                        required: "Обязательное поле",
                        minLength: {value:2, message:"Минимум 2" },
                        maxLength: {value:20, message:"Максимум 20"},
                        pattern: {value:/^[а-яА-ЯёЁ]+$/ , message:"Введите правильное имя"}
                    })
                }
            />
            {errors.name && <div className="error-message">{errors.name.message}</div>}
        </div>

        <div className="registration-input">
            <InputField 
                register={register}
                name="surname"
                type="text"
                placeholder="Фамилия"
                required="Обязательное поле"
                minLength={{ value:2, message:"Минимум 2" }}
                maxLength={{ value:20, message:"Максимум 20" }}
                pattern={{ value:/^[а-яА-ЯЁё]+(?:-[а-яА-ЯЁё]+)?$/ , message:"Введите правильную фамилию" }}
            />
            {errors.surname && <div className="error-message">{errors.surname.message}</div>}
        </div>
        
        <div className="registration-input">
            <InputField 
                register={register}
                name="email"
                type="text"
                placeholder="E-mail"
                required="Обязательное поле"
                maxLength={{ value:254, message:"Превышена длина E-mail" }}
                pattern={{ value:/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/ , message:"Неправильный email" }}
            />
            {errors.email && <div className="error-message">{errors.email.message}</div>}
        </div>

        <div className="registration-input">
            <InputField 
                register={register}
                name="login"
                type="text"
                placeholder="Логин"
                required="Обязательное поле"
                minLength={{ value:4, message:"Минимум 4 символа" }}
                maxLength={{ value:24, message:"Максимум 24 символа" }}
                pattern={{ value:/^[a-zA-Z0-9_]+$/ , message:"Неправильный логин" }}
            />
            {errors.login && <div className="error-message">{errors.login.message}</div>}
        </div>

        <div className="registration-input">
            <InputField 
                register={register}
                name="password"
                type="password"
                placeholder="Пароль"
                required="Обязательное поле"
                minLength={{ value:8, message:"Минимум 8 символов" }}
                pattern={{ value:/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+\\|[\]{};:'",./?]).{8,}$/ , message:"Необходимо прописные, заглавные буквы, цифры и разрешенные символы" }}
            />
            {errors.password && <div className="error-message">{errors.password.message}</div>}
        </div>

        <div className="registration-input">
            <InputField 
                register={register}
                name="passwordConfirm"
                type="password"
                placeholder="Подтверждение пароля"
                required="Обязательное поле"
                validate={(value) => value === getValues('password') || "Пароли не совпадают"}
            />
            {errors.passwordConfirm && <div className="error-message">{errors.passwordConfirm.message}</div>}
        </div>

        <fieldset>
                <legend>Как вы узнали о сайте?:</legend>
                <select {...register("source")}>
                    <option value="">--Выберите--</option>
                    <option value="search">Поисковые системы</option>
                    <option value="social">Социальные сети</option>
                    <option value="friends">Рекомендации друзей</option>
                    <option value="ads">Реклама</option>
                    <option value="blogs">Статьи и блоги</option>
                </select>
        </fieldset>

        <label className="label-checkbox">
                Принимаю правила сайта: <InputField register={register} required="Подтвердите" type="checkbox" name="acceptCB"/>
                {errors.acceptCB && <div className="error-message">{errors.acceptCB.message}</div>}
        </label>

        <button type="submit">Регистрация</button>
    </form>
    )
}