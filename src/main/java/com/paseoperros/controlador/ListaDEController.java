/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paseoperros.controlador;

import co.edu.umanizales.listase.modelo.ListaDE;
import co.edu.umanizales.listase.modelo.NodoDE;
import co.edu.umanizales.listase.modelo.Perro;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.FlowChartConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;

/**
 *
 * @author tsuyo
 */
@Named(value = "listaDEController")
@SessionScoped
public class ListaDEController implements Serializable {

    private ListaDE listaPerrosDE;

    private Perro perroMostrarDE;

    private NodoDE tempDE;

    private Perro perroEncontradoDE;

    private int datoBuscarDE;

    private int posicionUno;

    private int posicionDos;

    private int posicion;

    private int borrarPorPosicion;

    private DefaultDiagramModel model;

    private int seleccionUbicacionDE = 0;

    private int totalPerrosDE = 0;

    public ListaDEController() {
    }

    @PostConstruct
    private void iniciarDE() {
        listaPerrosDE = new ListaDE();
        //// Conectaría a un archivo plano o a una base de datos para llenar la 
        //lista de perros
        listaPerrosDE.adicionarNodoDE(new Perro("Pastor", (byte) 1, (byte) 3));
        listaPerrosDE.adicionarNodoDE(new Perro("Lulú", (byte) 2, (byte) 4));
        listaPerrosDE.adicionarNodoDE(new Perro("Firulais", (byte) 3, (byte) 6));
        listaPerrosDE.adicionarAlInicioDE(new Perro("Rocky", (byte) 4, (byte) 5));
        listaPerrosDE.adicionarNodoDE(new Perro("Lupe", (byte) 5, (byte) 1));
        listaPerrosDE.adicionarNodoDE(new Perro("Luna", (byte) 6, (byte) 3));
        perroMostrarDE = listaPerrosDE.getCabeza().getDato();
        tempDE = listaPerrosDE.getCabeza();

        totalPerrosDE = listaPerrosDE.contarNodosDE();
        inicializarModelo();

    }

    public ListaDE getListaPerrosDE() {
        return listaPerrosDE;
    }

    public void setListaPerrosDE(ListaDE listaPerrosDE) {
        this.listaPerrosDE = listaPerrosDE;
    }

    public Perro getPerroMostrarDE() {
        return perroMostrarDE;
    }

    public void setPerroMostrarDE(Perro perroMostrarDE) {
        this.perroMostrarDE = perroMostrarDE;
    }

    public NodoDE getTempDE() {
        return tempDE;
    }

    public void setTempDE(NodoDE tempDE) {
        this.tempDE = tempDE;
    }

    public Perro getPerroEncontradoDE() {
        return perroEncontradoDE;
    }

    public void setPerroEncontradoDE(Perro perroEncontradoDE) {
        this.perroEncontradoDE = perroEncontradoDE;
    }

    public int getDatoBuscarDE() {
        return datoBuscarDE;
    }

    public void setDatoBuscarDE(int datoBuscarDE) {
        this.datoBuscarDE = datoBuscarDE;
    }

    public int getPosicionUno() {
        return posicionUno;
    }

    public void setPosicionUno(int posicionUno) {
        this.posicionUno = posicionUno;
    }

    public int getPosicionDos() {
        return posicionDos;
    }

