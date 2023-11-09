package go.party.tcs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table (name = "ingresso")
public class Ingresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "codigo")
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario idUsuario;

    @ManyToOne
    @JoinColumn(name = "id_evento")
    private Evento evento;

    // Construtores, getters e setters...

    public Ingresso() {
    
    }

    public Ingresso(String codigo, Usuario idUsuario, Evento evento) {
        this.codigo = codigo;
        this.idUsuario = idUsuario;
        this.evento = evento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}
