function doSignIn(event) {
    event.preventDefault(); // Prevent form submission

    const authSysUserPath = "/authenticate"; 
    const userName = document.getElementById('userName').value;
    const passPhrase = document.getElementById('password').value;

    // Validate inputs
    if (!userName || !passPhrase) {
        alert("User name and password cannot be empty."); 
        return; 
    }

    // Store the username in session storage
    if (typeof sessionStorage !== "undefined") {
        sessionStorage.setItem("userName", userName);
    } else {
        console.warn("SessionStorage is not available.");
    }

    // Payload for the request
    const payload = {
        "userName": userName,
        "passKey": passPhrase
    };

    // Axios request configuration
    const config = {
        method: 'post',
        url: "http://127.0.0.1:8085/ptt/api/users" + authSysUserPath,
        data: payload
    };

    // Make the HTTP request using Axios
    axios(config)
        .then((response) => {
            console.log(response);
            if (response.data != null) {
                // Store the token and user info in session storage
                sessionStorage.setItem("x-token-id", response.data.token); // Assuming token is in response.data.token
                window.location.href = 'default/smarttrack.html'; 
            }
        })
        .catch((error) => {
            console.error("Error details:", error);
            if (error.response) {
                console.log("Response data:", error.response.data); 
                console.log("Response status:", error.response.status);

                if (error.response.status === 401) {
                    alert("Invalid credentials."); 
                } else {
                    alert("Invalid credentials: " + (error.response.data.message || "Please try again.")); 
                }
            } else {
                alert("Network error. Please check your connection."); 
            }
        });
}

function displayUserName() {
    const userName = sessionStorage.getItem("userName");
    if (userName) {
        document.getElementById("userDisplay").innerText = "Hi, " + userName;
    }
}
