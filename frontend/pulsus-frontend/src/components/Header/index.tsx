import "./style.css"

type HeaderProps = {

}

export default function Header(props: HeaderProps) {

    return(
    <div className="bg-block_background h-[50px] rounded shadow-sm ">
        <div className="max-w-[1200px] h-screen ml-auto mr-auto">
            <img src={"https://free-png.ru/wp-content/uploads/2021/07/free-png.ru-53.png"} width={50} height={50} />
        </div>
    </div>
    )
}