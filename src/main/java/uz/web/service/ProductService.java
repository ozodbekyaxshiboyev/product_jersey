package uz.web.service;

import uz.web.bean.BeanFactory;
import uz.web.dao.ProductDao;
import uz.web.dao.ProductStatusDao;
import uz.web.dao.ProductTypeDao;
import uz.web.dto.ProductDto;
import uz.web.entity.Product;
import uz.web.entity.ProductStatus;
import uz.web.entity.ProductType;

import java.util.List;
import java.util.Optional;

public class ProductService {
    private final ProductDao productDao = BeanFactory.productDao;
    private final ProductTypeDao productTypeDao = BeanFactory.productTypeDao;
    private final ProductStatusDao productStatusDao = BeanFactory.productStatusDao;

    public Optional<Product> findById(long id) {
        return productDao.findById(id);
    }

    public List<Product> findAll(int page, int size) {
        return productDao.findAll(page, size);
    }

    public Product save(ProductDto product) {
        Optional<ProductType> productType = productTypeDao.findById(product.getProductType());
        Optional<ProductStatus> productStatus = productStatusDao.findById(product.getProductStatus());
        if (productType.isEmpty() || productStatus.isEmpty()) return null;
        Product p = Product.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .productType(productType.get())
                .productStatus(productStatus.get())
                .build();
        return productDao.save(p);
    }

    public boolean delete(long id) {
        return productDao.delete(id);
    }

}
