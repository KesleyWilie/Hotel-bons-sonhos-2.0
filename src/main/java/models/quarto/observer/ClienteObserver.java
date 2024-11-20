package models.quarto.observer;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class ClienteObserver implements IObserver {

    private String emailCliente;

    public ClienteObserver(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    @Override
    public void atualizar() {
        enviarEmail("Adicionamos um novo quarto! Confira no nosso app :)");
    }

    private void enviarEmail(String mensagem) {
        // Configuração do servidor SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); 
        props.put("mail.smtp.port", "587");

        
        String email = System.getenv("EMAIL");  // Substitua pelo seu email
        String senha = System.getenv("SENHAEMAIL");  // Substitua pela sua senha ou use um app password

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, senha);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("hotelbonssonhos@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailCliente));
            message.setSubject("Quarto disponível!");
            message.setText(mensagem);

            Transport.send(message);

            System.out.println("Email enviado com sucesso para: " + emailCliente);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}