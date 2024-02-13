package uz.web.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uz.web.bean.BeanFactory;
import uz.web.dto.ProductDto;
import uz.web.entity.Product;
import uz.web.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Path("product")
public class ProductController {
    private final ProductService productService = BeanFactory.productService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAll(@DefaultValue("1") @QueryParam("page") int page, @DefaultValue("20") @QueryParam("size") int size) {
        return productService.findAll(page, size);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getById(@PathParam("id") long id) {
        Optional<Product> product = productService.findById(id);
        if(product.isEmpty()) return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.status(Response.Status.OK).entity(product.get()).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(ProductDto product) {
        Product saved = productService.save(product);
        if(saved == null) return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(ProductDto product) {
        Product saved = productService.save(product);
        if(saved == null) return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.status(Response.Status.ACCEPTED).entity(saved).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
        boolean delete = productService.delete(id);
        if(delete) return Response.status(Response.Status.NO_CONTENT).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
