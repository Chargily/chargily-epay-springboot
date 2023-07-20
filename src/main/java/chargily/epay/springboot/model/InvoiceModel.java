package chargily.epay.springboot.model;

import chargily.epay.springboot.enums.Mode;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class InvoiceModel {
    @NotNull
    @Size(min = 3, message = "Client name should be at least 3 characters long")
    private String client;

    @NotNull
    @Email(message = "Please provide a valid email")
    private String client_email;

    @NotNull
    private String invoice_number;

    @NotNull
    @DecimalMin(value = "75.0", message = "amount should be at least 75")
    private BigDecimal amount;

    @NotNull
    @Min(value = 0, message = "Discount percentage should be at least 0%")
    @Max(value = 100, message = "Discount percentage should not be more than 99.99%")
    private Double discount;

    @URL
    @NotNull
    private String back_url;

    @URL
    @NotNull
    private String webhook_url;

    @NotNull
    private Mode mode;

    @NotNull
    private String comment;
}