    public void setPosicionDos(int posicionDos) {
        this.posicionDos = posicionDos;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getBorrarPorPosicion() {
        return borrarPorPosicion;
    }

    public void setBorrarPorPosicion(int borrarPorPosicion) {
        this.borrarPorPosicion = borrarPorPosicion;
    }

    public int getSeleccionUbicacionDE() {
        return seleccionUbicacionDE;
    }

    public void setSeleccionUbicacionDE(int seleccionUbicacionDE) {
        this.seleccionUbicacionDE = seleccionUbicacionDE;
    }

    public void irSiguiente() {
        if (listaPerrosDE.getCabeza() != null) {
            tempDE = tempDE.getSiguiente();
            perroMostrarDE = tempDE.getDato();
        } else {
            JsfUtil.addErrorMessage("No hay datos");
        }
    }

    public void irAnterior() {
        if (listaPerrosDE.getCabeza() != null) {
            tempDE = tempDE.getAnterior();
            perroMostrarDE = tempDE.getDato();
        } else {
            JsfUtil.addErrorMessage("No hay datos");
        }
    }

    public void irPrimero() {
        if (listaPerrosDE.getCabeza() != null) {
            tempDE = listaPerrosDE.getCabeza();
            perroMostrarDE = tempDE.getDato();
            inicializarModelo();
        } else {
            JsfUtil.addErrorMessage("No hay datos en la lista");
        }

    }

    public void irUltimo() {
        tempDE = listaPerrosDE.getCabeza();
        while (tempDE.getSiguiente() != null) {
            tempDE = tempDE.getSiguiente();
        }
        perroMostrarDE = tempDE.getDato();
    }

    public void invertir() {
        listaPerrosDE.invertirDE();
        irPrimero();
        inicializarModelo();
    }

    public void intercambiar() {
        listaPerrosDE.intercambiarExtremosDE();
        irPrimero();
        inicializarModelo();
    }

    public void buscarPerro() {
        perroEncontradoDE = listaPerrosDE.encontrarxPosicionDE(datoBuscarDE);
        inicializarModelo();
    }

    public void eliminarPorPosicion() {
        if (tempDE.getSiguiente() != null) {
            listaPerrosDE.eliminarPorPosicionDE(borrarPorPosicion);
            JsfUtil.addSuccessMessage("Se ha eliminado");
            irPrimero();
        } else if (tempDE.getSiguiente() == null && tempDE.getAnterior() == null) {
            listaPerrosDE.eliminarPorPosicionDE(borrarPorPosicion);
            JsfUtil.addSuccessMessage("Se ha eliminado");
            perroMostrarDE = null;
            inicializarModelo();
        } else {
            listaPerrosDE.eliminarPorPosicionDE(borrarPorPosicion);
            JsfUtil.addSuccessMessage("Se ha eliminado");
            irPrimero();
        }
    }

    public void eliminacionDirecta() {
        if (tempDE.getSiguiente() != null) {
            listaPerrosDE.eliminarDE(perroMostrarDE);
            JsfUtil.addSuccessMessage(perroMostrarDE.getNombre() + "Se ha eliminado");
            irPrimero();
        } else if (tempDE.getSiguiente() == null && tempDE.getAnterior() == null) {
            listaPerrosDE.eliminarDE(perroMostrarDE);
            JsfUtil.addSuccessMessage(perroMostrarDE.getNombre() + "Se ha eliminado");
            perroMostrarDE = null;
            inicializarModelo();
        } else {
            listaPerrosDE.eliminarDE(perroMostrarDE);
            JsfUtil.addSuccessMessage(perroMostrarDE.getNombre() + "Se ha eliminado");
            irPrimero();
        }
    }

    public void inicializarModelo() {
        //Instancial el modelo
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);

        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#C7B097',lineWidth:3}");
        model.setDefaultConnector(connector);

        if (listaPerrosDE != null) {
            NodoDE ayudante = listaPerrosDE.getCabeza();
            int posX = 2;
            int posY = 2;
            while (ayudante != null) {
                Element perroPintar = new Element(ayudante.getDato().getNombre(), posX + "em", posY + "em");
                if (ayudante.getDato() == perroEncontradoDE) {
                    perroPintar.setStyleClass("ui-diagram-success");
                }
                if (ayudante.getDato() == (listaPerrosDE.encontrarxPosicionDE(1))) {
                    perroPintar.setStyleClass("ui-diagram-success2");
                }
                if (ayudante.getDato() == (listaPerrosDE.encontrarxPosicionDE(listaPerrosDE.contarNodosDE()))) {
                    perroPintar.setStyleClass("ui-diagram-success2");
                }
                perroPintar.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM_RIGHT));
                perroPintar.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
                model.addElement(perroPintar);
                ayudante = ayudante.getSiguiente();
                posX = posX + 5;
                posY = posY + 5;
            }
        }
        for (int i = 0; i < model.getElements().size() - 1; i++) {
            model.connect(createConnection(model.getElements().get(i).getEndPoints().get(0), model.getElements().get(i + 1).getEndPoints().get(1), null));
        }
    }

    public DiagramModel getModel() {
        return model;
    }

    private Connection createConnection(EndPoint from, EndPoint to, String label) {
        Connection conn = new Connection(from, to);
        conn.getOverlays().add(new ArrowOverlay(20, 20, 1, 1));
        if (label != null) {
            conn.getOverlays().add(new LabelOverlay(label, "flow-label", 0.5));
        }
        return conn;
    }

    public String irCrearPerroDE() {
        perroEncontradoDE = new Perro();
        posicion = 1;
        seleccionUbicacionDE = 0;
        return "crearDE";
    }

    public void guardarPerro() {
        switch (seleccionUbicacionDE) {
            case 1:
                listaPerrosDE.adicionarAlInicioDE(perroEncontradoDE);
                break;
            case 2:
                listaPerrosDE.adicionarNodoDE(perroEncontradoDE);
                break;
            case 3:
                listaPerrosDE.adicionarNodoPorPosicion(posicion, perroEncontradoDE);
            default:
                listaPerrosDE.adicionarNodoDE(perroEncontradoDE);
        }
        perroEncontradoDE = new Perro();
        irPrimero();
        JsfUtil.addSuccessMessage("Se ha adicionado el perro a la lista");
    }

    public String irHomeDE() {
        perroEncontradoDE = new Perro();
        inicializarModelo();
        return "homede";
    }

    public void mostrar() {
        listaPerrosDE.mostrarLista();
    }

    public void intercambiarPosicionesDadas() {
        if (posicionUno == posicionDos) {
            JsfUtil.addErrorMessage("No se puede intercambiar porque son el mismo");
        } else {
            listaPerrosDE.intercambiarPosiciones(posicionUno, posicionDos);
            irPrimero();
        }
    }
}
