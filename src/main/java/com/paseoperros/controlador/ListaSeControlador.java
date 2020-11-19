/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paseoperros.controlador;

import co.edu.umanizales.listase.modelo.ListaSe;
import co.edu.umanizales.listase.modelo.Nodo;
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
@Named(value = "listaSeControlador")
@SessionScoped
public class ListaSeControlador implements Serializable {

    private ListaSe listaPerros;

    private Perro perroMostrar;

    private Nodo temp;

    private int datoBuscar;

    private Perro perroEncontrado;

    private int datoBorrar;

    private DefaultDiagramModel model;

    private int seleccionUbicacion = 0;

    /**
     * Creates a new instance of ListaSeControlador
     */
    public ListaSeControlador() {
    }

    @PostConstruct
    private void iniciar() {
        listaPerros = new ListaSe();
//        listaPerros.adicionarNodo(new Perro("Pastor", (byte) 1, (byte) 3));
//        listaPerros.adicionarNodo(new Perro("Lulú", (byte) 2, (byte) 4));
//        listaPerros.adicionarNodo(new Perro("Firulais", (byte) 3, (byte) 6));
//
//        listaPerros.adicionarNodoAlInicio(new Perro("Rocky", (byte) 4, (byte) 5));
//        perroMostrar = listaPerros.getCabeza().getDato();
        temp = listaPerros.getCabeza();

        inicializadorModelo();
    }

    public int getSeleccionUbicacion() {
        return seleccionUbicacion;
    }

    public void setSeleccionUbicacion(int seleccionUbicacion) {
        this.seleccionUbicacion = seleccionUbicacion;
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

    public int getDatoBuscar() {
        return datoBuscar;
    }

    public void setDatoBuscar(int datoBuscar) {
        this.datoBuscar = datoBuscar;
    }

    public Perro getPerroMostrar() {
        return perroMostrar;
    }

    public void setPerroMostrar(Perro perroMostrar) {
        this.perroMostrar = perroMostrar;
    }

    public ListaSe getListaPerros() {
        return listaPerros;
    }

    public void setListaPerros(ListaSe listaPerros) {
        this.listaPerros = listaPerros;
    }

    public Nodo getTemp() {
        return temp;
    }

    public void setTemp(Nodo temp) {
        this.temp = temp;
    }

    public void irSiguiente() {
        //if(temp.getSiguiente()!=null)
        temp = temp.getSiguiente();
        perroMostrar = temp.getDato();
    }

    public void irUltimo() {
        temp = listaPerros.getCabeza();
        while (temp.getSiguiente() != null) {
            temp = temp.getSiguiente();
        }
        perroMostrar = temp.getDato();
    }

    public void irPrimero() {
        if (listaPerros.getCabeza() != null) {
            temp = listaPerros.getCabeza();
            perroMostrar = temp.getDato();

        } else {
            JsfUtil.addErrorMessage("No existen datos en la lista");
        }
    }

    public void invertir() {
        listaPerros.invertir();
        irPrimero();
        inicializadorModelo();
    }

    public void intercambiar() {
        listaPerros.intercambiarExtremos();
        irPrimero();
    }

    public void buscarPerro() {

        Perro perroEncontrado = listaPerros.encontrarxPosicion(datoBuscar);
        System.out.println("perroEncontrado = " + perroEncontrado);

    }

    public void borrarPerro() {
        listaPerros.borrarPorPerroId(datoBuscar);
        irPrimero();
        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#C7B097',lineWidth:3}");
        model.setDefaultConnector(connector);
    }

    public void inicializadorModelo() {
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);

        if (listaPerros.getCabeza() != null) {

            Nodo ayudante = listaPerros.getCabeza();
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
                ayudante = ayudante.getSiguiente();
                posX = posX + 5;
                posY = posY + 5;
            }

            for (int i = 0; i < model.getElements().size() - 1; i++) {
                model.connect(createConnection(model.getElements().get(i).getEndPoints().get(0),
                        model.getElements().get(i + 1).getEndPoints().get(1), null));
            }

        }


        /*
        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#C7B097',lineWidth:3}");
        model.setDefaultConnector(connector);

        Element start = new Element("Fight for your dream", "20em", "6em");
        start.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));
        start.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));

        Element trouble = new Element("Do you meet some trouble?", "20em", "18em");
        trouble.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
        trouble.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));
        trouble.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));

        Element giveup = new Element("Do you give up?", "20em", "30em");
        giveup.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
        giveup.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
        giveup.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));

        Element succeed = new Element("Succeed", "50em", "18em");
        succeed.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
        succeed.setStyleClass("ui-diagram-success");

        Element fail = new Element("Fail", "50em", "30em");
        fail.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
        fail.setStyleClass("ui-diagram-fail");

        model.addElement(start);
        model.addElement(trouble);
        model.addElement(giveup);
        model.addElement(succeed);
        model.addElement(fail);

        model.connect(createConnection(start.getEndPoints().get(0), trouble.getEndPoints().get(0), null));
        model.connect(createConnection(trouble.getEndPoints().get(1), giveup.getEndPoints().get(0), "Yes"));
        model.connect(createConnection(giveup.getEndPoints().get(1), start.getEndPoints().get(1), "No"));
        model.connect(createConnection(trouble.getEndPoints().get(2), succeed.getEndPoints().get(0), "No"));
        model.connect(createConnection(giveup.getEndPoints().get(2), fail.getEndPoints().get(0), "Yes"));
         */
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

    public String irCrearPerro() {
        perroEncontrado = new Perro();
        return "crear";
    }

    public void guardarPerro() {

        switch (seleccionUbicacion) {
            case 1:
                listaPerros.adicionarNodoAlInicio(perroEncontrado);
                break;
            case 2:
                listaPerros.adicionarNodo(perroEncontrado);
                break;
            default:
                listaPerros.adicionarNodo(perroEncontrado);
        }
        listaPerros.adicionarNodo(perroEncontrado);
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
