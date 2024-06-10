import "./style.css"
import { UseFormRegister, FieldValues, RegisterOptions } from "react-hook-form"

type InputFieldProps = {
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

export default function InputField(props: InputFieldProps) {
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
                className="form-radio h-4 w-4 text-blue-600 transition duration-100 ease-in-out cursor-pointer"
            />
        )
    }
    else if(type === 'password') {
        return(
        <div>
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
                className="py-2 rounded border border-solid hover:border-secondary duration-100 focus:outline-none focus:ring-2 focus:ring-primary w-full" //max-w-md
            />
            
        </div>
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
                className="py-2 rounded border border-solid hover:border-secondary duration-100 focus:outline-none focus:ring-2 focus:ring-primary w-full" //max-w-md
            />
        )
    }
}