/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.lavacao.model.domain;

/**
 *
 * @author letic
 */
public class ItemOS {
    private double itemServico;
    private String observações;
    
    private Modelo modelo;
    private OrdemServico ordemServico;

    public double getItemServico() {
        return itemServico;
    }

    public void setItemServico(double itemServico) {
        this.itemServico = itemServico;
    }

    public String getObservações() {
        return observações;
    }

    public void setObservações(String observações) {
        this.observações = observações;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }
    

    public ItemOS() {
    }

    public ItemOS(double itemServico, String observações) {
        this.itemServico = itemServico;
        this.observações = observações;
    }
    
    
    
}
