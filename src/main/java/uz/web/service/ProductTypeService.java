package uz.web.service;

import uz.web.bean.BeanFactory;
import uz.web.dao.ProductDao;
import uz.web.dao.ProductTypeDao;
import uz.web.entity.Product;
import uz.web.entity.ProductType;

import java.util.List;
import java.util.Optional;

public class ProductTypeService {
    private final ProductTypeDao productTypeDao = BeanFactory.productTypeDao;

    public Optional<ProductType> findById(long id) {
        return productTypeDao.findById(id);
    }

    public List<ProductType> findAll(int page, int size){
        return productTypeDao.findAll(page, size);
    }

    public ProductType save(ProductType productType){
        return productTypeDao.save(productType);
    }

    public boolean delete(long id){
        return productTypeDao.delete(id);
    }


}
