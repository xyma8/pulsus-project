import "./style.css"
import { useForm, SubmitHandler } from "react-hook-form"
import API from "../../utils/API";

type Inputs = {
    name: string,
    description: string
}

export default function EditInfoWorkoutForm() {
    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm<Inputs>()

    const onSubmit: SubmitHandler<Inputs> = (data) => {
        console.log(data)
        sendWorkoutInfo(data);
    }

    function sendWorkoutInfo(data: Inputs) {

    }
    
    return(
    <div className="edit-workout-form">

    </div>
    )
}