package views.telasADM;

import controller.UsuarioController;
import dto.UsuarioDTO;
import utils.telas.ValidarCampos;
import views.ObjetosTelas.Botao;
import views.ObjetosTelas.EspacoTexto;
import views.PainelPadrao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AtualizarCliente extends PainelPadrao {
    private EspacoTexto nome = new EspacoTexto();
    private EspacoTexto email = new EspacoTexto();
    private EspacoTexto senha = new EspacoTexto();
    private EspacoTexto telefone = new EspacoTexto();
    private EspacoTexto cpf = new EspacoTexto();
    private EspacoTexto pesquisar = new EspacoTexto();
    private Botao atualizar = new Botao(false);
    private Botao limpar = new Botao(false);
    private Botao lupa = new Botao(false);


    public AtualizarCliente(){
        objetos();
        ouvintes();
        fundo("atualizarCliente");
    }

    private void ouvintes() {
        atualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pegarNome = nome.getText();
                String pegarSenha = senha.getText();
                String pegarCPF = cpf.getText();
                String pegarEmail = email.getText();
                String pegarTelefone = telefone.getText();

                UsuarioDTO dto = new UsuarioDTO(pegarNome, pegarEmail, pegarCPF, pegarTelefone, pegarSenha, false);

                if (UsuarioController.atualizarCliente(dto)){
                    JOptionPane.showMessageDialog(null, "Cliente atualizado!");
                } else {
                    JOptionPane.showMessageDialog(null,"Erro", "Não foi possível atualizar", JOptionPane.ERROR_MESSAGE);
                }
                return;
            }
        });

        limpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nome.setText("");
                email.setText("");
                senha.setText("");
                telefone.setText("");
                cpf.setText("");

            }
        });

        lupa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (ValidarCampos.isVazio(pesquisar) || !ValidarCampos.isCPFValido(pesquisar)) {
                    JOptionPane.showMessageDialog(null, "Por favor, digite um CPF válido.");
                    return;
                }

                UsuarioDTO cliente = UsuarioController.resgatarCliente(pesquisar.getText());
                if (cliente != null){
                    nome.setText(cliente.getNome());
                    email.setText(cliente.getEmail());
                    telefone.setText(cliente.getTelefone());
                    cpf.setText(cliente.getCPF());
                    senha.setText(cliente.getSenha());
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null,"Não foi possível encontrar o CPF digitado", "Cliente não encontrado", JOptionPane.ERROR_MESSAGE);
                    pesquisar.setText("");
                    limpar.doClick();
                    return;
                }
            }
        });
    }


    private void objetos() {
        int a = 193,c = 299,d = 50;
        nome.setBounds(a,173,c,d);
        email.setBounds(a,253,c,d);
        senha.setBounds(a,333,c,d);
        telefone.setBounds(635,173,c,d);
        cpf.setBounds(635,253,c,d);
        cpf.setEnabled(false);
        pesquisar.setBounds(158,80,812,63);
        atualizar.setBounds(192,510,296,85);
        limpar.setBounds(584,510,296,85);
        lupa.setBounds(101,83,56,56);
        add(nome);
        add(email);
        add(senha);
        add(telefone);
        add(cpf);
        add(pesquisar);
        add(atualizar);
        add(limpar);
        add(lupa);
    }
}
