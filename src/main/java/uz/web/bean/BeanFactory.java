package uz.web.bean;

import uz.web.dao.ProductDao;
import uz.web.dao.ProductStatusDao;
import uz.web.dao.ProductTypeDao;
import uz.web.service.ProductService;
import uz.web.service.ProductStatusService;
import uz.web.service.ProductTypeService;

public interface BeanFactory {
    ProductDao productDao = new ProductDao("product");
    ProductTypeDao productTypeDao = new ProductTypeDao("producttype");
    ProductStatusDao productStatusDao = new ProductStatusDao("productstatus");
    ProductService productService = new ProductService();
    ProductTypeService productTypeService = new ProductTypeService();
    ProductStatusService productStatusService = new ProductStatusService();

}
