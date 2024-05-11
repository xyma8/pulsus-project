import "./style.css"
import { UseFormRegister, FieldValues, RegisterOptions } from "react-hook-form"

type InputProps = {
    register: any,
    name: string,
    type: string,
    defaultValue?: string | number,
    placeholder?: string,
    autoComplete?: string,
    value?: string,
    defaultChecked?: boolean,
    required?: string,
    minLength?: { value: number, message: string }
    maxLength?: { value: number, message: string }
    pattern?: { value: RegExp, message: string }
    onChange?: (value: string) => void
    validate?: RegisterOptions['validate']
}

export default function InputField(props: InputProps) {
    const {
        register,
        name,
        type,
        defaultValue,
        placeholder,
        autoComplete,
        value,
        defaultChecked,
        required, 
        minLength, 
        maxLength, 
        pattern,
        onChange,
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

    else if(type === 'radio') {
        return(
            <input
                type={type}
                defaultChecked={defaultChecked}
                value={value}
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
                defaultValue={defaultValue || ""}
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