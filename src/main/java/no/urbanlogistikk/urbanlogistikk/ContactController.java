package no.urbanlogistikk.urbanlogistikk;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class ContactController {
    private final MailService mail;
    public ContactController(MailService mail){ this.mail = mail; }

    @PostMapping(path="/contact", consumes="application/x-www-form-urlencoded")
    public String submit(@Valid @ModelAttribute ContactForm form, BindingResult br) {
        // Honeypot
        if (form.website() != null && !form.website().isBlank()) return "redirect:/";
        if (br.hasErrors()) return "redirect:/?ok=0&reason=val";

        try {
            mail.send(form);
            return "redirect:/?ok=1";
        } catch (MailException ex) {
            log.error("E-post sending feilet", ex);
            return "redirect:/?ok=0&reason=mail";
        } catch (Exception ex) {
            log.error("Uventet feil i /contact", ex);
            return "redirect:/?ok=0&reason=err";
        }
    }
}
