import { useState, useEffect } from "react";
import { getPost } from "../../services/postService";

type WorkoutPostProps = {
    workoutId: string | undefined
}

export default function WorkoutPost(props: WorkoutPostProps) {

    useEffect(() => {
        loadPost();

    }, []);

    function loadPost() {
        getPost(props.workoutId)
        .then(response => {
            console.log(response.data)
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 404) {
                alert("Not found");
            }
            else if(error.response.status != 200) {
                alert("Internal error");
            }
        })
    }

    return(
    <div className="workout-post">
        
    </div>
    )


}