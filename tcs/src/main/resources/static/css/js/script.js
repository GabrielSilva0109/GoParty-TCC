// TESTE FOTO

const fotoInput = document.getElementById("fotoInput");
const fotoExibida = document.getElementById("fotoExibida");
const fotoVazia = document.getElementById('imgPerfil-vazio');

fotoInput.addEventListener("change", function () {
    const file = fotoInput.files[0];
    fotoExibida.style.display = "";
    fotoVazia.style.display = "none";

    if (file && file.type.startsWith("image/")) {
        const reader = new FileReader();

        reader.onload = function (e) {
            fotoExibida.src = e.target.result;
        };

        reader.readAsDataURL(file);
    } else {
        alert("Por favor, selecione uma imagem válida.");
    }
});

   

const buttons = document.querySelectorAll('.chatBtn');

buttons.forEach((button) => {
    button.addEventListener('click', () => {
        const parent = button.closest('.evento-bloco');
        const comentariosDiv = parent.querySelector('.comentarios');
        if (comentariosDiv.classList.contains('hidden')) {
            comentariosDiv.classList.remove('hidden');
        } else {
            comentariosDiv.classList.add('hidden');
        }
    });
});