var button = document.getElementById("myBtn");
button.addEventListener("click", checkElements);


// get some variables for each field in the sign up form easier readability 
let username = document.getElementById("c_username");
let email = document.getElementById("c_email");
let password = document.getElementById("c_pass");
let passwordRepeated = document.getElementById("c_repeat_pass");
let firstName = document.getElementById("c_first_name");
let lastName = document.getElementById("c_last_name");
let country = document.getElementById("c_country");
let address = document.getElementById("c_addr");
let zip = document.getElementById("c_zip");
let language = document.getElementById("c_lang");
let bio = document.getElementById("c_bio");
let isMale = document.getElementById("c_male");
let isFemale = document.getElementById("c_female");

/**
 * this function is called when the button is clicked and it calls
 * the check function for each separate field and if every field is
 * valid it creates an alert window
 * @param {*} event is the event
 */
function checkElements(event) {
  event.preventDefault();

  let c1 = checkUsername(username.value);
  let c2 = checkEmail(email.value);
  let c3 = checkPassword(password.value, username.value, firstName.value, lastName.value);
  let c4 = checkRepeatPassword(password.value, passwordRepeated.value);
  let c5 = checkFirstName(firstName.value);
  let c6 = checkLastName(lastName.value);
  let c7 = checkCountry(country.value);
  let c8 = checkAddress();
  let c9 = checkZip(zip.value);
  let c10 = checkLanguage(language.value);
  let c11 = checkBio();
  let c12 = validateForm(isMale, isFemale);
  let correct = c1 && c2 && c3 && c4 && c5 && c6 && c7 && c8 && c9 && c10 && c11 && c12;
  // display the alert window
  if (correct === true) {
    window.alert(
      "You entered the following details:\n" +
      "Username: " + username.value + '\n' +
      "Email: " + email.value + '\n' +
      "Password: " + password.value + '\n' +
      "First name: " + firstName.value + '\n' +
      "Last name: " + lastName.value + '\n' +
      "Country: " + country.value + '\n' +
      "Address: " + address.value + '\n' +
      "Zip code: " + zip.value + '\n' +
      "Language: " + language.value + '\n' +
      "Gender: " + isMale.value + '\n' +
      "Bio: " + username.value);
    window.confirm("Do you want to create a new account with these details?");
  }
}

// the function for checking the username
function checkUsername(username) {
  let lastChar = username.charAt(username.length - 1);
  if (username.length === 0 || typeof username === 'undefined') {      //required field check
    appendMessage("This field is required!", "user_message", 1, 0);
    return false;
  }
  else if (username.length < 5 || username.length > 12) {
    appendMessage("Username must be 5 to 12 characters long!", "user_message", 1, 0);
    return false;
  }
  else if (username.charAt(0) < 'A' || username.charAt(0) > 'Z') {
    appendMessage("Username must start with a capital letter!", "user_message", 1, 0);
    return false;
  }
  else if (lastChar >= 'A' && lastChar <= 'Z' || lastChar >= 'a' && lastChar <= 'z') {
    appendMessage("Username must end with a special character or a number!", "user_message", 1, 0);
    return false;
  }
  appendMessage("Everything seems right!", "user_message", 0, 0);
  return true;
}

// the function for checking the first name
function checkFirstName(name) {
  if (name.length === 0 || typeof name === 'undefined') {      //required field check
    appendMessage("This field is required!", "c_firstname_message", 1, 4);
    return false;
  }
  else for (let i = 0; i < name.length; i++) {
    if (!(name.charAt(i) >= 'A' && name.charAt(i) <= 'Z' || name.charAt(i) >= 'a' && name.charAt(i) <= 'z')) {
      appendMessage("The name may only contain letters!", "c_firstname_message", 1, 4);
      return false;
    }
  }
  appendMessage("Everything seems right!", "c_firstname_message", 0, 4);
  return true;
}

// the function for checking the last name
function checkLastName(name) {
  if (name.length === 0 || typeof name === 'undefined') {      //required field check
    appendMessage("This field is required!", "c_lastname_message", 1, 5);
    return false;
  }
  else for (let i = 0; i < name.length; i++) { //check if it contains only letters
    if (!(name.charAt(i) >= 'A' && name.charAt(i) <= 'Z' || name.charAt(i) >= 'a' && name.charAt(i) <= 'z')) {
      appendMessage("The name may only contain letters!", "c_lastname_message", 1, 5);
      return false;
    }
  }
  appendMessage("Everything seems right!", "c_lastname_message", 0, 5);
  return true;
}

// the function for checking the country
function checkCountry(name) {
  if (name.length === 0 || typeof name === 'undefined') {      //required field check
    appendMessage("This field is required!", "c_country_message", 1, 6);
    return false;
  }
  else for (let i = 0; i < name.length; i++) //check if it contains only letters
    if (!(name.charAt(i) >= 'A' && name.charAt(i) <= 'Z' || name.charAt(i) >= 'a' && name.charAt(i) <= 'z' || name.charAt(i) == ' ')) {
      appendMessage("The country may only contain letters!", "c_country_message", 1, 6);
      return false;
    }
  appendMessage("Everything seems right!", "c_country_message", 0, 6);
  return true;
}

// the function for checking the address
function checkAddress() {
  appendMessage("Everything seems right!", "c_addr_message", 0, 7);
  return true;
}

