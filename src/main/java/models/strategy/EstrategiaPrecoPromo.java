package models.strategy;

public class EstrategiaPrecoPromo implements IEstrategiaDePrecos {
    public double calcularPreco(double precoBase, int numeroDeNoites) {
        double descontoPromocional = 0.8; 
        return precoBase * descontoPromocional * numeroDeNoites;
    }

}
