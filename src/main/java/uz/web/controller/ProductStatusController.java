package uz.web.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uz.web.bean.BeanFactory;
import uz.web.entity.ProductStatus;
import uz.web.service.ProductStatusService;
import java.util.List;
import java.util.Optional;


@Path("productstatus")
public class ProductStatusController {
    private final ProductStatusService productStatusService = BeanFactory.productStatusService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductStatus> getAll(@DefaultValue("1") @QueryParam("page") int page,@DefaultValue("20") @QueryParam("size") int size) {
        return productStatusService.findAll(page, size);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getById(@PathParam("id") long id) {
        Optional<ProductStatus> productStatus = productStatusService.findById(id);
        if(productStatus.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).entity(productStatus.get()).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(ProductStatus productStatus) {
        ProductStatus saved = productStatusService.save(productStatus);
        if(saved == null) return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(ProductStatus productStatus) {
        ProductStatus saved = productStatusService.save(productStatus);
        if(saved == null) return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.status(Response.Status.ACCEPTED).entity(saved).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
        boolean delete = productStatusService.delete(id);
        if(delete) return Response.status(Response.Status.NO_CONTENT).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
