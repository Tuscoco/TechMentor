// package dao;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;
//import config.*;

// import model.Monitoria;

// public class MonitorDAO {

//     private String url;
//     private String user;
//     private String password;

    /*public MonitorDAO(){

        Config config = new Config("config/config.properties");
        this.url = config.getProperty("db.url");
        this.user = config.getProperty("db.user");
        this.password = config.getProperty("db.password");

    }*/

//     public void adicionarMonitor(Monitoria monitor) throws SQLException {
//         try (Connection conn = DriverManager.getConnection(url, user, password)) {
//             String sql = "INSERT INTO monitoria (id_monitor, id_materia, sala, materia_principal) VALUES (?,?,?,?)";
//             PreparedStatement pstmt = conn.prepareStatement(sql);
//             pstmt.setInt(1, monitor.getIdMonitor());
//             pstmt.setInt(2, monitor.getIdMateria());
//             pstmt.setInt(3, 1101);
//             pstmt.setBoolean(4, monitor.getIsMateriaprincipal());

//             pstmt.executeUpdate();
//         }
//     }

//     public void removerMonitor(Monitoria monitor) throws SQLException {
//         try (Connection conn = DriverManager.getConnection(url, user, password)) {
//             String sql = "DELETE FROM monitoria WHERE id_monitor = ? AND id_materia = ?";
//             PreparedStatement pstmt = conn.prepareStatement(sql);
//             pstmt.setInt(1, monitor.getIdMonitor());
//             pstmt.setInt(2, monitor.getIdMateria());
//             pstmt.executeUpdate();
//         }
//     }

//     public List<Monitoria> getTodos() throws SQLException {
//         List<Monitoria> monitores = new ArrayList<>();
//         try (Connection conn = DriverManager.getConnection(url, user, password)) {
//             String sql = "SELECT * FROM monitoria";
//             PreparedStatement pstmt = conn.prepareStatement(sql);
//             ResultSet result = pstmt.executeQuery();

//             while (result.next()) {
// <<<<<<< HEAD
//                 //monitores.add(new Monitoria(result.getInt("id_monitor"), result.getInt("id_materia"),
//                         //result.getInt("sala"), result.getBoolean("materia_principal")));
// >>>>>>> refs/remotes/origin/master
// =======
//                 monitores.add(new Monitoria(result.getInt("id_monitor"), result.getInt("id_materia"),
//                         result.getInt("sala"), result.getBoolean("materia_principal")));

// >>>>>>> ab199aaf8b02ad7ae1ef6ac3ac4e48a7dd6b987e
//             }
//         }
//         return monitores;
//     }



//     public Monitoria getMonitorEMateria(int id_monitor,int id_materia) throws SQLException {
//         Monitoria monitor;
//         try (Connection conn = DriverManager.getConnection(url, user, password)) {
//             String sql = "SELECT * FROM monitoria WHERE id_monitor = ? AND id_materia = ?";
//             PreparedStatement pstmt = conn.prepareStatement(sql);
//             pstmt.setInt(1, id_monitor);
//             pstmt.setInt(2, id_materia);
//             ResultSet result = pstmt.executeQuery();


//             monitor = new Monitoria(result.getInt("id_monitor"), result.getInt("id_materia"),result.getInt("sala"), result.getBoolean("materia_principal"));

//         }
//         return monitor;
//     }

//     public List<Monitoria> getMonitor(int id) throws SQLException {
//         List<Monitoria> monitor = new ArrayList<>();
//         try (Connection conn = DriverManager.getConnection(url, user, password)) {
//             String sql = "SELECT * FROM monitoria WHERE id_monitor = ?";
//             PreparedStatement pstmt = conn.prepareStatement(sql);
//             pstmt.setInt(1, id);
//             ResultSet result = pstmt.executeQuery();
// <<<<<<< HEAD
//             while(result.next()){
//                 //monitor.add(new Monitoria(result.getInt("id_monitor"), result.getInt("id_materia"),result.getInt("sala"),result.getBoolean("materia_principal")));
// =======
//             while (result.next()) {
//                 //monitor.add(new Monitoria(result.getInt("id_monitor"), result.getInt("id_materia"),
//                         //result.getInt("sala"), result.getBoolean("materia_principal")));
// >>>>>>> refs/remotes/origin/master
// =======


