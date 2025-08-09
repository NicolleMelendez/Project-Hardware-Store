package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Repository.*;
import com.hardware.hardwareStore.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
<<<<<<< Updated upstream
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
=======
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
>>>>>>> Stashed changes

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
        model.addAttribute("employee", new Employee());
        return "employee/index";
    }

    @PostMapping("/employee/save")
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employee";
    }

    @PostMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/employee";
    }

    @GetMapping("/employee/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("employee", employeeRepository.findById(id).orElse(new Employee()));
        return "employee/index";
    }

    /* -------------------- SUPPLIER -------------------- */
    @GetMapping("/supplier")
    public String supplierPage(Model model) {
        model.addAttribute("suppliers", supplierRepository.findAll());
        model.addAttribute("supplier", new Supplier());
        return "supplier/index";
    }

    @PostMapping("/supplier/save")
    public String saveSupplier(@ModelAttribute Supplier supplier) {
        supplierRepository.save(supplier);
        return "redirect:/supplier";
    }

    @PostMapping("/supplier/delete/{id}")
    public String deleteSupplier(@PathVariable Long id) {
        supplierRepository.deleteById(id);
        return "redirect:/supplier";
    }

    @GetMapping("/supplier/edit/{id}")
    public String editSupplier(@PathVariable Long id, Model model) {
        model.addAttribute("suppliers", supplierRepository.findAll());
        model.addAttribute("supplier", supplierRepository.findById(id).orElse(new Supplier()));
        return "supplier/index";
    }

    /* -------------------- INVENTORY -------------------- */
    @GetMapping("/inventory")
    public String inventoryPage(Model model) {
        model.addAttribute("inventories", inventoryRepository.findAll());
        model.addAttribute("suppliers", supplierRepository.findAll()); // dropdown
        model.addAttribute("inventory", new Inventory());
        return "inventory/index";
    }

    @PostMapping("/inventory/save")
    public String saveInventory(@ModelAttribute Inventory inventory) {
        inventoryRepository.save(inventory);
        return "redirect:/inventory";
    }

    @PostMapping("/inventory/delete/{id}")
    public String deleteInventory(@PathVariable Long id) {
        inventoryRepository.deleteById(id);
        return "redirect:/inventory";
    }

    @GetMapping("/inventory/edit/{id}")
    public String editInventory(@PathVariable Long id, Model model) {
        model.addAttribute("inventories", inventoryRepository.findAll());
        model.addAttribute("suppliers", supplierRepository.findAll());
        model.addAttribute("inventory", inventoryRepository.findById(id).orElse(new Inventory()));
        return "inventory/index";
    }

    /* -------------------- ENTRY -------------------- */
    @GetMapping("/entry")
    public String entryPage(Model model) {
        model.addAttribute("entries", entryRepository.findAll());
        model.addAttribute("entry", new Entry());
        model.addAttribute("inventories", inventoryRepository.findAll());
        model.addAttribute("suppliers", supplierRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        return "entry/index";
    }

    @PostMapping("/entry/save")
    public String saveEntry(@ModelAttribute Entry entry) {
        entryRepository.save(entry);
        return "redirect:/entry";
    }

    @PostMapping("/entry/delete/{id}")
    public String deleteEntry(@PathVariable Long id) {
        entryRepository.deleteById(id);
        return "redirect:/entry";
    }

    @GetMapping("/entry/edit/{id}")
    public String editEntry(@PathVariable Long id, Model model) {
        model.addAttribute("entries", entryRepository.findAll());
        model.addAttribute("inventories", inventoryRepository.findAll());
        model.addAttribute("suppliers", supplierRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("entry", entryRepository.findById(id).orElse(new Entry()));
        return "entry/index";
    }

    /* -------------------- ISSUE -------------------- */
    @GetMapping("/issue")
    public String issuePage(Model model) {
        model.addAttribute("issues", issueRepository.findAll());
        model.addAttribute("issue", new Issue());
        model.addAttribute("inventories", inventoryRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        return "issue/index";
    }

    @PostMapping("/issue/save")
    public String saveIssue(@ModelAttribute Issue issue) {
        issueRepository.save(issue);
        return "redirect:/issue";
    }

    @PostMapping("/issue/delete/{id}")
    public String deleteIssue(@PathVariable Long id) {
        issueRepository.deleteById(id);
        return "redirect:/issue";
    }

    @GetMapping("/issue/edit/{id}")
    public String editIssue(@PathVariable Long id, Model model) {
        model.addAttribute("issues", issueRepository.findAll());
        model.addAttribute("inventories", inventoryRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("issue", issueRepository.findById(id).orElse(new Issue()));
        return "issue/index";
    }

    /* -------------------- ORDERBUY -------------------- */
    @GetMapping("/orderbuy")
    public String orderBuyPage(Model model) {
        model.addAttribute("orders", orderBuyRepository.findAll());
        model.addAttribute("orderBuy", new OrderBuy());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        return "orderbuy/index";
    }

    @PostMapping("/orderbuy/save")
    public String saveOrderBuy(@ModelAttribute OrderBuy orderBuy) {
        orderBuyRepository.save(orderBuy);
        return "redirect:/orderbuy";
    }

    @PostMapping("/orderbuy/delete/{id}")
    public String deleteOrderBuy(@PathVariable Long id) {
        orderBuyRepository.deleteById(id);
        return "redirect:/orderbuy";
    }

    @GetMapping("/orderbuy/edit/{id}")
    public String editOrderBuy(@PathVariable Long id, Model model) {
        model.addAttribute("orders", orderBuyRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("orderBuy", orderBuyRepository.findById(id).orElse(new OrderBuy()));
        return "orderbuy/index";
    }

    /* -------------------- ORDERDETAIL -------------------- */
    @GetMapping("/orderdetail")
    public String orderDetailPage(Model model) {
        model.addAttribute("details", orderDetailRepository.findAll());
        model.addAttribute("orderDetail", new OrderDetail());
        model.addAttribute("orders", orderBuyRepository.findAll());
        model.addAttribute("inventories", inventoryRepository.findAll());
        return "orderdetail/index";
    }

    @PostMapping("/orderdetail/save")
    public String saveOrderDetail(@ModelAttribute OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
        return "redirect:/orderdetail";
    }

    @PostMapping("/orderdetail/delete/{id}")
    public String deleteOrderDetail(@PathVariable Long id) {
        orderDetailRepository.deleteById(id);
        return "redirect:/orderdetail";
    }

    @GetMapping("/orderdetail/edit/{id}")
    public String editOrderDetail(@PathVariable Long id, Model model) {
        model.addAttribute("details", orderDetailRepository.findAll());
        model.addAttribute("orders", orderBuyRepository.findAll());
        model.addAttribute("inventories", inventoryRepository.findAll());
        model.addAttribute("orderDetail", orderDetailRepository.findById(id).orElse(new OrderDetail()));
        return "orderdetail/index";
    }

    /* -------------------- SALE -------------------- */
    @GetMapping("/sale")
    public String salePage(Model model) {
        model.addAttribute("sales", saleRepository.findAll());
        model.addAttribute("sale", new Sale());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        return "sale/index";
    }

    @PostMapping("/sale/save")
    public String saveSale(@ModelAttribute Sale sale) {
        saleRepository.save(sale);
        return "redirect:/sale";
    }

    @PostMapping("/sale/delete/{id}")
    public String deleteSale(@PathVariable Long id) {
        saleRepository.deleteById(id);
        return "redirect:/sale";
    }

    @GetMapping("/sale/edit/{id}")
    public String editSale(@PathVariable Long id, Model model) {
        model.addAttribute("sales", saleRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("sale", saleRepository.findById(id).orElse(new Sale()));
        return "sale/index";
    }

    /* -------------------- SALEDETAIL -------------------- */
    @GetMapping("/saledetail")
    public String saleDetailPage(Model model) {
        model.addAttribute("details", saleDetailRepository.findAll());
        model.addAttribute("saleDetail", new SaleDetail());
        model.addAttribute("sales", saleRepository.findAll());
        model.addAttribute("inventories", inventoryRepository.findAll());
        return "saledetail/index";
    }

    @PostMapping("/saledetail/save")
    public String saveSaleDetail(@ModelAttribute SaleDetail saleDetail) {
        saleDetailRepository.save(saleDetail);
        return "redirect:/saledetail";
    }

    @PostMapping("/saledetail/delete/{id}")
    public String deleteSaleDetail(@PathVariable Long id) {
        saleDetailRepository.deleteById(id);
        return "redirect:/saledetail";
    }

    @GetMapping("/saledetail/edit/{id}")
    public String editSaleDetail(@PathVariable Long id, Model model) {
        model.addAttribute("details", saleDetailRepository.findAll());
        model.addAttribute("sales", saleRepository.findAll());
        model.addAttribute("inventories", inventoryRepository.findAll());
        model.addAttribute("saleDetail", saleDetailRepository.findById(id).orElse(new SaleDetail()));
        return "saledetail/index";
    }
}
