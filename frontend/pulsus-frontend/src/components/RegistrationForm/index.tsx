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
        formState: { errors },
        getValues
    } = useForm<Inputs>({mode: 'onBlur'})

    const onSubmit: SubmitHandler<Inputs> = (data) => console.log(data)

    return (
    <form className="form-registration"onSubmit={handleSubmit(onSubmit)}>
        <div className="input-registration">
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
            {errors.name && <div className="error-message">{errors.name.message}</div>}
        </div>

        <div className="input-registration">
            <InputField 
                register={register}
                name="surname"
                type="text"
                placeholder="Фамилия"
                autoComplete="off"
                required="Обязательное поле"
                minLength={{ value:2, message:"Минимум 2" }}
                maxLength={{ value:20, message:"Максимум 20" }}
                pattern={{ value:/^[а-яА-ЯЁё]+(?:-[а-яА-ЯЁё]+)?$/ , message:"Введите правильную фамилию" }}
            />
            {errors.surname && <div className="error-message">{errors.surname.message}</div>}
        </div>
        
        <div className="input-registration">
            <InputField 
                register={register}
                name="email"
                type="text"
                placeholder="E-mail"
                autoComplete="off"
                required="Обязательное поле"
                maxLength={{ value:254, message:"Превышена длина E-mail" }}
                pattern={{ value:/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/ , message:"Неправильный email" }}
            />
            {errors.email && <div className="error-message">{errors.email.message}</div>}
        </div>

        <div className="input-registration">
            <InputField 
                register={register}
                name="login"
                type="text"
                placeholder="Логин"
                autoComplete="off"
                required="Обязательное поле"
                minLength={{ value:4, message:"Минимум 4 символа" }}
                maxLength={{ value:24, message:"Максимум 24 символа" }}
                pattern={{ value:/^[a-zA-Z0-9_]+$/ , message:"Неправильный логин" }}
            />
            {errors.login && <div className="error-message">{errors.login.message}</div>}
        </div>

        <div className="input-registration">
            <InputField 
                register={register}
                name="password"
                type="password"
                placeholder="Пароль"
                autoComplete="off"
                required="Обязательное поле"
                minLength={{ value:8, message:"Минимум 8 символов" }}
                pattern={{ value:/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+\\|[\]{};:'",.<>/?]).{8,}$/ , message:"Необходимо прописные, заглавные буквы, цифры и разрешенные символы" }}
            />
            {errors.password && <div className="error-message">{errors.password.message}</div>}
        </div>

        <div className="input-registration">
            <InputField 
                register={register}
                name="passwordConfirm"
                type="password"
                placeholder="Подтверждение пароля"
                autoComplete="off"
                required="Обязательное поле"
                validate={(value) => value == getValues('password') || "Пароли не совпадают"}
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

        <fieldset>
                <legend>Выберите пол:</legend>

                <div>                  
                <label>
                    <InputField register={register} type="radio" name="gender" value="0" defaultChecked={true}/>
                    Мужской
                </label>
                <br/>
                <label>
                    <InputField register={register} type="radio" name="gender" value="1"/>
                    Женский
                </label>
                </div>
        </fieldset>

        <label className="label-checkbox">
                Принимаю правила сайта: <InputField register={register} required="Подтвердите" type="checkbox" name="acceptCB"/>
                {errors.acceptCB && <div className="error-message">{errors.acceptCB.message}</div>}
        </label>

        <button type="submit" >Регистрация</button>
    </form>
    )
}