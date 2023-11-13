package go.party.tcs.model;

import go.party.tcs.controller.UsuarioController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @OneToOne
    private Usuario sender;

    @OneToOne
    private Usuario receiver;

    private boolean vista;

    public Message() {
    }

    public Message(Long id, String content, Usuario sender, Usuario receiver, boolean vista) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.vista = vista;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Usuario getSender() {
        return sender;
    }

    public void setSender(Usuario sender) {
        this.sender = sender;
    }

    public Usuario getReceiver() {
        return receiver;
    }

    public void setReceiver(Usuario receiver) {
        this.receiver = receiver;
    }

    public boolean isVista() {
        return vista;
    }

    public void setVista(boolean vista) {
        this.vista = vista;
    }

}
