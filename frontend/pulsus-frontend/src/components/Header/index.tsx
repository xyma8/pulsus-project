import "./style.css"

type HeaderProps = {

}

export default function Header(props: HeaderProps) {

    return(
    <div className="bg-block_background h-[50px] rounded shadow-sm sticky top-0">
        <div className="container h-screen mx-auto flex flex-col">
            <img src={"https://free-png.ru/wp-content/uploads/2021/07/free-png.ru-53.png"} width={50} height={50} />
        </div>
    </div>
    )
}