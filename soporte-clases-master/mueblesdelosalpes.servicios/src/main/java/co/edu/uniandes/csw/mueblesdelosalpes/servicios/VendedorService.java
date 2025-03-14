/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mueblesdelosalpes.servicios;

import co.edu.uniandes.csw.mueblesdelosalpes.dto.Vendedor;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.OperacionInvalidaException;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioVendedoresMockLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Vendedor")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VendedorService {

    /**
     * Referencia al Ejb del catalogo encargada de realizar las operaciones del
     * mismo.
     */
    @EJB
    private IServicioVendedoresMockLocal vendedoresEjb;

    /**
     * Servicio que ofrece una lista JSON con el catálogo de Muebles de los
     * alpes (Los muebles disponibles para la venta).
     *
     * @return la lista JSON con los muebles de MDLA.
     *
     */
    @GET
    @Path("asesores/")
    public List<Vendedor> getTodosLosAsesores() {
        return vendedoresEjb.getVendedores();
    }

    @POST
    public Response agregarVendedor(Vendedor vendedor) {
        try {
            vendedoresEjb.agregarVendedor(vendedor);
            return Response.ok().build();
        } catch (OperacionInvalidaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizarVendedor(@PathParam("id") long id, Vendedor vendedor) {
        try {
            vendedor.setIdentificacion(id); // Asegurar que el ID coincide con el de la URL
            vendedoresEjb.actualizarVendedor(vendedor);
            return Response.ok().build();
        } catch (OperacionInvalidaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarVendedor(@PathParam("id") long id) {
        try {
            vendedoresEjb.eliminarVendedor(id);
            return Response.noContent().build();
        } catch (OperacionInvalidaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
