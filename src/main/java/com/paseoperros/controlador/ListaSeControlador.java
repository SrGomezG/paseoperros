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
    /**
     * Creates a new instance of ListaSeControlador
     */
    public ListaSeControlador() {
    }
    @PostConstruct
    private void iniciar(){
        listaPerros = new ListaSe();
        listaPerros.adicionarNodo(new Perro("Pastor", (byte)1, (byte)3));
        listaPerros.adicionarNodo(new Perro("Lulú", (byte)2, (byte)4));
        listaPerros.adicionarNodo(new Perro("Firulais", (byte)3, (byte)6));
        
        listaPerros.adicionarNodoAlInicio(new Perro("Rocky",(byte)4, (byte)5));
        perroMostrar = listaPerros.getCabeza().getDato();
        temp = listaPerros.getCabeza();
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
    
    public void irSiguiente(){
        //if(temp.getSiguiente()!=null)
        temp = temp.getSiguiente();
        perroMostrar = temp.getDato();
    }
    public void irUltimo(){
        temp = listaPerros.getCabeza();
        while (temp.getSiguiente() != null) {
           temp = temp.getSiguiente();
        }
        perroMostrar = temp.getDato();
    }
    
    public void irPrimero(){
        temp = listaPerros.getCabeza();
        perroMostrar = temp.getDato();
    }
    public void invertir(){
        listaPerros.invertir();
        irPrimero();
    }
    
    public void intercambiar(){
        listaPerros.intercambiarExtremos();
        irPrimero();
    }

        
}
