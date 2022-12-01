/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.lavacao.model.domain;

/**
 *
 * @author matheus.ti
 */
public class Motor {
    private int potencia;
    private Modelo modelo;
    ECombustivel combustivel = ECombustivel.GASOLINA;
    
    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }
    
    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public ECombustivel getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(ECombustivel combustivel) {
        this.combustivel = combustivel;
    }
    
    
}
