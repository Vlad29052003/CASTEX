function signUp() {
    let server = window.location.protocol + '//' + window.location.host + "/api/user/signUp";
    let hash = CryptoJS.SHA256(password).toString();
    hash = hash.substring(0, 255);
    let gender = "F";
    if(isMale.checked) gender = "M";
    let authority = "USER";

    const credentials = {
        email: email.value,
        hashedPassword: hash,
        firstName: firstName.value,
        lastName: lastName.value,
        address: address.value,
        zipCode: zip.value,
        gender: gender,
        authority: authority
    };

    fetch(server, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(credentials)
    })
        .then(response => {
            let promise = response.text();
            if (response.ok) {
                //TODO create successfully created page instead of alert
                window.location.href = window.location.protocol + '//' + window.location.host + '/login';
                alert("Account successfully created");
            } else {
                promise.then((data) => {
                    if(data === "This email is already in use!")
                        appendMessage("This email is already in use!", "c_email_message", 1, 0);
                    else throw new Error(data);
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error);
        });
}