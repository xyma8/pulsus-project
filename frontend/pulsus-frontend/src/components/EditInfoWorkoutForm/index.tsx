import "./style.css"
import InputField from "../InputField"
import { useForm, SubmitHandler } from "react-hook-form"
import { getValue } from "@testing-library/user-event/dist/utils"
import API from "../../services/API";
import { ToastContainer, toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import { da } from "date-fns/locale";

type Inputs = {
    name: string,
    description: string,
    accessType: number,
    typeSport: string
}

type EditInfoWorkoutFormProps = {
    workoutId: string,
    inputs: Partial<Inputs>,
    onInputChange: (inputs: Inputs) => void
}

export default function EditInfoWorkoutForm(props: EditInfoWorkoutFormProps) {
    const {
        register,
        handleSubmit,
        watch,
        formState: { errors }
    } = useForm<Inputs>({mode: 'onBlur'})

    const onSubmit: SubmitHandler<Inputs> = (data) => {
        console.log(data);
        var newInputs: Inputs = {
            name: data.name,
            description: data.description,
            accessType: data.accessType,
            typeSport: "CYCLING" //del
        }

        updateWorkoutInfo(newInputs);
    }

    const navigate = useNavigate();

    function sendWorkoutInfo(data: Inputs) {
        props.onInputChange(data);
    }

    function handleChange(value: string) {
        console.log(value);
    }

    function updateWorkoutInfo(data: Inputs) {
        API.post(`/workouts/${props.workoutId}/edit`, data, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken'),
            }
        })
        .then(response => {
            console.log(response.data);
            toast.success('Информация успешно изменена!');
            navigate(`/workouts/${props.workoutId}`)
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 404) {
                navigate("*")
            }
        })
    }
    
    return(
    <form className="flex flex-col bg-block_background shadow-md rounded text-text p-3" onSubmit={handleSubmit(onSubmit)}>
        <div className="info-workout-input">
            <InputField
                register={register}
                name="name"
                type="text"
                defaultValue={props.inputs.name}
                placeholder="Название тренировки"
                required="Введите название тренировки"
                minLength={{ value:2, message:"Минимум 2 символа" }}
                maxLength={{ value:50, message:"Максимум 30 символов" }}
                pattern={{ value:/^[^<>\\/]*$/ , message:"Без символов < > \ /" }}
                onChange={(value: string) => console.log(value)}
            />
            {errors.name && <div className="text-error text-[14px]">{errors.name.message}</div>}
        </div>

        <div className="mt-5">
            <InputField
                register={register}
                name="description"
                type="text"
                defaultValue={props.inputs.description}
                placeholder="Описание тренировки"
                maxLength={{ value:250, message:"Максимум 250 символов" }}
                pattern={{ value:/^[^<>\\/]*$/ , message:"Без символов < > \ /" }}
            />
            {errors.description && <div className="text-error text-[14px]">{errors.description.message}</div>}
        </div>

        <fieldset className="flex flex-col mt-5 text-text">
        <legend className="text-lg font-medium mb-1">Тип доступа:</legend>
            <div className="flex items-center">
                <InputField
                    register={register}
                    name="accessType"
                    type="radio"
                    value="0"
                    defaultChecked={props.inputs.accessType == 0 ? true: false}
                /> <p className="ml-3 ">Публичный</p>
            </div>
            <div className="flex items-center mt-2">
                <InputField
                    register={register}
                    name="accessType"
                    type="radio"
                    value="1"
                    defaultChecked={props.inputs.accessType == 1 ? true: false}
                /> <p className="ml-3 ">Для подписчиков</p>
            </div>
            <div className="flex items-center mt-2">
                <InputField
                    register={register}
                    name="accessType"
                    type="radio"
                    value="2"
                    defaultChecked={props.inputs.accessType == 2 ? true: false}
                /> <p className="ml-3 ">Скрытый</p>
            </div>
            {errors.accessType && <div className="error-message">{errors.accessType.message}</div>}
        </fieldset>

        <button type="submit" className="bg-secondary text-main_text_button font-bold py-2 px-4 rounded hover:bg-secondary_hover_button duration-100 max-w-[200px] mt-5 ">Сохранить</button>
    </form>
    )
}