package no.urbanlogistikk.urbanlogistikk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender mail;
    private final String to;
    private final String from;

    public MailService(JavaMailSender mail,
                       @Value("${app.contact.to}") String to,
                       @Value("${spring.mail.username}") String from) {
        this.mail = mail; this.to = to; this.from = from;
    }

    public void send(ContactForm r) {
        var m = new SimpleMailMessage();
        m.setFrom(from);                     // må matche pålogget konto hos Outlook
        m.setTo(to);                         // hvor henvendelser leveres
        m.setReplyTo(r.epost());             // svar går til kunden
        m.setSubject("Kontaktforespørsel: " + r.navn());
        m.setText("""
      Navn: %s
      E-post: %s
      Telefon: %s

      Behov:
      %s
      """.formatted(r.navn(), r.epost(), r.telefon() == null ? "" : r.telefon(), r.behov()));
        mail.send(m);
    }
}
