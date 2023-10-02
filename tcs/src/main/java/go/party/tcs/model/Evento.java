package go.party.tcs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @Lob
    @Column(name = "foto_evento", columnDefinition = "BLOB")
    private byte[] fotoEvento;

    // Construtor vazio
    public Evento() {
    }

    // Construtor com parâmetros
    public Evento(String titulo, String descricao, Usuario autor) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.autor = autor;
    }

    // Getters e setters para id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getters e setters para titulo
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Getters e setters para descricao
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // Getters e setters para autor
    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public byte[] getFotoEvento() {
        return fotoEvento;
    }
    
    public void setFotoEvento(byte[] fotoEvento) {
        this.fotoEvento = fotoEvento;
    }

    
    
}
