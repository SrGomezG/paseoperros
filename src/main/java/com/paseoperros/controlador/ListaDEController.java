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

    private Perro perroMostrar;

    private NodoDE temp;

    private int datoBuscar;

    private Perro perroEncontrado;

    private int datoBorrar;

    private DefaultDiagramModel model;

    private int seleccionUbicacion = 0;

    /**
     * Creates a new instance of ListaDEController
     */
    public ListaDEController() {
    }

    @PostConstruct
    private void iniciar() {
        listaPerrosDE = new ListaDE();
        temp = listaPerrosDE.getCabeza();

        inicializadorModelo();
    }

    public Perro getPerroMostrar() {
        return perroMostrar;
    }

    public void setPerroMostrar(Perro perroMostrar) {
        this.perroMostrar = perroMostrar;
    }

    public int getDatoBuscar() {
        return datoBuscar;
    }

    public void setDatoBuscar(int datoBuscar) {
        this.datoBuscar = datoBuscar;
    }

    public Perro getPerroEncontrado() {
        return perroEncontrado;
    }

    public void setPerroEncontrado(Perro perroEncontrado) {
        this.perroEncontrado = perroEncontrado;
    }

    public int getDatoBorrar() {
        return datoBorrar;
    }

    public void setDatoBorrar(int datoBorrar) {
        this.datoBorrar = datoBorrar;
    }

    public int getSeleccionUbicacion() {
        return seleccionUbicacion;
    }

    public void setSeleccionUbicacion(int seleccionUbicacion) {
        this.seleccionUbicacion = seleccionUbicacion;
    }

    public ListaDE getListaPerrosDE() {
        return listaPerrosDE;
    }

    public void setListaPerrosDE(ListaDE listaPerrosDE) {
        this.listaPerrosDE = listaPerrosDE;
    }

    public void irSiguiente() {
        temp = temp.getSiguiente().getAnterior();
        perroMostrar = temp.getDato();

    }

    public void irUltimo() {
        temp = listaPerrosDE.getCabeza();
        while (temp.getSiguiente().getAnterior() != null) {
            temp = temp.getSiguiente().getAnterior();
        }
        perroMostrar = temp.getDato();
    }

    public void irPrimero() {
        if (listaPerrosDE.getCabeza() != null) {
            temp = listaPerrosDE.getCabeza();
            perroMostrar = temp.getDato();
        } else {
            JsfUtil.addErrorMessage("No existen datos en la lista");
        }
    }

    public void invertir() {
        listaPerrosDE.invertir();
        irPrimero();
        inicializadorModelo();
    }

    public void intercambiar() {
        listaPerrosDE.intercambiarExtremos();
        irPrimero();
    }

    public void buscarPerro() {
        Perro perroEncontrado = listaPerrosDE.encontrarxPosicionDE(datoBuscar);
        System.out.println("perroEncontrado = " + perroEncontrado);
    }

    public void borrarPerro() {
        listaPerrosDE.borrarPerroPorId(datoBuscar);
        irPrimero();
        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#C7B097',lineWidth:3}");
        model.setDefaultConnector(connector);
    }

    public void inicializadorModelo() {
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

    public String irCrearPerro() {
        perroEncontrado = new Perro();
        return "crear";
    }

    public void guardarPerro() {
        switch (seleccionUbicacion) {
            case 1:
                listaPerrosDE.adicionarAlInicio(perroEncontrado);
                break;
            case 2:
                listaPerrosDE.adicionarNodo(perroEncontrado);
                break;
            default:
                listaPerrosDE.adicionarNodo(perroEncontrado);

        }
        listaPerrosDE.adicionarNodo(perroEncontrado);
        perroEncontrado = new Perro();
        irPrimero();
        JsfUtil.addSuccessMessage("Se ha adicionado el perro a la lista");
    }

    public String irHome() {
        perroEncontrado = new Perro();
        inicializadorModelo();
        return "home";
    }
}
