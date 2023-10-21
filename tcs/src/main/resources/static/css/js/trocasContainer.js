 // elemento <a> com a classe "links"
 var meuLink = document.getElementById("meuLink");
 var homeLink = document.getElementById("home-link");
 var notificLink = document.getElementById("notificacoes-link");

 //as seções
 var feedSection = document.getElementById("feed-section");
 var criarEventoSection = document.getElementById("criar-evento-section");
 var notiSection = document.getElementById("notificacoes-section");
  

 // Adiciona um ouvinte de evento de clique ao link
 meuLink.addEventListener("click", function() {

    var botoes = document.querySelectorAll(".icon");
    botoes.forEach(function(item) {
        item.classList.remove("active-button");
    });

     // Altera o estilo das seções para ocultar/exibir
     feedSection.style.display = "none";
     notiSection.style.display = "none";
     criarEventoSection.style.display = "flex";
     iconeBotao.classList.add("active-button");
     
 });

  //ouvinte de evento de clique ao link
  notificLink.addEventListener("click", function() {

    var botoes = document.querySelectorAll(".icon");
    botoes.forEach(function(item) {
        item.classList.remove("active-button");
    });
      // Altera o estilo das seções para ocultar/exibir
      feedSection.style.display = "none";
      criarEventoSection.style.display = "none";
      notiSection.style.display = "flex";
      iconeBotaoNotis.classList.add("active-button");
  });

  
  //ouvinte de evento de clique ao link
  homeLink.addEventListener("click", function() {

    var botoes = document.querySelectorAll(".icon");
    botoes.forEach(function(item) {
        item.classList.remove("active-button");
    });
    // Altera o estilo das seções para ocultar/exibir
    criarEventoSection.style.display = "none";
    notiSection.style.display = "none";
    feedSection.style.display = "";
    iconeBotaoHome.classList.add("active-button");
});




 console.log("funcionou!")

 