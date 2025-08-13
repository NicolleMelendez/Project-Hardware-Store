package com.hardware.hardwareStore.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El cliente es requerido")
    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @NotNull(message = "El empleado es requerido")
    @ManyToOne
    @JoinColumn(name = "id_employee")
    private Employee employee;

    @NotNull(message = "La fecha es requerida")
    @PastOrPresent(message = "La fecha debe ser hoy o en el pasado")
    @Column(name = "date_sale")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateSale;

    @NotNull(message = "El total es requerido")
    @Min(value = 0, message = "El total debe ser mayor o igual a 0")
    private Integer total;

    @NotBlank(message = "El estado es requerido")
    @Pattern(regexp = "COMPLETADA|PENDIENTE|CANCELADA",
            message = "Estado no válido. Debe ser: COMPLETADA, PENDIENTE o CANCELADA")
    private String status;

    public Sale() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getDateSale() {
        return dateSale;
    }

    public void setDateSale(Date dateSale) {
        this.dateSale = dateSale;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
