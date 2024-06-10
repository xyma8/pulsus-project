import { useNavigate } from "react-router"

export default function NotFoundPage() {
    const navigate = useNavigate();

    return(
    <div className="flex flex-col items-center text-text">
        <p className="text-4xl font-bold animate-bounce">404</p>
        <p className="text-3xl font-bold mt-2">Страница не найдена :(</p>
        <p className="hover:text-secondary_hover_button duration-100 cursor-pointer mt-5" onClick={() => navigate('/dashboard')}>На главную</p>
    </div>
    )
}