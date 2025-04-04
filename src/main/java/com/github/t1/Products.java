package com.github.t1;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.List;

import static com.github.t1.YamlMessageBodyAdapter.APPLICATION_YAML;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.APPLICATION_XML;

@Path("/products")
@Consumes({APPLICATION_JSON, APPLICATION_XML, APPLICATION_YAML})
@Produces({APPLICATION_JSON, APPLICATION_XML, APPLICATION_YAML})
public class Products {
    @POST
    public Product addProduct(Product product) {return product;}

    @GET
    @Produces({APPLICATION_JSON, APPLICATION_YAML})
    public List<Product> products() {return List.of(product("1"), product("2"), product("3"));}

    @GET
    @Produces({APPLICATION_XML})
    public ProductList productsXml() {return new ProductList(products());}

    @GET
    @Path("/{id}")
    public Product product(@PathParam("id") String id) {
        return new Product(id,
                "Tabula Rasa #" + id,
                "A clean table",
                123_00);
    }
}