// the function for checking the zip code
function checkZip(zip) {
  if (zip.length === 0 || typeof zip === 'undefined') {      //required field check
    appendMessage("This field is required!", "c_zip_message", 1, 8);
    return false;
  }
  else if (zip.length !== 6) { //check if it is the right format 0000AA
    appendMessage("Invalid length!", "c_zip_message", 1, 8);
    return false;
  }
  else {
    for (let i = 0; i < 4; i++)
      if (zip.charAt(i) < '0' || zip.charAt(i) > '9') {
        appendMessage("Invalid format!", "c_zip_message", 1, 8);
        return false;
      }
    for (let i = 4; i < 6; i++)
      if (!(zip.charAt(i) >= 'A' && zip.charAt(i) <= 'Z' || zip.charAt(i) >= 'a' && zip.charAt(i) <= 'z')) {
        appendMessage("Invalid postal code!", "c_zip_message", 1, 8);
        return false;
      }
  }
  appendMessage("Everything seems right!", "c_zip_message", 0, 8);
  return true;
}

// the function for checking the language
function checkLanguage(name) {
  if (name.length === 0 || typeof name === 'undefined') {      //required field check
    appendMessage("This field is required!", "c_lang_message", 1, 9);
    return false;
  }
  appendMessage("Everything seems right!", "c_lang_message", 0, 9);
  return true;
}

// the function for checking the BIO
function checkBio() {
  document.getElementById("details").style.borderColor = "green";
  return true;
}

// the function for checking the email
function checkEmail(email) {
  if (email.length === 0 || typeof email === 'undefined') {      //required field check
    appendMessage("This field is required!", "c_email_message", 1, 1);
    return false;
  }
  else { //check if the format is valid aaa@aa.a
    let nr1 = 0, nr2 = 0;
    for (let i = 0; i < email.length; i++) {
      if (email[i] === '@') nr1++;
      if (email[i] === '.') nr2++;
    }
    if (nr1 != 1 || nr2 != 1) {
      appendMessage("The format is invalid!", "c_email_message", 1, 1);
      return false;
    }
    else if (email.indexOf("@") > email.indexOf(".")) {
      appendMessage("The format is invalid!", "c_email_message", 1, 1);
      return false;
    }
  }
  appendMessage("Everything seems right!", "c_email_message", 0, 1);
  return true;
}

// the function for checking the password
function checkPassword(password, username, firstName, lastName) {
  if (password.length === 0 || typeof password === 'undefined') {      //required field check
    appendMessage("This field is required!", "c_pass_message", 1, 2);
    return false;
  }
  else {
    if (password.length < 12) { //check the length
      appendMessage("The password requires at least 12 characters!", "c_pass_message", 1, 2);
      return false;
    }
    //check if the password contains the username/firstname/lastname
    if (username.length > 0 && password.toUpperCase().includes(username.toUpperCase())) {
      appendMessage("The password may not contain your username!", "c_pass_message", 1, 2);
      return false;
    }
    if (firstName.length > 0 && password.toUpperCase().includes(firstName.toUpperCase())) {
      appendMessage("The password may not contain your firstname!", "c_pass_message", 1, 2);
      return false;
    }
    if (lastName.length > 0 && password.toUpperCase().includes(lastName.toUpperCase())) {
      appendMessage("The password may not contain your lastname!", "c_pass_message", 1, 2);
      return false;
    }

    let hasLowercase = false;
    let hasUppercase = false;
    let hasNumber = false;
    let hasSymbol = false;
    //check if the password contains at least an uppercase, lowercase, number and symbol
    for (var i = 0; i < password.length; i++) {
      var char = password.charAt(i);
      if (!hasLowercase && char >= 'a' && char <= 'z') {
        hasLowercase = true;
      } else if (!hasUppercase && char >= 'A' && char <= 'Z') {
        hasUppercase = true;
      } else if (!hasNumber && !isNaN(char)) {
        hasNumber = true;
      } else if (!hasSymbol && /[!@#\$%\^&*]/.test(char)) {
        hasSymbol = true;
      }
    }

    if (!hasLowercase) {
      appendMessage("The password requires at least a lowercase char!", "c_pass_message", 1, 2);
      return false;
    }
    if (!hasUppercase) {
      appendMessage("The password requires at least an upperrcase char!", "c_pass_message", 1, 2);
      return false;
    }
    if (!hasNumber) {
      appendMessage("The password requires at least a number!", "c_pass_message", 1, 2);
      return false;
    }
    if (!hasSymbol) {
      appendMessage("The password requires at least a symbol!", "c_pass_message", 1, 2);
      return false;
    }
  }

  if (password.length < 14) {
    appendMessage("The password is recommended to have more than 14 characters!", "c_pass_message", 2, 2);
  }
  else appendMessage("Everything seems right!", "c_pass_message", 0, 2);
  return true;
}



// the function for checking the passwords match
function checkRepeatPassword(password, repeatpassword) {
  if (password.length === 0 || typeof password === 'undefined') { //required field check
    appendMessage("This field is required!", "c_reppass_message", 1, 3);
    return false;
  }
  else if (password !== repeatpassword) { //check if passwords match
    appendMessage("Passwords do not match!", "c_reppass_message", 1, 3);
    return false;
  }
  else
    appendMessage("Everything seems right!", "c_reppass_message", 0, 3);
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
 * @param {*} position the position in the sign up form
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
* @param {*} position the position in the sign up form
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