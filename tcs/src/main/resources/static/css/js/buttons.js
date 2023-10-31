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

//MODAL DE PERFIL DO USUARIO

function encontrarUsuarioPorId(usuarioId) {
    // URL da sua API para buscar um usuário por ID
    const apiUrl = `/acharUsuario/${usuarioId}`; // Substitua pela URL correta

    return fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Erro na requisição: ${response.status}`);
            }
            return response.json();
        })
        .then(usuario => {

            document.getElementById("modal-usuario-nome").textContent = "Nome: " + usuario.nome;
           
            if (usuario.descricao) {
                document.getElementById("modal-usuario-email").textContent = "Biografia: " + usuario.descricao;
            } 
             // Verifique se a URL da foto do usuário existe
             if (usuario.urlFoto) {
                document.getElementById("fotoUsuario").src = usuario.fotoPerfil;
            } else {
                // Caso não exista uma foto, defina uma foto padrão
                document.getElementById("fotoUsuario").src = "/css/img/fotoPerfil.jpg"; 
            }

            return usuario;
        })
        .catch(error => {
            console.error("Erro na requisição:", error);
            return null;
        });
}

function mostrarFiltros() {
    var filtrosDiv = document.querySelector('.filtros-evento');

    if (filtrosDiv.style.display === 'none' || filtrosDiv.style.display === '') {
        filtrosDiv.style.display = 'flex';
    } else {
        filtrosDiv.style.display = 'none';
    }
}

    function mostrarModal(botao) {
        // Obter o ID do usuário a partir do atributo data-usuario-id
        const usuarioId = botao.getAttribute("data-usuario-id");
        const usuario = encontrarUsuarioPorId(usuarioId);

        // Mostrar o modal
        document.getElementById("modal-perfil-usuario").style.display = "block";
    }

    function fecharModal() {
        // Fechar o modal
        document.getElementById("modal-perfil-usuario").style.display = "none";
    }

    // API DE CEP  

    document.addEventListener("DOMContentLoaded", function () {
        const cepInput = document.getElementById("cep");
        const cidadeInput = document.getElementById("cidade");
        const bairroInput = document.getElementById("bairro");
    
        cepInput.addEventListener("input", function (e) {
          // Remove caracteres não numéricos
          const cleanedValue = e.target.value.replace(/\D/g, "");
    
          // Formata o CEP como "XXXXX-XXX"
          if (cleanedValue.length > 5) {
            e.target.value = cleanedValue.slice(0, 5) + "-" + cleanedValue.slice(5, 8);
          } else {
            e.target.value = cleanedValue;
          }
        });

        cepInput.addEventListener("blur", function () {
            const cep = cepInput.value.replace(/\D/g, ""); // Remove caracteres não numéricos
        
            if (cep.length === 8) {
              // Faz uma solicitação para a API do ViaCEP
              fetch(`https://viacep.com.br/ws/${cep}/json/`)
                .then((response) => response.json())
                .then((data) => {
                  if (!data.erro) {
                    // Preenche os campos de cidade e bairro com os dados da API
                    cidadeInput.value = data.localidade;
                    bairroInput.value = data.bairro;

                    // Seleciona automaticamente a opção do estado com base no estado retornado
                    const estadoSelect = document.getElementById("estado");
                    const estadoSelecionado = data.uf.toLowerCase();
                    estadoSelect.value = estadoSelecionado;
                  }
                })
                .catch((error) => {
                  console.error("Erro na solicitação ViaCEP: " + error);
                });
            }
          });
      });


    






