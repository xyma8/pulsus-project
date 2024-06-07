import API from '../../services/API';
import { useState, useEffect } from 'react';

type WorkoutPostLikeProps = {
    workoutId: string,
    isLike: boolean,
    countLikes: string
}

type WorkoutPostLike = {
    isLike: boolean,
    countLikes: string
}

export default function WorkoutPostLike(props: WorkoutPostLikeProps) {
    const [likes, setLikes] = useState<WorkoutPostLike>();

    useEffect(() => {
        const initialLikesData: WorkoutPostLike = {
            isLike: props.isLike,
            countLikes: props.countLikes
        }
        setLikes(initialLikesData)

    }, []);

    function clickLike() {
        API.post(`/workoutsLikes/${props?.workoutId}/change`, null, { //при post запросе обязательно передавать тело вторым параметром, либо null если оно не принимается
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            console.log(response.data);
            setLikes(response.data)
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 404) {
                alert("Workout not found");
            }
            else if(error.response.status != 200) {
                alert("Internal error");
            }
        })
    }

    if(!setLikes) {
        return(<></>)
    }
    
    //shadow-red-500
    return(
    <div className="">

        {likes?.isLike ?
            <button onClick={clickLike} className="bg-secondary text-text font-bold py-2 px-4 rounded hover:bg-secondary_hover_button duration-100 shadow">
                👍{likes?.countLikes}
            </button>
            :
             <button onClick={clickLike} className="bg-gray-100 text-text font-bold py-2 px-4 rounded hover:bg-white duration-100 shadow">
                👍{likes?.countLikes}
            </button>
        }
    </div>
    )
}