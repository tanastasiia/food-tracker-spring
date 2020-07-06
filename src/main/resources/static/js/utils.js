function createAlertDiv(msg, beforeElementId, infoOrDanger) {
    var errorField = document.getElementById(beforeElementId);
    var errorDiv = document.createElement("DIV");
    errorDiv.className = "alert alert-" + infoOrDanger;
    errorDiv.appendChild(document.createTextNode(msg));
    errorField.parentNode.insertBefore(errorDiv, errorField.nextSibling);
}

function removeAlertDivs() {
    var divs = document.getElementsByClassName("alert alert-danger");
    while (divs[0]) {
        divs[0].parentNode.removeChild(divs[0]);
    }
    divs = document.getElementsByClassName("alert alert-info");
    while (divs[0]) {
        divs[0].parentNode.removeChild(divs[0]);
    }
}