package br.com.lavacao.model.dao;

import br.com.lavacao.model.domain.Marca;
//import br.com.lavacao.model.domain.Modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarcaDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Marca marca) {
        String sql = "INSERT INTO marca(descricao) VALUES(?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, marca.getNome());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Marca marca) {
        String sql = "UPDATE marca SET descricao=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, marca.getNome());
            stmt.setInt(2, marca.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Marca marca) {
        String sql = "DELETE FROM marca WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, marca.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
//     public List<Marca> listarPorMarca(Marca marca) {
//        String sql =  "SELECT mr.descricao as marca_descricao, m.nome as modelo_nome FROM marca mr "
//                + "INNER JOIN modelo m ON m.id_marca = mr.id;";
//        List<Marca> retorno = new ArrayList<>();
//        try {
//            PreparedStatement stmt = connection.prepareStatement(sql);
//            stmt.setString(1, modelo.getMarca().getNome());
//            ResultSet resultado = stmt.executeQuery();
//            while (resultado.next()) {
//                Marca marca = populateVO(resultado);
//                retorno.add(marca);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return retorno;
//    }

    public List<Marca> listar() {
        String sql = "SELECT * FROM marca";
        List<Marca> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Marca marca = new Marca();
                marca.setId(resultado.getInt("id"));
                marca.setNome(resultado.getString("descricao"));
                retorno.add(marca);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Marca buscar(Marca marca) {
        String sql = "SELECT * FROM marca WHERE id=?";
        Marca retorno = new Marca();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, marca.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                marca.setNome(resultado.getString("descricao"));
                retorno = marca;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    private Marca populateVO(ResultSet rs) throws SQLException {
       // Modelo modelo = new Modelo();
        Marca marca = new Marca();
        //modelo.setMarca(marca);
        
        //modelo.setId(rs.getInt("modelo_id"));
        //modelo.setNome(rs.getString("modelo_nome"));
        marca.setId(rs.getInt("marca_id"));
        marca.setNome(rs.getString("marca_descricao"));
        return marca;
    }    
}
