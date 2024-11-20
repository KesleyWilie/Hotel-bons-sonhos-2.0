package abstract_factory;

public class QuartoSuite extends Quartos {
    public QuartoSuite(int numero, int andar, double precoDiaria, String tipo) {
        super(numero, andar, precoDiaria, tipo);
    }

    public String getDescricao() {
        return "Quarto de Suíte no andar " + getAndar() + " com número " + getNumero();
    }
}
