let banner = document.getElementById("cookiesBanner");
let okButton = document.getElementById("cookiesButton");
okButton.addEventListener("click", dismiss);
let cookie = getCookie("cookiesAccepted");

if(cookie == null) {
    banner.classList.remove("hidden");
}

function dismiss() {
    banner.classList.add("hidden");
    setCookie("cookiesAccepted", true, 1);
}

function setCookie(name, value, daysToExpire) {
    const expirationDate = new Date();
    expirationDate.setDate(expirationDate.getDate() + daysToExpire);

    const cookieValue = encodeURIComponent(value) + '; expires=' + expirationDate.toUTCString() + '; path=/';
    document.cookie = name + '=' + cookieValue;
}

function getCookie(name) {
    const cookieName = name + '=';
    const cookies = document.cookie.split(';');

    for (let i = 0; i < cookies.length; i++) {
        let cookie = cookies[i].trim();

        if (cookie.indexOf(cookieName) === 0) {
            return decodeURIComponent(cookie.substring(cookieName.length));
        }
    }

    return null;
}