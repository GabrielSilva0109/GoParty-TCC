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

// Função para abrir modal das Configuração
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

// Função para abrir edições
function abrirEdicao() {
    var blocoEdicao = document.getElementById('blocoEdicao');
            if (blocoEdicao.style.display === 'none' || blocoEdicao.style.display === '') {
                blocoEdicao.style.display = 'flex';
            } else {
                blocoEdicao.style.display = 'none';
            }
}

// Função para abrir edições
function abrirExcluir() {
    var blocoExcluir = document.getElementById('blocoExcluir');
            if (blocoExcluir.style.display === 'none' || blocoExcluir.style.display === '') {
                blocoExcluir.style.display = 'flex';
            } else {
                blocoExcluir.style.display = 'none';
            }
}

//Requisição de curtida 

// JavaScript
function checkboxChanged(checkbox) {
    const eventoId = checkbox.getAttribute('data-evento-id');
    if (checkbox.checked) {
        curtirEvento(eventoId);
        
    } else {
        descurtirEvento(eventoId);
    }
}

// JavaScript
function curtirEvento(eventoId) {
    const url = `/curtirEvento/${eventoId}`;

    // O objeto de opções da solicitação, incluindo o método POST
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json', // Se você estiver enviando dados no corpo da solicitação
        },
        // Adicione um corpo se estiver enviando dados
        // body: JSON.stringify({ chave: valor }),
    };

    fetch(url, requestOptions)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Erro na requisição: ${response.status}`);
            }
            // Trate a resposta aqui, se necessário
            console.log('Evento curtido com sucesso');
            
        })
        .catch(error => {
            console.error('Erro na requisição:', error);
        });
}

function descurtirEvento(eventoId) {
    const url = `/descurtiEvento/${eventoId}`;

    // O objeto de opções da solicitação, incluindo o método POST
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json', // Se você estiver enviando dados no corpo da solicitação
        },
        // Adicione um corpo se estiver enviando dados
        // body: JSON.stringify({ chave: valor }),
    };

    fetch(url, requestOptions)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Erro na requisição: ${response.status}`);
            }
            // Trate a resposta aqui, se necessário
            console.log('Evento descurtido com sucesso');
            
        })
        .catch(error => {
            console.error('Erro na requisição:', error);
        });
}





