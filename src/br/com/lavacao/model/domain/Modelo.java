/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.lavacao.model.domain;
import br.com.lavacao.model.domain.Marca;

/**
 *
 * @author matheus.ti
 */
public class Modelo {
    private int id;
    private Marca marca;
    private String nome;
    private Motor motor;
    ECategoria categoria = ECategoria.PEQUENO;

    /*public Modelo() {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }*/

    public Modelo() {
        this.createMotor();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    
    public Modelo(int id, Marca marca, String nome) {
        this();
        this.id = id;
        this.marca = marca;
        this.nome = nome;
    }

    public Modelo(int id, Marca marca, String nome, Motor motor) {
        this(id, marca, nome);
        this.motor = motor;
    }
    
    public void createMotor(){
        this.motor = new Motor();
        this.motor.setModelo(this);
    }

    public Motor getMotor() {
        return motor;
    }

    /*public void setMotor(Motor motor) {
    this.motor = motor;
    }*/

    public ECategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(ECategoria categoria) {
        this.categoria = categoria;
    }
    
    
    

    @Override
    public String toString() {
        return nome;
    }

   
    
}
