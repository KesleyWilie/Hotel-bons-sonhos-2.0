package views.telasADM;

import controller.UsuarioController;
import dto.UsuarioDTO;
import utils.telas.ValidarCampos;
import views.ObjetosTelas.Botao;
import views.ObjetosTelas.EspacoTexto;
import views.ObjetosTelas.TextosTelas;
import views.PainelPadrao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoverCliente extends PainelPadrao {
    private EspacoTexto dado = new EspacoTexto();
    private Botao remover = new Botao(false);
    private Botao pesquisar = new Botao(false);
    private TextosTelas nome = new TextosTelas();
    private TextosTelas email = new TextosTelas();
    private TextosTelas telefone = new TextosTelas();
    private TextosTelas cpf = new TextosTelas();


    public RemoverCliente(){
        ouvintes();
        objetos();
        fundo("remover cliente");
    }

    private void ouvintes() {
        remover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cpfCliente = dado.getText();
                String resposta = UsuarioController.removerCliente(cpfCliente);

                if (resposta.equals("Usuário removido com sucesso")) {
                    JOptionPane.showMessageDialog(null, "Cliente removido com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(null, resposta, "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        pesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (ValidarCampos.isVazio(dado) || !ValidarCampos.isCPFValido(dado)) {
                    JOptionPane.showMessageDialog(null, "Por favor, digite um CPF válido.");
                    return;
                }

                UsuarioDTO cliente = UsuarioController.resgatarCliente(dado.getText());
                if (cliente != null){
                    nome.setText(cliente.getNome());
                    email.setText(cliente.getEmail());
                    telefone.setText(cliente.getTelefone());
                    cpf.setText(cliente.getCPF());
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null,"Não foi possível encontrar o CPF digitado", "Cliente não encontrado", JOptionPane.ERROR_MESSAGE);
                    pesquisar.setText("");
                    return;
                }
            }
        });
    }


    private void objetos() {
        int c = 300, d = 28;
        dado.setBounds(158,79,812,63);
        remover.setBounds(388,510,296,84);
        pesquisar.setBounds(101,83,56,56);
        telefone.setBounds(720,184,c,d);
        cpf.setBounds(720,264,c,d);
        email.setBounds(220,263,c,d);
        nome.setBounds(220,184,c,d);
        add(dado);
        add(remover);
        add(pesquisar);
        add(nome);
        add(email);
        add(telefone);
        add(cpf);
    }
}
