/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.lavacao.model.dao;

import br.com.lavacao.model.domain.Cor;
import br.com.lavacao.model.domain.Marca;
import br.com.lavacao.model.domain.Modelo;
import br.com.lavacao.model.domain.Veiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mpisc
 */
public class VeiculoDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Veiculo veiculo) {
        String sql = "INSERT INTO veiculo(placa, id_cor, id_modelo, observacoes) VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veiculo.getPlaca());
            stmt.setInt(2, veiculo.getCor().getId());
            stmt.setInt(3, veiculo.getModelo().getId());
            stmt.setString(4, veiculo.getObservacao());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Veiculo veiculo) {
        String sql = "UPDATE veiculo SET placa=?, id_cor=?, id_modelo=?, observacoes=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veiculo.getPlaca());
            stmt.setInt(2, veiculo.getCor().getId());
            stmt.setInt(3, veiculo.getModelo().getId());
            stmt.setString(4, veiculo.getObservacao());
            stmt.setInt(5, veiculo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Veiculo veiculo) {
        String sql = "DELETE FROM veiculo WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, veiculo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Veiculo> listar() {
       
        
        String sql = "SELECT mt.tipo_comb as motor_comb, mt.id_modelo as motor_idModelo, mt.potencia as motor_potencia, v.id as veiculo_id,"
                + "v.observacoes as veiculo_observacao, v.placa as veiculo_placa, m.nome as modelo_nome, m.id as modelo_id,"
                + "c.nome as cor_nome, mr.descricao as marca_nome, c.id as cor_id "
                + "FROM veiculo v INNER JOIN modelo m INNER JOIN marca mr INNER JOIN cor c INNER JOIN motor mt "
                + "ON v.id_modelo = m.id AND m.id_marca = mr.id and v.id_cor = c.id and mt.id_modelo = m.id; ";
       // String sql = "SELECT * FROM VEICULO";
        List<Veiculo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Veiculo veiculo = populateVO2(resultado);
                retorno.add(veiculo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    

    public Veiculo buscar(Veiculo veiculo) {
        String sql = "SELECT * FROM veiculo WHERE id=?";
        Veiculo retorno = new Veiculo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, veiculo.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno = populateVO2(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    private Veiculo populateVO(ResultSet rs) throws SQLException {
        Veiculo veiculo = new Veiculo();
        Modelo modelo = new Modelo();
        Marca marca = new Marca();
        Cor cor = new Cor();
        
        veiculo.setModelo(modelo);
        modelo.setMarca(marca);
        veiculo.setCor(cor);
        veiculo.setPlaca(rs.getString("veiculo_placa"));
        veiculo.setId(rs.getInt("veiculo_id"));
        veiculo.setObservacao(rs.getString("veiculo_observacao"));
        cor.setNome(rs.getString("cor_nome"));
        marca.setNome(rs.getString("marca_nome"));
        modelo.setId(rs.getInt("modelo_id"));
        modelo.setNome(rs.getString("modelo_nome"));
        
        return veiculo;
    }

    private Veiculo populateVO2(ResultSet rs) throws SQLException {
        Veiculo veiculo = new Veiculo();
        Modelo modelo = new Modelo();
        Cor cor = new Cor();
        
        veiculo.setPlaca(rs.getString("veiculo_placa"));
        veiculo.setId(rs.getInt("veiculo_id"));
        veiculo.setObservacao(rs.getString("veiculo_observacao"));
        cor.setId(rs.getInt("cor_id"));
        CorDAO corDAO = new CorDAO();
        corDAO.setConnection(connection);
        cor = corDAO.buscar(cor);
        veiculo.setCor(cor);
        
        modelo.setId(rs.getInt("modelo_id"));
        ModeloDAO modeloDAO = new ModeloDAO();
        modeloDAO.setConnection(connection);
        veiculo.setModelo(modeloDAO.buscar(modelo));
        
        
        return veiculo;
    }    
}