//             while (result.next()) {
//                 monitor.add(new Monitoria(result.getInt("id_monitor"), result.getInt("id_materia"),
//                         result.getInt("sala"), result.getBoolean("materia_principal")));

// >>>>>>> ab199aaf8b02ad7ae1ef6ac3ac4e48a7dd6b987e
//             }
//         }
//         return monitor;
//     }

// <<<<<<< HEAD
//     public Monitoria getMonitorMateria(int id_monitor, int id_materia) throws SQLException{
// =======
//     public Monitoria getMonitorMateria(int id_monitor, int id_materia) throws SQLException {
// >>>>>>> refs/remotes/origin/master
//         Monitoria monitor;
//         try (Connection conn = DriverManager.getConnection(url, user, password)) {
//             String sql = "SELECT * FROM monitoria WHERE id_monitor = ? AND id_materia = ?";
//             PreparedStatement pstmt = conn.prepareStatement(sql);
//             pstmt.setInt(1, id_monitor);
//             pstmt.setInt(2, id_materia);
//             ResultSet result = pstmt.executeQuery();
//             //monitor = new Monitoria(result.getInt("id_monitor"), result.getInt("id_materia"),result.getInt("sala"),result.getBoolean("materia_principal"));
// =======
//             if (result.next()) {
//                 monitor = new Monitoria(result.getInt("id_monitor"), result.getInt("id_materia"),
//                         result.getInt("sala"), result.getBoolean("materia_principal"));
//             } else {
//                 monitor = null;  // Handle case where no record is found
//             }
// >>>>>>> refs/remotes/origin/master
//         }
//         //return monitor;
//     }
// =======
// >>>>>>> ab199aaf8b02ad7ae1ef6ac3ac4e48a7dd6b987e

//     public List<Monitoria> getMonitorPorMateria(int id) throws SQLException {
//         List<Monitoria> monitores = new ArrayList<>();
//         try (Connection conn = DriverManager.getConnection(url, user, password)) {
//             String sql = "SELECT * FROM monitoria WHERE id_materia = ?";
//             PreparedStatement pstmt = conn.prepareStatement(sql);
//             pstmt.setInt(1, id);
//             ResultSet result = pstmt.executeQuery();

//             while (result.next()) {
//                 monitores.add(new Monitoria(result.getInt("id_monitor"), result.getInt("id_materia"),
//                         result.getInt("sala"), result.getBoolean("materia_principal")));

//             }
//         }
//         return monitores;
//     }

//     pub lic void ficarOnline(int id) throws SQLException {
//         try (Connection conn = DriverManager.getConnection(url, user, password)) {
//             String sql = "UPDATE monitoria SET online = true WHERE id_monitor = ?";
//             PreparedStatement pstmt = conn.prepareStatement(sql);
//             pstmt.setInt(1, id);
//             pstmt.executeUpdate();
            
//         }
//     }

//     public void alterarSala(int sala, int id) throws SQLException {
//         try (Connection conn = DriverManager.getConnection(url, user, password)) {
//             String sql = "UPDATE monitoria SET sala = ? WHERE id_monitor = ?";
//             PreparedStatement pstmt = conn.prepareStatement(sql);
//             pstmt.setInt(1, sala);
//             pstmt.setInt(2, id);
//             pstmt.executeUpdate();
//         }
//     }

//     public void ficarOffline(int id) throws SQLException {
//         try (Connection conn = DriverManager.getConnection(url, user, password)) {
//             String sql = "UPDATE monitoria SET online = false, sala = 1101 WHERE id_monitor = ?";
//             PreparedStatement pstmt = conn.prepareStatement(sql);
//             pstmt.setInt(1, id);
//             pstmt.executeUpdate();
//         }
// }

