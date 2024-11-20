package abstract_factory;

public abstract class Quartos {
    private int numero;
    private int andar;
    private double precoDiaria;
    private String tipo;

    public Quartos(int numero, int andar, double precoDiaria, String tipo) {
        this.numero = numero;
        this.andar = andar;
        this.precoDiaria = precoDiaria;
        this.tipo = tipo;
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
    public String getTipo(){
        return tipo;
    }
    public void setTipo(String tipo){

    }

    public abstract String getDescricao();

}