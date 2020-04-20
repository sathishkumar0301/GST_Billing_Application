package gst_billing.fresherpro.examly.com;

import java.util.Optional;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@RequestMapping(path="/product")
@Controller
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;

    Product cproduct;
    double amount=0;
    String error="";
    ArrayList<searchResults> search_results = new ArrayList<searchResults>();


	@PostMapping(path="/add")
	public String addProduct(@RequestParam String productName,@RequestParam Double productPrice,
			@RequestParam Integer productGst) {
		Product p = new Product();
		p.setProductName(productName);
		p.setProductPrice(productPrice);
		p.setProductGst(productGst);
		productRepository.save(p);		
		return "redirect:/product/all";
	}
	
	@RequestMapping("/{id}")
	public String getProduct(@PathVariable Integer id,Model model) {
		model.addAttribute("product", productRepository.findByProductCode(id));
        model.addAttribute("total",amount);
        model.addAttribute("error",error);
		return "product";
	}
    @RequestMapping("/choose/{id}")
    public String getSuggestion(@PathVariable Integer id,Model model)
    {
        cproduct = productRepository.findByProductCode(id);
        model.addAttribute("product",cproduct);
        model.addAttribute("total",amount);
        model.addAttribute("error",error);
        model.addAttribute("sproducts",search_results);
        error="";
        return "billing";
    }

    @PostMapping("/addProduct")
    public String addProductToList(Model model,@RequestParam Double quantity)
    {
        if(cproduct!=null && cproduct.getProductName()!=null)
        {
            searchResults temp = new searchResults();
            temp.product = cproduct;
            temp.quantity = quantity;
            double amt = cproduct.getProductPrice() * quantity;
            amt=amt+(amt/cproduct.getProductGst());
            amount=amount+amt;
            temp.amount = amt;
            search_results.add(temp);
            cproduct=null;
            error="Item added!!";
        }
        else error="Choose one item before proceeding !!!";

            model.addAttribute("sproducts",search_results);        
            model.addAttribute("error",error);
            model.addAttribute("product",new Product());
            model.addAttribute("total",amount);
            
        
        return "billing";
    }

	
	@DeleteMapping("/delete/{id}")
	public String deleteProduct(@PathVariable Integer id) {
	    productRepository.deleteByProductCode(id);
		return "redirect:/product/all";
	}
	
	@PostMapping(path="/update")
	public String updateProduct(@RequestParam Integer productCode,@RequestParam String productName,@RequestParam Double productPrice,
			@RequestParam Integer productGst) {
		Product p = new Product();
		p.setProductCode(productCode);
		p.setProductName(productName);
		p.setProductGst(productGst);
		p.setProductPrice(productPrice);
		productRepository.save(p);
		return "redirect:/product/all";
	}
	
	@GetMapping(path="/all")
	public String getAllProducts(Model model){
		model.addAttribute("products",productRepository.findAll());
        model.addAttribute("total",amount);
        model.addAttribute("error",error);
		return "products";
	}

    @GetMapping(path="/billing")
    public String goBilling(Model model)
    {
        error="";
        model.addAttribute("error",error);
        model.addAttribute("product",new Product());
        model.addAttribute("total",amount);
        model.addAttribute("sproducts",search_results);
        return "billing";
    }

    @PostMapping(path="/search")
	public String addProduct(Model model,@RequestParam String productName) {
        List<Product> results = productRepository.findAll();
        ArrayList<Product> rproducts = new ArrayList<Product>();
        for(int i=0;i<results.size();i++)
        {
            Product temp = results.get(i);
            if(temp.getProductName().contains(productName) || String.valueOf(temp.getProductCode()).contains(productName))
             rproducts.add(temp);
        }
        if(rproducts.size()==0) error="No results found";
        else error="No of results found : "+rproducts.size();
        model.addAttribute("error",error);
        model.addAttribute("product",new Product());
        model.addAttribute("total",amount);
        model.addAttribute("sproducts",search_results);
        model.addAttribute("rproducts",rproducts);		
		return "billing";
	}



}
