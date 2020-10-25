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

    private int datoBuscarDE;

    private Perro perroEncontradoDE;

    private int datoBorrarDE;

    private DefaultDiagramModel model;

    private int seleccionUbicacionDE = 0;

    /**
     * Creates a new instance of ListaDEController
     */
    public ListaDEController() {
    }

    @PostConstruct
    private void iniciar() {
        listaPerrosDE = new ListaDE();
        tempDE = listaPerrosDE.getCabeza();

        inicializadorModeloDE();
    }

    public NodoDE getTempDE() {
        return tempDE;
    }

    public void setTempDE(NodoDE tempDE) {
        this.tempDE = tempDE;
    }

    
    public Perro getPerroMostrar() {
        return perroMostrarDE;
    }

    public void setPerroMostrar(Perro perroMostrar) {
        this.perroMostrarDE = perroMostrar;
    }

    public int getDatoBuscar() {
        return datoBuscarDE;
    }

    public void setDatoBuscar(int datoBuscar) {
        this.datoBuscarDE = datoBuscar;
    }

    public Perro getPerroEncontrado() {
        return perroEncontradoDE;
    }

    public void setPerroEncontrado(Perro perroEncontrado) {
        this.perroEncontradoDE = perroEncontrado;
    }

    public int getDatoBorrar() {
        return datoBorrarDE;
    }

    public void setDatoBorrar(int datoBorrar) {
        this.datoBorrarDE = datoBorrar;
    }

    public int getSeleccionUbicacion() {
        return seleccionUbicacionDE;
    }

    public void setSeleccionUbicacion(int seleccionUbicacion) {
        this.seleccionUbicacionDE = seleccionUbicacion;
    }

    public ListaDE getListaPerrosDE() {
        return listaPerrosDE;
    }

    public void setListaPerrosDE(ListaDE listaPerrosDE) {
        this.listaPerrosDE = listaPerrosDE;
    }

    public void irSiguienteDE() {
        tempDE = tempDE.getSiguiente().getAnterior();
        perroMostrarDE = tempDE.getDato();

    }

    public void irUltimoDE() {
        tempDE = listaPerrosDE.getCabeza();
        while (tempDE.getSiguiente().getAnterior() != null) {
            tempDE = tempDE.getSiguiente().getAnterior();
        }
        perroMostrarDE = tempDE.getDato();
    }

    public void irPrimeroDE() {
        if (listaPerrosDE.getCabeza() != null) {
            tempDE = listaPerrosDE.getCabeza();
            perroMostrarDE = tempDE.getDato();
        } else {
            JsfUtil.addErrorMessage("No existen datos en la lista");
        }
    }

    public void invertirDE() {
        listaPerrosDE.invertirDE();
        irPrimeroDE();
        inicializadorModeloDE();
    }

    public void intercambiarDE() {
        listaPerrosDE.intercambiarExtremosDE();
        irPrimeroDE();
    }

    public void buscarPerroDE() {
        Perro perroEncontradoDE = listaPerrosDE.encontrarxPosicionDE(datoBuscarDE);
        System.out.println("perroEncontrado = " + perroEncontradoDE);
    }

    public void borrarPerroDE() {
        listaPerrosDE.borrarPerroPorIdDE(datoBuscarDE);
        irPrimeroDE();
        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#C7B097',lineWidth:3}");
        model.setDefaultConnector(connector);
    }

    public void inicializadorModeloDE() {
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);

        if (listaPerrosDE.getCabeza() != null) {
            NodoDE ayudante = listaPerrosDE.getCabeza();
            int posX = 2;
            int posY = 2;

            while (ayudante != null) {
                Element perroPintar = new Element(ayudante.getDato().getNombre(), posX + "em", posY + "2em");

                if (ayudante.getDato().getNombre().toLowerCase().startsWith("p")) {
                    perroPintar.setStyleClass("ui-diagram-success");
                }
                perroPintar.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));
                perroPintar.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));

                model.addElement(perroPintar);
                ayudante = ayudante.getAnterior().getSiguiente();
                posX = posX + 5;
                posY = posY + 5;

            }

            for (int i = 0; i < model.getElements().size() - 1; i++) {
                model.connect(createConnection(model.getElements().get(i).getEndPoints().get(0),
                        model.getElements().get(i + 1).getEndPoints().get(1), null));
            }
        }
    }

    public DiagramModel getModel() {
        return model;
    }

    private Connection createConnection(EndPoint from, EndPoint to, String label) {
        Connection conn = new Connection(from, to);

        if (label != null) {
            conn.getOverlays().add(new LabelOverlay(label, "flow-label", 0.5));
        }
        return conn;
    }

    public String irCrearPerroDE() {
        perroEncontradoDE = new Perro();
        return "crearDE";
    }

    public void guardarPerroDE() {
        switch (seleccionUbicacionDE) {
            case 1:
                listaPerrosDE.adicionarAlInicioDE(perroEncontradoDE);
                break;
            case 2:
                listaPerrosDE.adicionarNodoDE(perroEncontradoDE);
                break;
            default:
                listaPerrosDE.adicionarNodoDE(perroEncontradoDE);

        }
        listaPerrosDE.adicionarNodoDE(perroEncontradoDE);
        perroEncontradoDE = new Perro();
        irPrimeroDE();
        JsfUtil.addSuccessMessage("Se ha adicionado el perro a la lista");
    }

    public String irHome() {
        perroEncontradoDE = new Perro();
        inicializadorModeloDE();
        return "homede";
    }
}
