/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paseoperros.controlador;

import co.edu.umanizales.listase.modelo.ListaDE;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author tsuyo
 */
@Named(value = "listaDEController")
@SessionScoped
public class ListaDEController implements Serializable {

    private ListaDE listaPerrosDE;

    /**
     * Creates a new instance of ListaDEController
     */
    public ListaDEController() {
    }

}
