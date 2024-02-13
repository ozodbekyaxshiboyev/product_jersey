package uz.web.controller;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uz.web.bean.BeanFactory;
import uz.web.entity.ProductType;
import uz.web.service.ProductTypeService;
import java.util.List;
import java.util.Optional;


@Path("producttype")
public class ProductTypeController {
    private final ProductTypeService productTypeService = BeanFactory.productTypeService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductType> getAll(@DefaultValue("1") @QueryParam("page") int page,@DefaultValue("20") @QueryParam("size") int size) {
        return productTypeService.findAll(page, size);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getById(@PathParam("id") long id) {
        Optional<ProductType> productType = productTypeService.findById(id);
        if(productType.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).entity(productType.get()).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(ProductType productType) {
        ProductType saved = productTypeService.save(productType);
        if(saved == null) return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(ProductType productType) {
        ProductType saved = productTypeService.save(productType);
        if(saved == null) return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.status(Response.Status.ACCEPTED).entity(saved).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
        boolean delete = productTypeService.delete(id);
        if(delete) return Response.status(Response.Status.NO_CONTENT).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
