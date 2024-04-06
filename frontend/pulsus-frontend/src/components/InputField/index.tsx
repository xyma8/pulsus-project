import "./style.css"
import { UseFormRegister, FieldValues, RegisterOptions } from "react-hook-form"

type InputProps = {
    register: any,
    name: string,
    type: string,
    value?: string | number,
    placeholder?: string,
    autoComplete?: string,
    defaultChecked?: boolean,
    required?: string,
    minLength?: { value: number, message: string }
    maxLength?: { value: number, message: string }
    pattern?: { value: RegExp, message: string }
    validate?: RegisterOptions['validate']
}

export default function InputField(props: InputProps) {
    const {
        register,
        name,
        type,
        value,
        placeholder,
        autoComplete,
        defaultChecked,
        required, 
        minLength, 
        maxLength, 
        pattern,
        validate } = props;

    if(type === 'ckeckbox') {
        return(
            <input
                type={type}
                defaultChecked={defaultChecked}
                {
                    ...register(name, {
                        required,
                        validate
                    })
                }
            />
        )
    }

    else if(type == 'radio') {
        return(
            <input
                type={type}
                value={value}
                defaultChecked={defaultChecked}
                {
                    ...register(name, {
                        required,
                        validate
                    })
                }
            />
        )
    }

    else {
        return(
            <input
                type={type}
                placeholder={placeholder} 
                autoComplete={autoComplete || "off"}
                value={value}
                {
                    ...register(name, {
                        required,
                        minLength,
                        maxLength,
                        pattern,
                        validate
                    })
                }
            />
        )
    }
}