/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.lavacao.model.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author letic
 */
public class OrdemServico {
    private long numero;
    private double total;
    private LocalDate agenda;
    private double desconto;
    
    private EStatus eStatus;
    private ItemOS itemOS;
    
    private List<ItemOS> listaItemOS;
    private Veiculo veiculo;
    

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public double getTotal() {
        return total;
    }


    public LocalDate getAgenda() {
        return agenda;
    }

    public void setAgenda(LocalDate agenda) {
        this.agenda = agenda;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public EStatus geteStatus() {
        return eStatus;
    }

    public void seteStatus(EStatus eStatus) {
        this.eStatus = eStatus;
    }

    public ItemOS getItemOS() {
        return itemOS;
    }

    public void setItemOS(ItemOS itemOS) {
        this.itemOS = itemOS;
    }

    public List<ItemOS> getListaItemOS() {
        return listaItemOS;
    }

    public void setListaItemOS(List<ItemOS> listaItemOS) {
        this.listaItemOS = listaItemOS;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
    
    

    public OrdemServico() {
    }

    public OrdemServico(long numero, double total, LocalDate agenda, double desconto) {
        this.numero = numero;
        this.total = total;
        this.agenda = agenda;
        this.desconto = desconto;
    }
    
    public double calcularServico(){
        return 0.0;
    }
    
    public void add(ItemOS itemOS){
        if(itemOS ==null){
            listaItemOS = new ArrayList<>();
        }
        listaItemOS.add(itemOS);
        itemOS.setOrdemServico(this);
    }
    public void remove(ItemOS itemOS) {
        listaItemOS.remove(itemOS);
    }
    
}
