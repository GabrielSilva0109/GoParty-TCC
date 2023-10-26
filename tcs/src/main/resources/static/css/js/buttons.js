// Função para aparecer os comentarios 
function toggleComentarios(button) {
    // Encontre o elemento pai que contém o botão e os comentários
    const parent = button.closest('.evento-bloco');

    // Encontre a div de comentários dentro do elemento pai
    const comentariosDiv = parent.querySelector('.comentarios');

    // Alternar a classe hidden nas divs de comentários
    if (comentariosDiv.classList.contains('hidden')) {
        comentariosDiv.classList.remove('hidden');
    } else {
        comentariosDiv.classList.add('hidden');
    }
}

// Função para abrir modal das Notificações
function openModalNotificacao() {
    var modal = document.getElementById("modalNotificacao");
    modal.style.display = "flex";
}

function fecharModalNotificacao() {
    var modal = document.getElementById("modalNotificacao");
    modal.style.display = "none";
}

window.onclick = function (event) {
    var modal = document.getElementById("modalNotificacao");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

// Função para abrir modal das Notificações
function openModalConfiguracao() {
    var modal = document.getElementById("modalConfiguracao");
    modal.style.display = "flex";
}

function fecharModalConfiguracao() {
    var modal = document.getElementById("modalConfiguracao");
    modal.style.display = "none";
}

window.onclick = function (event) {
    var modal = document.getElementById("modalConfiguracao");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
