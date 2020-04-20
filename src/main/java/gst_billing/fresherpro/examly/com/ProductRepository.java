package gst_billing.fresherpro.examly.com;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    Product findByProductCode(Integer id);
    Product findByProductName(String name);
    void deleteByProductCode(Integer id);
}
