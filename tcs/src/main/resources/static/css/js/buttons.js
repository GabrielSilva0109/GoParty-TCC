
    const buttons = document.querySelectorAll('.chatBtn');

    buttons.forEach((button) => {
        button.addEventListener('click', () => {
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
        });
    });
