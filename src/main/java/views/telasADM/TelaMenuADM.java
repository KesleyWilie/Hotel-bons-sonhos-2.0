package views.telasADM;

import views.JanelaPadrao;
import views.ObjetosTelas.Botao;

public class TelaMenuADM extends JanelaPadrao {
    private Botao adicionarQuarto = new Botao(false);
    private Botao removerQuarto = new Botao(false);
    private Botao removerCliente = new Botao(false);
    private Botao atualizarCliente = new Botao(false);
    private Botao infomacoesCliente = new Botao(false);


    public TelaMenuADM(){
        objetos();
        ouvintes();
        invisivel();
        fundo("menu ADM");
    }

    private void ouvintes() {
        mostrar(adicionarQuarto,getAdicionarQuarto());
        mostrar(removerQuarto,getRemoverQuarto());
        mostrar(removerCliente,getRemoverCliente());
        mostrar(atualizarCliente,getAtualizarCliente());
        mostrar(infomacoesCliente,getInformacoesCliente());


    }

    private void objetos() {
        int a = 19, c =175, d =53;
        adicionarQuarto.setBounds(a,269,c,d);
        removerQuarto.setBounds(a,338,c,d);
        removerCliente.setBounds(a,406,c,d);
        atualizarCliente.setBounds(a,474,c,d);
        infomacoesCliente.setBounds(a,542,c,64);
        add(adicionarQuarto);
        add(removerQuarto);
        add(removerCliente);
        add(atualizarCliente);
        add(infomacoesCliente);
    }
}
