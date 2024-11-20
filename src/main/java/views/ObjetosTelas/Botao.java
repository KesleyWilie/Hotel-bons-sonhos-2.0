package views.ObjetosTelas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Botao extends JButton {
    public Botao(Boolean visibilidade){
        setBackground(Color.white);
        setBorder(new EmptyBorder(0,0,0,0));
        setOpaque(false);
        setContentAreaFilled(visibilidade);
    }
}
