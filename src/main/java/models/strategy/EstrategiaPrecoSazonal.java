package models.strategy;

public class EstrategiaPrecoSazonal implements IEstrategiaDePrecos {

    @Override
    public double calcularPreco(double precoBase, int numeroDeNoites) {
        double fatorSazonal = 1.2; 
        return precoBase * fatorSazonal * numeroDeNoites;
    }
        
        
    }
    


