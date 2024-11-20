package models.quarto.observer;

import java.util.ArrayList;
import java.util.List;

public class QuartoNovoSubject {

    private List<IObserver> observadores = new ArrayList<>();

    public void adicionarObserver(IObserver observer) {
        observadores.add(observer);
    }

    public void removerObserver(IObserver observer) {
        observadores.remove(observer);
    }

    public void notificarObservers() {
        for (IObserver observer : observadores) {
            observer.atualizar();
        }
    }
}

