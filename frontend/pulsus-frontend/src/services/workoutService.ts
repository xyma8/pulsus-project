import API from './API';

export function getWorkoutInfo(workoutId: string) {
    return null;
}

export function getWorkoutTrack(workoutId: string | undefined) {
    return API.get(`/workouts/${workoutId}/track`, {
        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('jwtToken'),
        }
    });
}