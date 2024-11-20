package views;

import views.ObjetosTelas.Botao;
import views.telasADM.*;
import views.telasCliente.TelaMenuCliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class JanelaPadrao extends JFrame {
    private Botao fechar = new Botao(false);
    private AdicionarQuartoPainel adicionarQuarto = new AdicionarQuartoPainel();
    private RemoverQuarto removerQuarto = new RemoverQuarto();
    private RemoverCliente removerCliente = new RemoverCliente();
    private AtualizarCliente atualizarCliente = new AtualizarCliente();
    private InformacoesCliente informacoesCliente = new InformacoesCliente();
    private JLabel label;


    public JanelaPadrao(){
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setUndecorated(true);
        setSize(1280,720);
        setLocationRelativeTo(null);
        paineis();
        invisivel();
        setVisible(true);
    }

    private void paineis() {
        int a = 207, b = 0, c = 1072, d = 720;
        adicionarQuarto.setBounds(a,b,c,d);
        removerQuarto.setBounds(a,b,c,d);
        removerCliente.setBounds(a,b,c,d);
        atualizarCliente.setBounds(a,b,c,d);
        informacoesCliente.setBounds(a,b,c,d);
        add(adicionarQuarto);
        add(removerQuarto);
        add(removerCliente);
        add(atualizarCliente);
        add(informacoesCliente);
    }


    public void mostrar(Botao botao,JPanel painel){
        botao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                invisivel();
                painel.setVisible(true);
                repaint();
            }
        });
    }

    public void voltarMenu(String CPF){
        dispose();
        new TelaMenuCliente(CPF);
    }

    public AdicionarQuartoPainel getAdicionarQuarto() {
        return adicionarQuarto;
    }

    public RemoverQuarto getRemoverQuarto() {
        return removerQuarto;
    }

    public RemoverCliente getRemoverCliente() {
        return removerCliente;
    }

    public AtualizarCliente getAtualizarCliente() {
        return atualizarCliente;
    }

    public InformacoesCliente getInformacoesCliente() {
        return informacoesCliente;
    }

    private void ouvinte() {
        fechar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void invisivel(){
        adicionarQuarto.setVisible(false);
        removerQuarto.setVisible(false);
        removerCliente.setVisible(false);
        atualizarCliente.setVisible(false);
        informacoesCliente.setVisible(false);
    }

    public void fechar(int a,int b,int c, int d) {
        fechar.setBounds(a,b,c,d);
        add(fechar);
        ouvinte();
    }

    public void remover(){
        remove(label);
    }


    public void fundo(String foto) {
        ImageIcon imagem = new ImageIcon("src/main/java/utils/imagens/"+foto+".png");
        label = new JLabel();
        label.setIcon(imagem);
        label.setBounds(0,0,getWidth(),getHeight());
        add(label);
        repaint();
    }
}
