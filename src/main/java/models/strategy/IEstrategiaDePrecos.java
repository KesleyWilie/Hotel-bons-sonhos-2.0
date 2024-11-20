package models.strategy;

public interface IEstrategiaDePrecos {
    double calcularPreco(double precoBase, int numeroDeNoites);
    
   
    default double calcularPreco(double precoBase, int numeroDeNoites, int quantReservas) {
       
        return calcularPreco(precoBase, numeroDeNoites);
    }
}
