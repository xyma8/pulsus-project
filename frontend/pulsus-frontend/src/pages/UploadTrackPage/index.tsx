import UploadTrackFile from "../../components/UploadTrackFile";


export default function UploadTrackPage() {

    return(
    <div className="flex flex-col bg-block_background shadow-md rounded text-text p-3">
        <p className="text-2xl font-bold mb-5">Загрузить тренировку из файла</p>
        <UploadTrackFile />
        <p className="text-l mt-3">Выберите файл в формате .fit размером не более 25 МБ.</p>

    </div>
    )
}