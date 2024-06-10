import { useState, useEffect } from "react";
import { getPost } from "../../services/postService";
import API from '../../services/API';
import ProfilePicture from "../ProfilePicture";
import WorkoutPostLike from "../WorkoutPostLike";
import { useNavigate } from "react-router"

type WorkoutPostProps = {
    workoutId: string | undefined
}

type UsualTime = {
    hours: string,
    minutes: string,
    seconds: string
}

type WorkoutLikeDto = {
    isLike: boolean,
    countLikes: string
}

type WorkoutPost = {
    workoutId: string,
    userId:string,
    username: string,
    name: string,
    typeSport: string,
    totalDistance: string,
    totalTime: UsualTime,
    totalAscent: string,
    startTime: string,
    likes: WorkoutLikeDto
}

export default function WorkoutPost(props: WorkoutPostProps) {
    const [workoutPost, setWorkoutPost] = useState<WorkoutPost>();
    const navigate = useNavigate();

    useEffect(() => {
        loadPost();

    }, []);

    /* сделать потом вот так
    const fetchWorkoutPost = async () => {
        try {
            const response = await API.get<WorkoutPost>(`/posts/${props.workoutId}`);
            setWorkoutPost(response.data);
        } catch (error) {
            console.error('Ошибка при загрузке данных:', error);
        }
    };
    */ 

    function loadPost() {
        getPost(props.workoutId)
        .then(response => {
            //console.log(response.data)
            setWorkoutPost(response.data)
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 404) {
                //alert("Not found");
            }
            else if(error.response.status != 200) {
                
            }
        })
    }

    function navigateToUserPage() {
        navigate(`/users/${workoutPost?.userId}`);
    }

    function navigateToWorkoutPage() {
        navigate(`/workouts/${workoutPost?.workoutId}`);
    }

    if(!setWorkoutPost) {
        return(<></>)
    }

    return(
    <div className=" bg-block_background p-5 rounded shadow-md text-text ">
        <div className="flex flex-row items-left">
           {workoutPost?.userId && <ProfilePicture userId={workoutPost?.userId} size={45} clickable={true} /> }
            <div className="flex flex-col ml-5">
                <div className="font-medium flex">
                    <p className="cursor-pointer hover:text-secondary duration-100" onClick={navigateToUserPage}>{workoutPost?.username}</p>
                </div>
                <p className="text-sm">{workoutPost?.startTime}</p>
                <p className="font-semibold text-2xl mt-2 cursor-pointer hover:text-secondary duration-100" onClick={navigateToWorkoutPage}>{workoutPost?.name}</p>
                <div className="flex flex-row gap-10 mt-3">
                    <div className="">
                        <p className="text-sm">Расстояние</p>
                        <p className="text-xl font-medium">{workoutPost?.totalDistance} км</p>
                    </div>
                    
                    <div className="">
                        <p className="text-sm">Набор высоты</p>
                        <p className="text-xl font-medium">{workoutPost?.totalAscent} м</p>
                    </div>

                    <div className="">
                        <p className="text-sm">Время</p>
                        <p className="text-xl font-medium">
                            {workoutPost?.totalTime.hours != "0" && (
                                <>
                                    {workoutPost?.totalTime.hours}ч.&nbsp;
                                </>
                            )}
                            {workoutPost?.totalTime.minutes != "0" && (
                                <>
                                    {workoutPost?.totalTime.minutes}м.&nbsp;
                                </>
                            )}
                            {workoutPost?.totalTime.seconds}с.  </p>
                    </div>
                </div>
            </div>
        </div>
        
        <div className="flex flex-col items-end">
            {workoutPost?.likes &&
             <WorkoutPostLike workoutId={workoutPost?.workoutId} isLike={workoutPost.likes.isLike} countLikes={workoutPost.likes.countLikes} />}
        </div>
    </div>

    )


}