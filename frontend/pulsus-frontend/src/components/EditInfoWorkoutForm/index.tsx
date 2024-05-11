import "./style.css"
import InputField from "../InputField"
import { useForm, SubmitHandler } from "react-hook-form"
import { getValue } from "@testing-library/user-event/dist/utils"

type Inputs = {
    name: string,
    description: string,
    accessType: number,
    typeSport: string
}

type Props = {
    inputs: Partial<Inputs>,
    onInputChange: (inputs: Inputs) => void
}

export default function EditInfoWorkoutForm(props: Props) {
    const {
        register,
        handleSubmit,
        watch,
        formState: { errors }
    } = useForm<Inputs>({mode: 'onBlur'})

    const onSubmit: SubmitHandler<Inputs> = (data) => {
        console.log(data);
        sendWorkoutInfo(data);
    }

    function sendWorkoutInfo(data: Inputs) {
        props.onInputChange(data);
    }

    function handleChange(value: string) {
        console.log(value);
    }
    
    return(
    <form className="edit-info-workout-form" onSubmit={handleSubmit(onSubmit)}>
        <div className="info-workout-input">
            <InputField
                register={register}
                name="name"
                type="text"
                defaultValue={props.inputs.name}
                placeholder="Название тренировки"
                required="Введите название тренировки"
                minLength={{ value:2, message:"Минимум 2 символа" }}
                maxLength={{ value:50, message:"Максимум 50 символов" }}
                pattern={{ value:/^[^<>\\/]*$/ , message:"Без символов < > \ /" }}
                onChange={(value: string) => console.log(value)}
            />
            {errors.name && <div className="error-message">{errors.name.message}</div>}
        </div>

        <div className="info-workout-input">
            <InputField
                register={register}
                name="description"
                type="text"
                defaultValue={props.inputs.description}
                placeholder="Описание тренировки"
                maxLength={{ value:250, message:"Максимум 250 символов" }}
                pattern={{ value:/^[^<>\\/]*$/ , message:"Без символов < > \ /" }}
            />
            {errors.description && <div className="error-message">{errors.description.message}</div>}
        </div>

        <fieldset className="info-workout-checkboxs-accessType">
            <InputField
                register={register}
                name="accessType"
                type="radio"
                value="0"
                defaultChecked={props.inputs.accessType == 0 ? true: false}
            /> Публичный
            <InputField
                register={register}
                name="accessType"
                type="radio"
                value="1"
                defaultChecked={props.inputs.accessType == 1 ? true: false}
            /> Для подписчиков
            <InputField
                register={register}
                name="accessType"
                type="radio"
                value="2"
                defaultChecked={props.inputs.accessType == 2 ? true: false}
            /> Скрытый
            {errors.accessType && <div className="error-message">{errors.accessType.message}</div>}
        </fieldset>

        <button type="submit">Сохранить</button>
    </form>
    )
}