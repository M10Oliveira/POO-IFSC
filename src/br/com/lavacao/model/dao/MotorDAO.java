package br.com.lavacao.model.dao;

import br.com.lavacao.model.domain.ECombustivel;
import br.com.lavacao.model.domain.Marca;
import br.com.lavacao.model.domain.Motor;
import br.com.lavacao.model.domain.Modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MotorDAO{

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean atualizar(Motor motor) {
        String sql = "UPDATE motor SET id_modelo=?, potencia=?, tipo_comb=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, motor.getModelo().getId());
            stmt.setInt(2, motor.getPotencia());
            stmt.setString(3, motor.getCombustivel().getDescricao());
            stmt.setInt(5, motor.getModelo().getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MotorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Motor> listar() {
        String sql = "SELECT m.id as id_modelo, mt.id_modelo as modelo_motor, mt.potencia as motor_potencia, "
                + " m.nome as modelo_nome,"
                + " mt.tipo_comb as comb_motor FROM motor mt INNER JOIN modelo m ON mt.id_modelo = m.id;";
        List<Motor> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Motor motor = populateVO(resultado);
                retorno.add(motor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MotorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    public boolean inserir(Motor motor) {
        String sql = "INSERT INTO motor(potencia, id_modelo) VALUES(?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, motor.getPotencia());
            stmt.setInt(2, motor.getModelo().getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<ECombustivel> listarCombustivel() {
        List<ECombustivel> retorno = new ArrayList<>();
            for (ECombustivel ecomb : ECombustivel.values()){
                retorno.add(ecomb);
            }

        return retorno;
    }

    private Motor populateVO(ResultSet rs) throws SQLException {
        Motor motor = new Motor();
        Modelo modelo = new Modelo();
        motor.setModelo(modelo);
    
        motor.setPotencia(rs.getInt("motor_potencia"));
        motor.setCombustivel(Enum.valueOf(ECombustivel.class, rs.getString("comb_motor")));
        modelo.setId(rs.getInt("id_modelo"));
        modelo.setNome(rs.getString("modelo_nome"));
        return motor;
    }   
    

}
