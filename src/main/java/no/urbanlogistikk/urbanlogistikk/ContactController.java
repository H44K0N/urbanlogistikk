package no.urbanlogistikk.urbanlogistikk;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {
    private final MailService mail;
    public ContactController(MailService mail) { this.mail = mail; }

    @PostMapping(path = "/contact", consumes = "application/x-www-form-urlencoded")
    public String submit(@Valid ContactForm form,
                         @RequestParam(value = "website", required = false) String website) {
        // Honeypot (valgfritt). Hvis du vil bruke det, legg et hidden input name="website" i skjemaet.
        if (website != null && !website.isBlank()) return "redirect:/";
        mail.send(form);
        return "redirect:/?ok=1"; // eller vis en takk‑side
    }
}