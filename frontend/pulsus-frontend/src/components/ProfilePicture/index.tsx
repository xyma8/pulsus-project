import "./style.css"
import API from "../../utils/API";

type ProfilePictureProps = {
    type: boolean
}

export default function ProfilePicture(props: ProfilePictureProps) {

    function loadProfilePicture() {
        API.post("/auth/generateToken", data)
        .then(response => {
            props.onSuccessLogin(response.data.token);
        })
        .catch(error =>{
            console.error(error);
            if(error.response.status == 403) {
                alert("Incorrect login or password");
            }
            else if(error.response.status != 200) {
                alert("Internal error");
            }
        })
    }

    return(
    <div className="profile-picture">
        
    </div>
    )
}