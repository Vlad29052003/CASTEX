let button = document.getElementById("myBtn");
button.addEventListener("click", checkSignUpElements);


// get some variables for each field in the sign-up form easier readability
let email = document.getElementById("c_email");
let password = document.getElementById("c_pass");
let passwordRepeated = document.getElementById("c_repeat_pass");
let firstName = document.getElementById("c_first_name");
let lastName = document.getElementById("c_last_name");
let address = document.getElementById("c_addr");
let zip = document.getElementById("c_zip");
let isMale = document.getElementById("c_male");
let isFemale = document.getElementById("c_female");

/**
 * this function is called when the button is clicked, and it calls
 * the check function for each separate field and if every field is
 * valid it creates an alert window
 * @param {*} event is the event
 */
function checkSignUpElements(event) {
  event.preventDefault();

  let c0 = checkEmail(email.value);
  let c1 = checkPassword(password.value, firstName.value, lastName.value);
  let c2 = checkRepeatPassword(password.value, passwordRepeated.value);
  let c3 = checkFirstName(firstName.value);
  let c4 = checkLastName(lastName.value);
  let c5 = checkAddress(address.value);
  let c6 = checkZip(zip.value);
  let c7 = validateForm(isMale, isFemale);
  let correct = c0 && c1 && c2 && c3 && c4 && c5 && c6 && c7;

  if (correct === true) {
    signUp();
  }
}

// the function for checking the first name
function checkFirstName(name) {
  if (name.length === 0 || typeof name === 'undefined') {      //required field check
    appendMessage("This field is required!", "c_firstname_message", 1, 3);
    return false;
  }
  else if (name.length > 50) {
    appendMessage("Max 50 characters!", "c_firstname_message", 1, 3);
    return false;
  }
  else for (let i = 0; i < name.length; i++) {
    if (!(name.charAt(i) >= 'A' && name.charAt(i) <= 'Z' || name.charAt(i) >= 'a' && name.charAt(i) <= 'z')) {
      appendMessage("The name may only contain letters!", "c_firstname_message", 1, 3);
      return false;
    }
  }
  appendMessage("Everything seems right!", "c_firstname_message", 0, 3);
  return true;
}

// the function for checking the last name
function checkLastName(name) {
  if (name.length === 0 || typeof name === 'undefined') {      //required field check
    appendMessage("This field is required!", "c_lastname_message", 1, 4);
    return false;
  }
  else if (name.length > 50) {
    appendMessage("Max 50 characters!", "c_lastname_message", 1, 4);
    return false;
  }
  else for (let i = 0; i < name.length; i++) { //check if it contains only letters
    if (!(name.charAt(i) >= 'A' && name.charAt(i) <= 'Z' || name.charAt(i) >= 'a' && name.charAt(i) <= 'z')) {
      appendMessage("The name may only contain letters!", "c_lastname_message", 1, 4);
      return false;
    }
  }
  appendMessage("Everything seems right!", "c_lastname_message", 0, 4);
  return true;
}

// the function for checking the address
function checkAddress(address) {
  if (address.length === 0 || typeof address === 'undefined') {      //required field check
    appendMessage("This field is required!", "c_addr_message", 1, 5);
    return false;
  }
  else if (address.length < 10 || address.length > 255) {
    appendMessage("Address must be 10 to 255 characters long!", "c_addr_message", 1, 5);
    return false;
  }
  appendMessage("Everything seems right!", "c_addr_message", 0, 5);
  return true;
}

// the function for checking the zip code
function checkZip(zip) {
  // TODO //
  if (zip.length === 0 || typeof zip === 'undefined') {      //required field check
    appendMessage("This field is required!", "c_zip_message", 1, 6);
    return false;
  }
  else if (zip.length > 20) { //check if it is the right format 0000AA
    appendMessage("Max 20 characters!", "c_zip_message", 1, 6);
    return false;
  }
  appendMessage("Everything seems right!", "c_zip_message", 0, 6);
  return true;
}

// the function for checking the email
function checkEmail(email) {
  if (email.length === 0 || typeof email === 'undefined') {      //required field check
    appendMessage("This field is required!", "c_email_message", 1, 0);
    return false;
  }
  else if (email.length > 255) { //check if it is the right format 0000AA
    appendMessage("Max 255 characters!", "c_email_message", 1, 0);
    return false;
  }
  else { //check if the format is valid aaa@aa.a
    let nr1 = 0, nr2 = 0;
    for (let i = 0; i < email.length; i++) {
      if (email[i] === '@') nr1++;
      if (email[i] === '.') nr2++;
    }
    if (nr1 !== 1 || nr2 !== 1) {
      appendMessage("The format is invalid!", "c_email_message", 1, 0);
      return false;
    }
    else if (email.indexOf("@") > email.indexOf(".")) {
      appendMessage("The format is invalid!", "c_email_message", 1, 0);
      return false;
    }
  }
  appendMessage("Everything seems right!", "c_email_message", 0, 0);
  return true;
}

