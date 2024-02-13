package uz.web.service;

import uz.web.bean.BeanFactory;
import uz.web.dao.ProductStatusDao;
import uz.web.entity.Product;
import uz.web.entity.ProductStatus;
import uz.web.entity.ProductType;

import java.util.List;
import java.util.Optional;

public class ProductStatusService {
    private final ProductStatusDao productStatusDao = BeanFactory.productStatusDao;

    public Optional<ProductStatus> findById(long id) {
        return productStatusDao.findById(id);
    }

    public List<ProductStatus> findAll(int page, int size) {
        return productStatusDao.findAll(page, size);
    }

    public ProductStatus save(ProductStatus productType) {
        return productStatusDao.save(productType);
    }

    public boolean delete(long id){
        return productStatusDao.delete(id);
    }


}
