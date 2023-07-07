let logInButton = document.getElementById("logIn");
logInButton.addEventListener("click", checkLogInElements);

let email = document.getElementById("c_li_email");
let password = document.getElementById("c_li_password");

function checkLogInElements(event) {
    event.preventDefault();

    let c0 = checkLogInEmail(email.value);
    let c1 = checkLogInPassword(password.value);
    let correct = c0 && c1;

    if (correct === true) {
        let hash = CryptoJS.SHA256(password).toString();
        hash = hash.substring(0, 255);
        const authenticationRequest = {
            email: email.value,
            password: hash
        };
        logIn(authenticationRequest);
    }
}

function checkLogInEmail(email) {
    if (email.length === 0 || typeof email === 'undefined') {      //required field check
        appendErrorMessage("This field is required!", "c_li_email_message", 0);
        return false;
    }
    appendErrorMessage("", "c_li_email_message", 0);
    return true;
}

function checkLogInPassword(password) {
    if (password.length === 0 || typeof password === 'undefined') {      //required field check
        appendErrorMessage("This field is required!", "c_li_password_message", 1);
        return false;
    }
    appendErrorMessage("", "c_li_password_message", 1);
    return true;
}

function appendErrorMessage(message, id, position) {
    let div = document.getElementById(id);
    let inputs = document.getElementsByClassName("field");
    div.innerHTML = message;
    if(message.length > 0) {
        div.style.color = "red";
        inputs[position].style.borderColor = 'red';
    }
    else {
        inputs[position].style.borderColor = '#adadad';
    }
}

function logIn(authenticationRequest) {
    let server = window.location.protocol + '//' + window.location.host + "/api/user/logIn";

    fetch(server, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(authenticationRequest)
    }).then(res => {
        if(res.ok) {
            res.json().then(
                data => {
                    let token = data.token;
                    document.cookie = `${"jwtCookie"}=${token}; Secure; HttpOnly; SameSite=Strict; Path=/`;
                    alert("Successfully logged in!\n" + token);
                }
            )
        }
        else {
            res.text().then(
                data => {
                    appendErrorMessage(data, "c_li_email_message", 0);
                    appendErrorMessage(data, "c_li_password_message", 1);
                }
            )
        }
    }).catch(error => {
        console.error('Error:', error);
    });
}

