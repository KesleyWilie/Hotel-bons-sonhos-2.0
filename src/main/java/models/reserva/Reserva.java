package models.reserva;

import jakarta.persistence.*;
import models.Usuario;
import models.quarto.Quarto;

import java.util.Date;

@Entity
@Table(name = "reservas")
public class Reserva implements Prototype{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "id_quarto", nullable = false)
    private Quarto quarto;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_checkin", nullable = false)
    private Date dataCheckin;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_checkout", nullable = false)
    private Date dataCheckout;

    @Column(name = "preco_total", nullable = false)
    private double precoTotal;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public Date getDataCheckin() {
        return dataCheckin;
    }

    public void setDataCheckin(Date dataCheckin) {
        this.dataCheckin = dataCheckin;
    }

    public Date getDataCheckout() {
        return dataCheckout;
    }

    public void setDataCheckout(Date dataCheckout) {
        this.dataCheckout = dataCheckout;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    @Override
    public Reserva clone() {
        Reserva reservaClonada = new Reserva();
        reservaClonada.setCliente(this.cliente); // Mantém a referência, ajuste se necessário
        reservaClonada.setQuarto(this.quarto);   // Mantém a referência, ajuste se necessário
        reservaClonada.setDataCheckin(this.dataCheckin);
        reservaClonada.setDataCheckout(this.dataCheckout);
        reservaClonada.setPrecoTotal(this.precoTotal);
        return reservaClonada;
    }
}
    /*
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "id_quarto", nullable = false)
    private Quarto quarto;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_checkin", nullable = false)
    private Date dataCheckin;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_checkout", nullable = false)
    private Date dataCheckout;

    @Column(name = "preco_total", nullable = false)
    private double precoTotal;

    // gets e set da vida
} */
