package abstract_factory;

public class QuartoDeLuxo extends Quartos{
    public QuartoDeLuxo(int numero, int andar, double precoDiaria, String tipo) {
        super(numero, andar, precoDiaria, tipo);
    }

    
    public String getDescricao(){
        return "Quarto de Luxo no andar " + getAndar() + " com número " + getNumero();
    }
}
