package models.quarto;

import controller.UsuarioController;
import models.quarto.observer.IObserver;
import models.quarto.observer.QuartoNovoSubject;
//import jakarta.persistence.*;

public class Quarto {

    private int codigoQuarto;
    private int numero;
    private int andar;
    private double precoDiaria;
    private String tipo;
    private int capacidadeMaxima;

    protected QuartoNovoSubject subject;

    public void configurarOuvintes() {
        this.subject = UsuarioController.recuperarOuvintes();
        this.subject.notificarObservers();
    }

    public int getCodigoQuarto() {
        return codigoQuarto;
    }

    public void setCodigoQuarto(int id) {
        this.codigoQuarto = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    public double getPrecoDiaria() {
        return precoDiaria;
    }

    public void setPrecoDiaria(double precoDiaria) {
        this.precoDiaria = precoDiaria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public void adicionarObserver(IObserver observer) {
        this.subject.adicionarObserver(observer);
    }

    public void removerObserver(IObserver observer) {
        this.subject.removerObserver(observer);
    }
    /*
    @Entity
    @Table(name = "quartos")
    public class Quarto {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(nullable = false)
        private String tipo;

        @Column(name = "preco_diaria", nullable = false)
        private double precoDiaria;

        @Column(nullable = false)
        private int numero;

        @Column(nullable = false)
        private int andar;

        @Column
        private int capacidade;

        // Getters e Setters
} */
}
