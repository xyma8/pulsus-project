import "./style.css"
import { useForm } from "react-hook-form"

type InputProps = {
    name: string,
    type: string,
    placeholder: string,
    autoComplete: string,
    required?: string,
    minLength?: { value: number, message: string }
    maxLength?: { value: number, message: string }
    pattern?: { value: RegExp, message: string }
}

export default function InputField(props: InputProps) {
    const {
        register,
        formState: { errors }
    } = useForm({ mode: 'onBlur'})

    const {
        name,
        type,
        placeholder,
        autoComplete,
        required, 
        minLength, 
        maxLength, 
        pattern, } = props;

    return(
        
        <input type={type} placeholder={placeholder} autoComplete={autoComplete}
        {
            ...register(name, {
                required,
                minLength,
                maxLength,
                pattern
            })
        }
        />
    )
}