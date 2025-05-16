package itmo.blps.lab1.workflow.delegates;

import itmo.blps.lab1.entity.Payment;
import itmo.blps.lab1.entity.Product;
import itmo.blps.lab1.service.PaymentService;
import itmo.blps.lab1.service.ProductService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component("productDelegate")
public class ProductDelegate implements JavaDelegate {
    @Autowired
    private ProductService productService;

    @Override
    public void execute(DelegateExecution execution) {
        List<String> products = productService.getAllProducts().stream().map(Product::getName).toList();
        execution.setVariable("products", products);
    }
}
