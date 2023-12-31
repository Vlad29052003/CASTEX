const server = window.location.protocol + '//' + window.location.host;

function redirectToIndex() {
    window.location.href = server + '/';
}

function redirectToSingUp() {
    window.location.href = server + '/signup';
}

function redirectToLogIn() {
    window.location.href = server + '/login';
}

function redirectToHelp() {
    window.location.href = server + '/help';
}

function redirectToTermsAndConditions() {
    window.location.href = server + '/help#terms';
}

function redirectToCart() {
    window.location.href = server + '/cart';
}

function redirectToAccountCreated() {
    window.location.href = server + '/account-created';
}