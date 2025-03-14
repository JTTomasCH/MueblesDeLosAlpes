/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ $Id$
 * ServicioVendedoresMock.java Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación Licenciado bajo el
 * esquema Academic Free License version 3.0
 *
 * Ejercicio: Muebles de los Alpes Autor: Juan Sebastián Urrego
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package co.edu.uniandes.csw.mueblesdelosalpes.logica.ejb;

import co.edu.uniandes.csw.mueblesdelosalpes.dto.Vendedor;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.OperacionInvalidaException;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioVendedoresMockRemote;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioVendedoresMockLocal;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioPersistenciaMockLocal;
import co.edu.uniandes.csw.mueblesdelosalpes.persistencia.mock.ServicioPersistenciaMock;

import java.util.List;
import javax.ejb.Stateless;

/**
 * Implementación de los servicios de administración de un vendedor en el
 * sistema
 *
 * @author Juan Sebastián Urrego
 */
@Stateless
public class ServicioVendedoresMock implements IServicioVendedoresMockRemote, IServicioVendedoresMockLocal {

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------
    /**
     * Interface con referencia al servicio de persistencia en el sistema
     */
    private IServicioPersistenciaMockLocal persistencia;

    //-----------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------
    /**
     * Constructor de la clase sin argumentos
     */
    public ServicioVendedoresMock() {
        persistencia = new ServicioPersistenciaMock();
    }

    //-----------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------
    /**
     * Agrega un vendedor al sistema
     *
     * @param vendedor Nuevo vendedor
     * @throws OperacionInvalidaException Excepción lanzada en caso de error
     */
    @Override
    public void agregarVendedor(Vendedor vendedor) throws OperacionInvalidaException {
        try {
            persistencia.create(vendedor);
        } catch (OperacionInvalidaException ex) {
            throw new OperacionInvalidaException(ex.getMessage());
        }
    }

    /**
     * Actualiza un vendedor en el sistema
     *
     * @param vendedor Vendedor con los datos actualizados
     * @throws OperacionInvalidaException Excepción lanzada en caso de error
     */
    @Override
    public void actualizarVendedor(Vendedor vendedor) throws OperacionInvalidaException {
        Vendedor v = (Vendedor) persistencia.findById(Vendedor.class, vendedor.getIdentificacion());
        if (v != null) {
            persistencia.update(vendedor);
        } else {
            throw new OperacionInvalidaException("El vendedor con ID " + vendedor.getIdentificacion() + " no existe.");
        }
    }

    /**
     * Elimina un vendedor del sistema dado su ID
     *
     * @param id Identificador único del vendedor
     * @throws OperacionInvalidaException Excepción lanzada en caso de error
     */
    @Override
    public void eliminarVendedor(long id) throws OperacionInvalidaException {
        Vendedor v = (Vendedor) persistencia.findById(Vendedor.class, id);
        try {
            persistencia.delete(v);
        } catch (OperacionInvalidaException ex) {
            throw new OperacionInvalidaException(ex.getMessage());
        }
    }

    /**
     * Devuelve todos los vendedores del sistema
     *
     * @return vendedores Vendedores del sistema
     */
    @Override
    public List<Vendedor> getVendedores() {
        return persistencia.findAll(Vendedor.class);
    }

}
