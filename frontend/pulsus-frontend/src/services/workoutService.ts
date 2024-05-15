import API from './API';

export function getWorkoutInfo(workoutId: string) {
    return API.get(`/workouts/${workoutId}`, {
        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('jwtToken'),
        }
    });
}

export function getWorkoutTrack(workoutId: string | undefined) {
    return API.get(`/workouts/${workoutId}/track`, {
        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('jwtToken'),
        }
    });
}

export function getWorkoutTrackSummary(workoutId: string | undefined) {
    return API.get(`/workouts/${workoutId}/trackSummary`, {
        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('jwtToken'),
        }
    });
}