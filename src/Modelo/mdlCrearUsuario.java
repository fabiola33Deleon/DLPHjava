
package Modelo;

import Vista.frmCrearU;
import com.sun.jdi.connect.spi.Connection;
import java.beans.Statement;
import java.util.UUID;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class mdlCrearUsuario {

    
    private String nombre; 
    private String contrasenaUsuario; 
    private String correoUsuario; 
    private String rolUsuario;
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }

    public void setContrasenaUsuario(String contrasenaUsuario) {
        this.contrasenaUsuario = contrasenaUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }
    
    public void Guardar() {
        try {
            String sql = "INSERT INTO Usuarios (UUID, Nombre, contrasenaUsuario, correoUsuario, rollUsuario) VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement pstmt = ClaseConexion.prepareStatement(sql);
            
            pstmt.setString(1, UUID.randomUUID().toString());
            pstmt.setString(2, getNombre());
            pstmt.setString(3, getContrasenaUsuario());
            pstmt.setString(4, getCorreoUsuario());
            pstmt.setString(5, getRolUsuario());

         
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Este es el error en el modelo: método Guardar " + ex);
        }
    }
    
    public void Mostrar(JTable tabla) {
       
        Connection conexion = (Connection) ClaseConexion.getConexion();
      
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"UUID", "Nombre", "contrasenaUsuario", "correoUsuario", "rollUsuario"});
        
        try {
       
            String query = "SELECT * FROM Usuarios"; 
           
            Statement statement = conexion.createStatement();
            
            ResultSet rs = statement.executeQuery(query);
          
            while (rs.next()) {
                
                modelo.addRow(new Object[]{ 
                    rs.getString("Nombre"), 
                    rs.getString("Contraseña"), 
                    rs.getString("Correo"),
                    rs.getString("Rol")
                });
            }
         
            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setMinWidth(0);
            tabla.getColumnModel().getColumn(0).setMaxWidth(0);
            tabla.getColumnModel().getColumn(0).setWidth(0);
        } catch (Exception e) {
            System.out.println("Este es el error en el modelo, método Mostrar " + e);
        }
    }
    
    public void Eliminar(JTable tabla) {
   
    Connection conexion = (Connection) ClaseConexion.getConexion();

    
    int filaSeleccionada = tabla.getSelectedRow();

   
    if (filaSeleccionada != -1) {
      
        String miId = tabla.getValueAt(filaSeleccionada, 0).toString();

       
        try {
            String sql = "DELETE FROM Usuarios WHERE UUID = ?";
            PreparedStatement deleteUsuario = conexion.prepareStatement(sql);
            deleteUsuario.setString(1, miId);
            deleteUsuario.executeUpdate();
        } catch (Exception e) {
            System.out.println("Este es el error en el método Eliminar: " + e);
        }
    } else {
        System.out.println("No se seleccionó ninguna fila.");
    }
}

    public void Actualizar(JTable tabla) {
  
    Connection conexion = (Connection) ClaseConexion.getConexion();

 
    int filaSeleccionada = tabla.getSelectedRow();


    if (filaSeleccionada != -1) {

        String miUUId = tabla.getValueAt(filaSeleccionada, 0).toString();

  
        try {
            String sql = "UPDATE Usuarios SET Nombre = ?, contrasenaUsuario = ?, correoUsuario = ?, rollUsuario = ? WHERE UUID = ?";
            PreparedStatement updateUsuario = conexion.prepareStatement(sql);

            updateUsuario.setString(1, getNombre());
            updateUsuario.setString(2, getContrasenaUsuario());
            updateUsuario.setString(3, getCorreoUsuario());
            updateUsuario.setString(4, getRolUsuario());
            updateUsuario.setString(5, miUUId);
            updateUsuario.executeUpdate();

        } catch (Exception e) {
            System.out.println("Este es el error en el método Actualizar: " + e);
        }
    } else {
        System.out.println("No se seleccionó ninguna fila.");
    }
}

   public void cargarDatosTabla(frmCrearU vista) {
    limpiarCampos(vista);

    int filaSeleccionada = vista.Usuario.getSelectedRow();

    if (filaSeleccionada != -1) {
        String UUID_USUARIO = vista.Usuarios.getValueAt(filaSeleccionada, 0).toString();
        String Nombre = vista.Usuarios.getValueAt(filaSeleccionada, 1).toString();
        String contrasenaUsuario = vista.Usuarios.getValueAt(filaSeleccionada, 2).toString();
        String CorreoUsuario = vista.Usuarios.getValueAt(filaSeleccionada, 3).toString();
        String rollUsuario = vista.Usuarios.getValueAt(filaSeleccionada, 4).toString();

        
        if (validarNombre(Nombre) && 
            validarContrasena(contrasenaUsuario) && 
            validarCorreo(CorreoUsuario) && 
            validarRol(rollUsuario)) {
     
            vista.txtNombreCu.setText(Nombre);
            vista.txtContrasenaCu.setText(contrasenaUsuario);
            vista.txtCorreoCu.setText(CorreoUsuario);
            vista.jSpinner1.setValue(rollUsuario); // Se usa setValue para JSpinner
        } else {
            System.out.println("Error: Uno o más campos contienen datos inválidos.");
        }
    } else {
        System.out.println("No se seleccionó ninguna fila.");
    }
}

private void limpiarCampos(frmCrearU vista) {
    vista.txtNombreCu.setText("");
    vista.txtContrasenaCu.setText("");
    vista.txtCorreoCu.setText("");
    vista.jSpinner1.setValue(""); 
}


private boolean validarNombre(String nombre) {
    if (nombre == null || nombre.trim().isEmpty()) {
        System.out.println("El campo Nombre no puede estar vacío.");
        return false;
    }

    return true;
}


private boolean validarContrasena(String contrasena) {
    if (contrasena == null || contrasena.trim().isEmpty()) {
        System.out.println("El campo Contraseña no puede estar vacío.");
        return false;
    }
    if (contrasena.length() < 6) {
        System.out.println("La contraseña debe tener al menos 6 caracteres.");
        return false;
    }
 
    return true;
}


private boolean validarCorreo(String correo) {
    if (correo == null || correo.trim().isEmpty()) {
        System.out.println("El campo Correo no puede estar vacío.");
        return false;
    }
    if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
        System.out.println("El formato del correo es inválido.");
        return false;
    }
    return true;
}


private boolean validarRol(String rol) {
    if (rol == null || rol.trim().isEmpty()) {
        System.out.println("El campo Rol no puede estar vacío.");
        return false;
    }
 
    return true;
}

}

    
