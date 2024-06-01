import API from './API';

export function getPost(workoutId: string | undefined) {
    return API.get(`/posts/${workoutId}`, {
        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('jwtToken'),
        }
    });
}