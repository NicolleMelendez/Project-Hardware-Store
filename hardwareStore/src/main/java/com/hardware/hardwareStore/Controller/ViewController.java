package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Repository.*;
import com.hardware.hardwareStore.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import org.springframework.ui.Model;

@Controller
public class ViewController {

    // Repositorios (inyectados)
    @Autowired private ClientRepository clientRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private SupplierRepository supplierRepository;
    @Autowired private InventoryRepository inventoryRepository;
    @Autowired private EntryRepository entryRepository;
    @Autowired private IssueRepository issueRepository;
    @Autowired private OrderBuyRepository orderBuyRepository;
    @Autowired private OrderDetailRepository orderDetailRepository;
    @Autowired private SaleRepository saleRepository;
    @Autowired private SaleDetailRepository saleDetailRepository;

    /* -------------------- DASHBOARD -------------------- */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /* -------------------- CLIENT -------------------- */
    @GetMapping("/client")
    public String clientPage(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        return "client/index";
    }

    @PostMapping("/clients/save")
    public String saveClient(@ModelAttribute Client client) {
        // Si es nuevo cliente, establecer fecha actual
        if(client.getId() == null) {
            client.setDateClient(new Date());
        } else {
            // Si es edición, mantener la fecha original
            Client existing = clientRepository.findById(client.getId()).orElse(null);
            if(existing != null) {
                client.setDateClient(existing.getDateClient());
            }
        }
        clientRepository.save(client);
        return "redirect:/client";
    }

    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
        return "redirect:/client";
    }

    /* -------------------- EMPLOYEE -------------------- */
    @GetMapping("/employee")
    public String employeePage(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employee/index";
    }

    @PostMapping("/employees/save")
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employee";
    }

    @PostMapping("/delete/employee/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/employee";
    }

    /* -------------------- SUPPLIER -------------------- */
    @GetMapping("/supplier")
    public String supplierPage(Model model) {
        model.addAttribute("suppliers", supplierRepository.findAll());
        return "supplier/index";
    }

    @PostMapping("/suppliers/save")
    public String saveSupplier(@ModelAttribute Supplier supplier) {
        supplierRepository.save(supplier);
        return "redirect:/supplier";
    }

    @PostMapping("/delete/supplier/{id}")
    public String deleteSupplier(@PathVariable Long id) {
        supplierRepository.deleteById(id);
        return "redirect:/supplier";
    }

    /* -------------------- INVENTORY -------------------- */
    @GetMapping("/inventory")
    public String inventoryPage(Model model) {
        model.addAttribute("inventories", inventoryRepository.findAll());
        model.addAttribute("suppliers", supplierRepository.findAll());
        return "inventory/index";
    }

    @PostMapping("/inventory/save")
    public String saveInventory(@ModelAttribute Inventory inventory) {
        inventoryRepository.save(inventory);
        return "redirect:/inventory";
    }

    @PostMapping("/delete/inventory/{id}")
    public String deleteInventory(@PathVariable Long id) {
        inventoryRepository.deleteById(id);
        return "redirect:/inventory";
    }

    /* -------------------- ENTRY -------------------- */
    @GetMapping("/entry")
    public String entryPage(Model model) {
        model.addAttribute("entries", entryRepository.findAll());
        model.addAttribute("inventories", inventoryRepository.findAll());
        model.addAttribute("suppliers", supplierRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        return "entry/index";
    }

    @PostMapping("/entries/save")
    public String saveEntry(@ModelAttribute Entry entry) {
        entryRepository.save(entry);
        return "redirect:/entry";
    }

    @PostMapping("/delete/entry/{id}")
    public String deleteEntry(@PathVariable Long id) {
        entryRepository.deleteById(id);
        return "redirect:/entry";
    }

    /* -------------------- ISSUE -------------------- */
    @GetMapping("/issue")
    public String issuePage(Model model) {
        model.addAttribute("issues", issueRepository.findAll());
        model.addAttribute("inventories", inventoryRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        return "issue/index";
    }

    @PostMapping("/issues/save")
    public String saveIssue(@ModelAttribute Issue issue) {
        issueRepository.save(issue);
        return "redirect:/issue";
    }

    @PostMapping("/delete/issue/{id}")
    public String deleteIssue(@PathVariable Long id) {
        issueRepository.deleteById(id);
        return "redirect:/issue";
    }

    /* -------------------- ORDERBUY -------------------- */
    @GetMapping("/orderbuy")
    public String orderBuyPage(Model model) {
        model.addAttribute("orders", orderBuyRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        return "orderbuy/index";
    }

    @PostMapping("/orderbuy/save")
    public String saveOrderBuy(@ModelAttribute OrderBuy orderBuy) {
        orderBuyRepository.save(orderBuy);
        return "redirect:/orderbuy";
    }

    @PostMapping("/delete/orderbuy/{id}")
    public String deleteOrderBuy(@PathVariable Long id) {
        orderBuyRepository.deleteById(id);
        return "redirect:/orderbuy";
    }

    /* -------------------- ORDERDETAIL -------------------- */
    @GetMapping("/orderdetail")
    public String orderDetailPage(Model model) {
        model.addAttribute("details", orderDetailRepository.findAll());
        model.addAttribute("orders", orderBuyRepository.findAll());
        model.addAttribute("inventories", inventoryRepository.findAll());
        return "orderdetail/index";
    }

    @PostMapping("/orderdetail/save")
    public String saveOrderDetail(@ModelAttribute OrderDetail detail) {
        orderDetailRepository.save(detail);
        return "redirect:/orderdetail";
    }

    @PostMapping("/delete/orderdetail/{id}")
    public String deleteOrderDetail(@PathVariable Long id) {
        orderDetailRepository.deleteById(id);
        return "redirect:/orderdetail";
    }
    /* -------------------- SALE -------------------- */
    @GetMapping("/sale")
    public String salePage(Model model) {
        model.addAttribute("sales", saleRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        return "sale/index";
    }

    @GetMapping("/api/sale/{id}")
    @ResponseBody
    public Sale getSaleById(@PathVariable Long id) {
        return saleRepository.findById(id).orElse(null);
    }

    @PostMapping("/sales/save")
    public String saveSale(@ModelAttribute Sale sale) {
        // Si es nueva venta, establecer fecha actual si no viene
        if(sale.getId() == null && sale.getDateSale() == null) {
            sale.setDateSale(new Date());
        } else if(sale.getId() != null) {
            // Si es edición, mantener la fecha original si no se cambió
            Sale existing = saleRepository.findById(sale.getId()).orElse(null);
            if(existing != null && sale.getDateSale() == null) {
                sale.setDateSale(existing.getDateSale());
            }
        }

        saleRepository.save(sale);
        return "redirect:/sale";
    }

    @PostMapping("/delete/sale/{id}")
    public String deleteSale(@PathVariable Long id) {
        saleRepository.deleteById(id);
        return "redirect:/sale";
    }


    /* -------------------- SALEDETAIL -------------------- */
    @GetMapping("/sale-detail")
    public String saleDetailPage(Model model) {
        model.addAttribute("details", saleDetailRepository.findAll());
        model.addAttribute("sales", saleRepository.findAll());
        model.addAttribute("inventories", inventoryRepository.findAll());
        return "saledetail/index";
    }

    @PostMapping("/sale-details/save")
    public String saveSaleDetail(@ModelAttribute SaleDetail detail) {
        saleDetailRepository.save(detail);
        return "redirect:/sale-detail";
    }

    @PostMapping("/delete/sale-detail/{id}")
    public String deleteSaleDetail(@PathVariable Long id) {
        saleDetailRepository.deleteById(id);
        return "redirect:/sale-detail";
    }
}
