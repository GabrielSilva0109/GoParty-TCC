console.log("Funcionou");

//Username com mais de 12 caracteres
document.addEventListener("DOMContentLoaded", function() {
    const usernameInput = document.getElementById("username");
    const usernameInvalidoDiv = document.querySelector(".usernameInvalido-container");
    const submitButton = document.getElementById("btn-cadastrar-space");

    usernameInput.addEventListener("input", function() {
        const usernameValue = usernameInput.value;

        if (usernameValue.length < 10) {
            usernameInvalidoDiv.style.display = "none";
            submitButton.disabled = false;
        } else {
            usernameInvalidoDiv.style.display = "block";
            submitButton.disabled = true;
        }
    });
});
