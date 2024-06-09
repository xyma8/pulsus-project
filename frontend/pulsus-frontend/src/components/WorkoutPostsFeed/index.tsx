import { useState, useEffect, useRef } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import API from "../../services/API";
import WorkoutPost from '../WorkoutPost';

type WorkoutPostsFeedProps = {
    userId?: string,
    feed?: boolean
    size: number
}

export default function WorkoutPostsFeed(props: WorkoutPostsFeedProps) {
    const [workoutIds, setWorkoutIds] = useState<number[]>([]);
    //const [page, setPage] = useState(1);
    //const [hasMore, setHasMore] = useState(true);
    const hasMore = useRef(true);
    const pageRef = useRef(0);

    useEffect(() => {
        fetchMoreWorkouts();
    }, []);

    function fetchMoreWorkouts() {
        console.log(props.userId);
        pageRef.current += 1;
        var apiRequest: string = `/posts/${pageRef.current}/${props.size}`

        if(props.userId) {
            apiRequest = `/posts/${props.userId}/${pageRef.current}/${props.size}`
        }
        if(props.feed) {
            apiRequest = `/posts/feed/${pageRef.current}/${props.size}`
        }

        API.get(apiRequest, {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            //console.log(response.data)
            setWorkoutIds(prev => [...prev, ...response.data.workoutIds]);
            //setPage(prev => prev + 1);
            
            //setHasMore(response.data.workoutIds.length > 0);
            if(response.data.workoutIds.length <= 0) {
                hasMore.current = false;
            }
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 403) {
                alert("Error");
            }
            else if(error.response.status != 200) {
                alert("Internal error");
            }
        })
    }
    //сделать пропсами loader endMessage
    return(
    <div className="text-text">
        <InfiniteScroll
            dataLength={workoutIds.length}
            next={fetchMoreWorkouts}
            hasMore={hasMore.current}
            loader={<h4>Loading...</h4>}
            endMessage={<p>No more posts</p>}>

            {workoutIds.map(id => (
                <WorkoutPost workoutId={id.toString()}/>
            ))}
        </InfiniteScroll>
    </div>
    )
}




