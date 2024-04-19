import "./style.css"
import API from "../../utils/API";

type ProfilePictureProps = {
    type: boolean
}

export default function ProfilePicture(props: ProfilePictureProps) {

    
    function loadProfilePicture() {
        API.post("/auth/generateToken", {
            headers: {
                Authorization: 'Bearer '+ localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            console.log(response.data.path);
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
    
    return(
    <div className="profile-picture">
        
    </div>
    )
}