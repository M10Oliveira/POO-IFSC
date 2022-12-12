package br.com.lavacao.model.dao;

//import br.com.lavacao.model.domain.Marca;
//import br.com.lavacao.model.domain.Modelo;
import br.com.lavacao.model.domain.ECategoria;
import br.com.lavacao.model.domain.ECombustivel;
import br.com.lavacao.model.domain.Marca;
import br.com.lavacao.model.domain.Modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ModeloDAO{

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    public boolean inserir(Modelo modelo) {
        final String sql = "INSERT INTO modelo(nome, id_marca, categoria) VALUES(?,?,?);";
        final String sqlMotor = "INSERT INTO motor(id_modelo, potencia, tipo_comb) VALUES((SELECT max(id) FROM modelo), ?, ?)";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, modelo.getNome());
            stmt.setInt(2, modelo.getMarca().getId());
            stmt.setString(3, modelo.getCategoria().name());
            stmt.execute();
            stmt = connection.prepareStatement(sqlMotor);
            stmt.setInt(1, modelo.getMotor().getPotencia());
            stmt.setString(2, modelo.getMotor().getCombustivel().getDescricao());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Modelo modelo) {
        String sql = "UPDATE modelo SET nome=?, id_marca=? , categoria=? WHERE id=?;";
        String sqlmotor = "UPDATE motor SET potencia=?, tipo_comb=? WHERE id_modelo=?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, modelo.getNome());
            stmt.setInt(2, modelo.getMarca().getId());
            stmt.setString(3, modelo.getCategoria().toString());
            stmt.setInt(4, modelo.getId());
            stmt.execute();
            
            stmt = connection.prepareStatement(sqlmotor);
            stmt.setInt(1, modelo.getMotor().getPotencia());
            stmt.setString(2,modelo.getMotor().getCombustivel().name());
            stmt.setInt(3, modelo.getId());
            stmt.execute();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Modelo modelo) {
        String sql = "DELETE FROM modelo WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modelo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public List<Modelo> listarTudo(){
        String sql = "SELECT m.id as modelo_id, m.nome as modelo_nome FROM modelo m;";
        //String sql = "SELECT * from modelo;";
        //String sql =  "SELECT m.categoria as modelo_categoria, m.id as modelo_id, m.nome as modelo_nome, "
                //+ "mr.id as marca_id, mr.descricao as marca_descricao "
                //+ "FROM modelo m INNER JOIN marca mr ON m.id_marca = mr.id;";
        List<Modelo> retorno = new ArrayList<>();
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()){
                Modelo modelo = populateVOAll(resultado);
                retorno.add(modelo);
            }
            
        }   catch(SQLException ex){
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    public List<Modelo> listar() {
        String sql =  "SELECT m.categoria as modelo_categoria, m.id as modelo_id, m.nome as modelo_nome, "
                + "mr.id as marca_id, mr.descricao as marca_descricao "
                + "FROM modelo m INNER JOIN marca mr ON m.id_marca = mr.id;";
        List<Modelo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Modelo modelo = populateVO(resultado);
                retorno.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<ECategoria> listarCategorias() {
        List<ECategoria> retorno = new ArrayList<>();
            for (ECategoria ecat : ECategoria.values()){
                retorno.add(ecat);
            }

        return retorno;
    }
    
    public List<Modelo> listarPorMarca(Marca marca) {
        
        
        String sql =  "SELECT m.categoria as modelo_categoria, m.id as modelo_id, mr.descricao as marca_descricao, mr.id as marca_id, m.nome as modelo_nome FROM marca mr "
                + "INNER JOIN modelo m ON m.id_marca = mr.id WHERE mr.id=?;";
        List<Modelo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, marca.getId());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Modelo modelo = populateVO(resultado);
                retorno.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Modelo buscar(Modelo modelo) {
        String sql =  "SELECT mt.id_modelo as motor_idModelo, mt.potencia as motor_potencia, mt.tipo_comb as motor_comb, m.categoria as modelo_categoria, m.id as modelo_id, m.nome as modelo_nome, "
                + "mr.id as marca_id, mr.descricao as marca_descricao "
                + "FROM modelo m INNER JOIN marca mr INNER JOIN motor mt ON m.id_marca = mr.id AND mt.id_modelo = m.id WHERE m.id = ?;";
//        String sql =  "SELECT mt.id_modelo as motor_idModelo, mt.potencia as motor_potencia, mt.tipo_comb as motor_comb, m.id as modelo_id, m.nome as modelo_nome, "
//                + "mr.id as marca_id, mr.descricao as marca_descricao "
//                + "FROM modelo m INNER JOIN marca mr ON mr.id = m.id_marca WHERE m.id = ?;";
        Modelo retorno = new Modelo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modelo.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno = populateVO1(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    private Modelo populateVO(ResultSet rs) throws SQLException {
        Modelo modelo = new Modelo();
        Marca marca = new Marca();
        modelo.setMarca(marca);
        
        modelo.setId(rs.getInt("modelo_id"));
        modelo.setNome(rs.getString("modelo_nome"));
        marca.setId(rs.getInt("marca_id"));
        marca.setNome(rs.getString("marca_descricao"));
        modelo.setCategoria(ECategoria.valueOf(rs.getString("modelo_categoria")));
        return modelo;
    }   
    private Modelo populateVOAll(ResultSet rs) throws SQLException {
        Modelo modelo = new Modelo();   
        //Marca marca = new Marca();
        //modelo.setMarca(marca);
        //marca.setId(rs);
        modelo.setId(rs.getInt(rs.getString("modelo_id")));
        modelo.setNome(rs.getString("modelo_nome"));
        
        return modelo;
    }    
    private Modelo populateVO1(ResultSet rs) throws SQLException {
        Modelo modelo = new Modelo();
        Marca marca = new Marca();
        modelo.setMarca(marca);
        modelo.setNome(rs.getString("modelo_nome"));
        marca.setId(rs.getInt("marca_id"));
        marca.setNome(rs.getString("marca_descricao"));
        modelo.setId(rs.getInt("modelo_id"));
        
        modelo.getMotor().setModelo(modelo);
        modelo.getMotor().setPotencia(rs.getInt("motor_potencia"));
        modelo.getMotor().setCombustivel(ECombustivel.valueOf(rs.getString("motor_comb")));
        modelo.setCategoria(ECategoria.valueOf(rs.getString("modelo_categoria")));
        
        return modelo;
    }    
}
