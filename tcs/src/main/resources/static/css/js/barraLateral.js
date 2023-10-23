
// ADICIONAR ATIVO AO CLIQUE
var menuItem = document.querySelectorAll('.item-menu');

function selectLink(){
    menuItem.forEach((item) =>
       item.classList.remove('ativo')
    )
    this.classList.add('ativo')
}

menuItem.forEach((item)=>
 item.addEventListener('click', selectLink)
)

var btnExp = document.querySelector('#btn-exp');
var navBar = document.querySelector('.menu-lateral')

btnExp.addEventListener('click', function(){
    navBar.classList.toggle('expandir')
})


   
  