// the function for checking the password
function checkPassword(password, firstName, lastName) {
  if (password.length === 0 || typeof password === 'undefined') {      //required field check
    appendMessage("This field is required!", "c_pass_message", 1, 1);
    return false;
  }
  else {
    if (password.length < 8) { //check the length
      appendMessage("The password requires at least 8 characters!", "c_pass_message", 1, 1);
      return false;
    }
    else if (password.length > 255) { //check if it is the right format 0000AA
      appendMessage("Max 255 characters!", "c_pass_message", 1, 1);
      return false;
    }
    if (firstName.length > 0 && password.toUpperCase().includes(firstName.toUpperCase())) {
      appendMessage("The password may not contain your firstname!", "c_pass_message", 1, 1);
      return false;
    }
    if (lastName.length > 0 && password.toUpperCase().includes(lastName.toUpperCase())) {
      appendMessage("The password may not contain your lastname!", "c_pass_message", 1, 1);
      return false;
    }

    let hasLowercase = false;
    let hasUppercase = false;
    let hasNumber = false;
    let hasSymbol = false;
    //check if the password contains at least an uppercase, lowercase, number and symbol
    for (let i = 0; i < password.length; i++) {
      let char = password.charAt(i);
      if (!hasLowercase && char >= 'a' && char <= 'z') {
        hasLowercase = true;
      } else if (!hasUppercase && char >= 'A' && char <= 'Z') {
        hasUppercase = true;
      } else if (!hasNumber && !isNaN(char)) {
        hasNumber = true;
      } else if (!hasSymbol && /[!@#$%^&*]/.test(char)) {
        hasSymbol = true;
      }
    }

    if (!hasLowercase) {
      appendMessage("The password requires at least a lowercase char!", "c_pass_message", 1, 1);
      return false;
    }
    if (!hasUppercase) {
      appendMessage("The password requires at least an uppercase char!", "c_pass_message", 1, 1);
      return false;
    }
    if (!hasNumber) {
      appendMessage("The password requires at least a number!", "c_pass_message", 1, 1);
      return false;
    }
    if (!hasSymbol) {
      appendMessage("The password requires at least a symbol!", "c_pass_message", 1, 1);
      return false;
    }
  }

  if (password.length < 12) {
    appendMessage("The password is recommended to have more than 12 characters!", "c_pass_message", 2, 1);
  }
  else appendMessage("Everything seems right!", "c_pass_message", 0, 1);
  return true;
}



// the function for checking the passwords match
function checkRepeatPassword(password, repeatPassword) {
  if (password.length === 0 || typeof password === 'undefined') { //required field check
    appendMessage("This field is required!", "c_reppass_message", 1, 2);
    return false;
  }
  else if (password !== repeatPassword) { //check if passwords match
    appendMessage("Passwords do not match!", "c_reppass_message", 1, 2);
    return false;
  }
  else
    appendMessage("Everything seems right!", "c_reppass_message", 0, 2);
  return true;
}

function validateForm(isMale, isFemale) {
  if (!isMale.checked && !isFemale.checked) {
    appendMessage1("This field is required", "c_checkbox1", 1, 0);
    return false;
  }
  appendMessage1("Everything seems right!", "c_checkbox1", 0, 0);
  return true;
}

/**
 * The function for adding a message under each input box
 * @param {*} message the string that has to appear if the certain condition holds or not 
 * @param {*} id for the message box
 * @param {*} color the color representing the 0 - green 1 - red
 *  2 - yellow for the underline of the message and the message itself
 * @param {*} position the position in the sign-up form
 */
function appendMessage(message, id, color, position) {
  let div = document.getElementById(id);
  let inputs = document.getElementsByClassName("field");
  div.innerHTML = message;
  if (color === 0) {
    div.style.color = "green";
    inputs[position].style.borderColor = "green";
  }
  else if (color === 2) {
    div.style.color = "rgb(243, 148, 25)";
    inputs[position].style.borderColor = "rgb(243, 148, 25)";
  }
  else {
    div.style.color = "red";
    inputs[position].style.borderColor = 'red';
  }

}

/**
* The function for adding a message under the gender
* @param {*} message the string that has to appear if the certain condition holds or not 
* @param {*} id for the message box
* @param {*} color the color representing the 0 - green 1 - red
*  2 - yellow for the underline of the message and the message itself
* @param {*} position the position in the sign-up form
*/
function appendMessage1(message, id, color, position) {
  let div = document.getElementById(id);
  let inputs = document.getElementsByClassName("checkboxes");
  div.innerHTML = message;
  if (color === 0) {
    div.style.color = "green";
    inputs[position].style.borderColor = "green";
  }
  else if (color === 2) {
    div.style.color = "rgb(243, 148, 25)";
    inputs[position].style.borderColor = "rgb(243, 148, 25)";
  }
  else {
    div.style.color = "red";
    inputs[position].style.borderColor = 'red';
  }

}