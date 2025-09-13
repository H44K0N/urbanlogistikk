package no.urbanlogistikk.urbanlogistikk;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactForm(
        @NotBlank @Size(max = 80) String navn,
        @NotBlank @Email @Size(max = 120) String epost,
        @Size(max = 30) String telefon,
        @NotBlank @Size(max = 2000) String behov,
        String website // honeypot (optional)
) {}
