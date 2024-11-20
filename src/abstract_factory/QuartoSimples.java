package abstract_factory;

public class QuartoSimples extends Quartos {
    public QuartoSimples(int numero, int andar, double precoDiaria, String tipo) {
        super(numero, andar, precoDiaria,tipo);
    }

    public String getDescricao() {
        return "Quarto Simples no andar " + getAndar() + " com número " + getNumero();
    }

}
