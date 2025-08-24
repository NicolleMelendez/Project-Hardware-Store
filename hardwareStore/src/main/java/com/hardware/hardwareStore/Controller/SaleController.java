package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.ClientService;
import com.hardware.hardwareStore.Service.EmployeeService;
import com.hardware.hardwareStore.Service.InventoryService;
import com.hardware.hardwareStore.Service.SaleService;
import com.hardware.hardwareStore.model.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private InventoryService inventoryService;

    /**
     * Muestra la página principal de gestión de ventas.
     * Carga todas las ventas, clientes, empleados e inventario en el modelo.
     * * @param model El modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf (sale/index).
     */
    @GetMapping
    public String salePage(Model model) {
        model.addAttribute("sales", saleService.getAllSale());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("inventories", inventoryService.findAll()); // Necesario para el modal de añadir productos
        return "sale/index";
    }

    /**
     * Procesa la creación de una nueva venta desde el formulario.
     * * @param sale La nueva entidad de venta a crear.
     * @param redirectAttributes Para enviar mensajes flash a la vista.
     * @return Una redirección a la página principal de ventas.
     */
    @PostMapping("/save")
    public String saveSale(@ModelAttribute Sale sale, RedirectAttributes redirectAttributes) {
        try {
            saleService.save(sale);
            redirectAttributes.addFlashAttribute("success", "Venta creada exitosamente. Ahora puedes añadir productos.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la venta: " + e.getMessage());
        }
        return "redirect:/sales";
    }

    /**
     * Procesa la actualización del estado de una venta existente.
     * * @param id El ID de la venta a modificar.
     * @param status El nuevo estado para la venta.
     * @param redirectAttributes Para enviar mensajes flash a la vista.
     * @return Una redirección a la página principal de ventas.
     */
    @PostMapping("/updateStatus")
    public String updateSaleStatus(@RequestParam("id") Long id,
                                   @RequestParam("status") String status,
                                   RedirectAttributes redirectAttributes) {
        try {
            saleService.updateStatus(id, status);
            redirectAttributes.addFlashAttribute("success", "Estado de la venta actualizado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el estado: " + e.getMessage());
        }
        return "redirect:/sales";
    }
}