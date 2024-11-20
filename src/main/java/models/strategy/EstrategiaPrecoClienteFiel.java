package models.strategy;

public class EstrategiaPrecoClienteFiel implements IEstrategiaDePrecos {

    @Override
    public double calcularPreco(double precoBase, int numeroDeNoites, int quantReservas) {
        
        double descontoFiel = Math.min(0.1 * (quantReservas / 10), 0.3); // Limite de 30% de desconto que isso daqui não é bagunça!
        return precoBase * (1 - descontoFiel) * numeroDeNoites;
    }

    @Override
    public double calcularPreco(double precoBase, int numeroDeNoites) {
       
        return calcularPreco(precoBase, numeroDeNoites, 0);
    }
}
