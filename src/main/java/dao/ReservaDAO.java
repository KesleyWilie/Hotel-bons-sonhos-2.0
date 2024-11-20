package dao;

import dto.QuartoDTO;
import dto.ReservaDTO;
import dto.UsuarioDTO;
import utils.mapper.Mapper;
import models.*;
import models.quarto.Quarto;

import java.sql.*;
import java.util.ArrayList;

public class ReservaDAO {
    public void cadastrarReserva(ReservaDTO reserva){
        //Reserva entity = Mapper.parseObject(reserva, Reserva.class);

        String sql = "INSERT INTO reservas (ID, ID_CLIENTE, ID_QUARTO, DATA_CHECKIN, DATA_CHECKOUT, PRECO_TOTAL) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = null;

        try {
            ps = SingletonConnection.getCon().prepareStatement(sql);
            ps.setInt(1, reserva.getId());
            ps.setString(2, reserva.getCliente().getCPF());
            ps.setInt(3, reserva.getQuarto().getCodigoQuarto());
            ps.setDate(4, new Date(reserva.getDataCheckin().getTime()));
            ps.setDate(5, new Date(reserva.getDataCheckout().getTime()));
            ps.setDouble(6, reserva.getPrecoTotal());

            ps.execute();
            ps.close();
            System.out.println("Reserva cadastrada com sucesso");
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<ReservaDTO> listarReservas() {
        String sql = "SELECT id, id_cliente, id_quarto, data_checkin, data_checkout, preco_total FROM reservas";
        ArrayList<ReservaDTO> reservas = new ArrayList<>();
    
        try (Connection con = SingletonConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
    
            while (rs.next()) {
                ReservaDTO reserva = new ReservaDTO();
                reserva.setId(rs.getInt("id"));
                
                UsuarioDTO cliente = new UsuarioDAO().recuperarUsuario(rs.getString("id_cliente"));
                QuartoDTO quarto = new QuartoDAO().recuperarQuarto(rs.getInt("id_quarto"));

                reserva.setCliente(Mapper.parseObject(cliente, Cliente.class));
                reserva.setQuarto(Mapper.parseObject(quarto, Quarto.class));
                
                reserva.setDataCheckin(rs.getDate("data_checkin"));
                reserva.setDataCheckout(rs.getDate("data_checkout"));
                reserva.setPrecoTotal(rs.getDouble("preco_total"));
                
                reservas.add(reserva);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return reservas;
    }    

    public ArrayList<ReservaDTO> listarReservasPorQuarto(int codigoQuarto) {
        String sql = "SELECT id, id_cliente, id_quarto, data_checkin, data_checkout, preco_total FROM reservas WHERE id_quarto = ?";
        ArrayList<ReservaDTO> reservas = new ArrayList<>();

        try (PreparedStatement ps = SingletonConnection.getCon().prepareStatement(sql)) {
            ps.setInt(1, codigoQuarto);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReservaDTO reserva = new ReservaDTO();
                reserva.setId(rs.getInt("id"));

                UsuarioDTO cliente = new UsuarioDAO().recuperarUsuario(rs.getString("id_cliente"));
                QuartoDTO quarto = new QuartoDAO().recuperarQuarto(rs.getInt("id_quarto"));

                reserva.setCliente(Mapper.parseObject(cliente, Cliente.class));
                reserva.setQuarto(Mapper.parseObject(quarto, Quarto.class));

                reserva.setDataCheckin(rs.getDate("data_checkin"));
                reserva.setDataCheckout(rs.getDate("data_checkout"));
                reserva.setPrecoTotal(rs.getDouble("preco_total"));

                reservas.add(reserva);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservas;
    }

    public ArrayList<ReservaDTO> listarReservasPorCliente(String cpf) {
        String sql = "SELECT id, id_cliente, id_quarto, data_checkin, data_checkout, preco_total FROM reservas WHERE id_cliente = ?";
        ArrayList<ReservaDTO> reservas = new ArrayList<>();

        try (PreparedStatement ps = SingletonConnection.getCon().prepareStatement(sql)) {
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReservaDTO reserva = new ReservaDTO();
                reserva.setId(rs.getInt("id"));

                UsuarioDTO cliente = new UsuarioDAO().recuperarUsuario(rs.getString("id_cliente"));
                QuartoDTO quarto = new QuartoDAO().recuperarQuarto(rs.getInt("id_quarto"));

                reserva.setCliente(Mapper.parseObject(cliente, Cliente.class));
                reserva.setQuarto(Mapper.parseObject(quarto, Quarto.class));

                reserva.setDataCheckin(rs.getDate("data_checkin"));
                reserva.setDataCheckout(rs.getDate("data_checkout"));
                reserva.setPrecoTotal(rs.getDouble("preco_total"));

                reservas.add(reserva);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservas;
    }

    public ReservaDTO recuperarReserva(int id) {
        String sql = "SELECT id, id_cliente, id_quarto, data_checkin, data_checkout, preco_total FROM reservas WHERE id = ?";
        ReservaDTO reserva = null;
    
        try (PreparedStatement ps = SingletonConnection.getCon().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) { 
                reserva = new ReservaDTO();
                reserva.setId(rs.getInt("id"));
                
                UsuarioDTO cliente = new UsuarioDAO().recuperarUsuario(rs.getString("id_cliente"));
                QuartoDTO quarto = new QuartoDAO().recuperarQuarto(rs.getInt("id_quarto"));
                
                reserva.setCliente(Mapper.parseObject(cliente, Cliente.class));
                reserva.setQuarto(Mapper.parseObject(quarto, Quarto.class));
                
                reserva.setDataCheckin(rs.getDate("data_checkin"));
                reserva.setDataCheckout(rs.getDate("data_checkout"));
                reserva.setPrecoTotal(rs.getDouble("preco_total"));
            }
    
        } catch(SQLException e){
            e.printStackTrace();
        }
    
        return reserva;
    }

    public boolean atualizarReserva(ReservaDTO reserva) {
        String sql = "UPDATE reservas SET id_cliente = ?, id_quarto = ?, data_checkin = ?, data_checkout = ?, preco_total = ? WHERE id = ?";

        //Reserva entity = Mapper.parseObject(reserva, Reserva.class);
    
        try (PreparedStatement ps = SingletonConnection.getCon().prepareStatement(sql)) {
            ps.setString(1, reserva.getCliente().getCPF());
            ps.setInt(2, reserva.getQuarto().getCodigoQuarto());
            ps.setDate(3, new Date(reserva.getDataCheckin().getTime()));
            ps.setDate(4, new Date(reserva.getDataCheckout().getTime()));
            ps.setDouble(5, reserva.getPrecoTotal());
            ps.setInt(6, reserva.getId());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return false;
    }
    
    public void removerReserva(int id) {
        String sql = "DELETE FROM reservas WHERE id = ?";
        PreparedStatement ps = null;

        try {
            ps = SingletonConnection.getCon().prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            ps.close();
            System.out.println("Reserva removida com sucesso");
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
