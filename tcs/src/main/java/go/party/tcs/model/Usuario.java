package go.party.tcs.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

//Classe Entidade
//Tabela no banco  "Usuarios"
@Entity
@Table(name ="usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "idade")
    private LocalDate idade;

    @Column(name = "url")
    private String url;

    @Column(name = "senha")
    private String senha;

    @Lob
    @Column(name = "foto_perfil", columnDefinition = "BLOB")
    private byte[] fotoPerfil;

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getUsername() {
        return username;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getIdade() {
        return idade;
    }

    public String getUrl() {
        return url;
    }

    public String getSenha() {
        return senha;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setIdade(LocalDate idade) {
        this.idade = idade;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public byte[] getFotoPerfil() {
        return fotoPerfil;
    }
    
    public void setFotoPerfil(byte[] fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
    
}
