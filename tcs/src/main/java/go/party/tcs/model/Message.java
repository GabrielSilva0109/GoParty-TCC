package go.party.tcs.model;

import java.time.LocalDateTime;

import go.party.tcs.controller.UsuarioController;
import jakarta.persistence.Column;
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

    @Column
    private String content;

    @Column
    private Integer sender;

    @Column
    private Integer receiver;

    @Column
    private boolean vista;

    @Column
    private LocalDateTime dataHoraMsg;

    public Message() {
    }

    public Message(Long id, String content, Integer sender, Integer receiver, boolean vista, LocalDateTime dataHoraMsg) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.vista = vista;
        this.dataHoraMsg = dataHoraMsg;
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

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public boolean isVista() {
        return vista;
    }

    public void setVista(boolean vista) {
        this.vista = vista;
    }

    public LocalDateTime getDataHoraMsg() {
        return dataHoraMsg;
    }

    public void setDataHoraMsg(LocalDateTime dataHoraMsg) {
        this.dataHoraMsg = dataHoraMsg;
    }

}
