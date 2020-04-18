package proyecto;

import com.mysql.jdbc.Connection;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Principal extends JFrame {

    JButton b1 = new JButton("Disponibles");
    JButton b2 = new JButton("Reparacion");

    Principal() {
        super("Ingenieria de software");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(44, 62, 80)); //color de fondo del JFrame
        
        lamina l = new lamina();//Crea JPanel de Reparacion
        lamina1 l1 = new lamina1();//Crea JPanel de disponibles
        
        //Darle tamaño y añadir botones y JPanel's
        setLayout(null);
        setBounds(0, 0, 720, 500);
        add(l);
        add(l1);
        l.setVisible(false);
        b1.setBounds(220, 10, 105, 32);
        b2.setBounds(370, 10, 105, 32);
        add(b1);
        add(b2);
        
        //Boton para mostrar opcion de computadoras en reparacion
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l1.setVisible(true);
                l.setVisible(false);
            }
        });
        
        //Boton para mostrar la opcion de computadoras disponibles
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l.setVisible(true);
                l1.setVisible(false);
            }
        });
    }

    //Clase del Panel de computadoras en reparacion    
    public class lamina extends JPanel {

        Connection conectar = null;//Objeto para la conexion a la base de datos
        JTable datos = new JTable();//Tabla donde se mestrarán los datos
        
        lamina() {
            //Aqui se hace la coneccion a la base de datos
            try {
                Class.forName("org.gjt.mm.mysql.Driver");
                conectar = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/computadoras", "root", "");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error de conexion de la base de datos");
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "no pa");
            }
            
            mostrarDatos();//Metodo para mostrar los datos en la tabla
            
            //Añadir JButton's, JLabel y TextField's
            JLabel titulo = new JLabel("Computadoras en Reparacion");
            JLabel modelo = new JLabel("Modelo");
            JLabel id = new JLabel("Id");
            JLabel so = new JLabel("S.O");
            JLabel marca = new JLabel("Marca");
            TextField inModelo = new TextField();
            TextField inId = new TextField();
            TextField inSo = new TextField();
            TextField inMarca = new TextField();
            JButton ingresar = new JButton("Ingresar");
            setBounds(0, 50, 720, 500);
            setBackground(new Color(133, 193, 233));
            setLayout(null);
            titulo.setBounds(0, 0, 340, 32);
            modelo.setBounds(0, 80, 60, 30);
            id.setBounds(0, 130, 60, 30);
            so.setBounds(0, 180, 60, 30);
            marca.setBounds(0, 230, 60, 30);
            inModelo.setBounds(80, 84, 200, 22);
            inId.setBounds(80, 134, 200, 22);
            inSo.setBounds(80, 184, 200, 22);
            inMarca.setBounds(80, 234, 200, 22);
            ingresar.setBounds(127, 280, 105, 32);
            datos.setBounds(330, 70, 330, 200);
            titulo.setFont(new java.awt.Font("Tahoma", 0, 25));
            modelo.setFont(new java.awt.Font("Tahoma", 0, 17));
            id.setFont(new java.awt.Font("Tahoma", 0, 17));
            so.setFont(new java.awt.Font("Tahoma", 0, 17));
            marca.setFont(new java.awt.Font("Tahoma", 0, 17));
            add(titulo);
            add(modelo);
            add(id);
            add(so);
            add(marca);
            add(inModelo);
            add(inId);
            add(inSo);
            add(inMarca);
            add(ingresar);
            add(datos);
            
            //Boton para ingresar los datos la base de datos
            ingresar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   try {
                        PreparedStatement pst = conectar.prepareStatement("INSERT INTO reparacion(modelo,id,so,marca) VALUES(?,?,?,?)");
                        pst.setString(1, inModelo.getText());
                        pst.setString(2, inId.getText());
                        pst.setString(3, inSo.getText());
                        pst.setString(4, inMarca.getText());
                        int a = pst.executeUpdate();
                        if (a > 0) {
                            JOptionPane.showMessageDialog(null, "Registro exitoso");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al agregar");
                        }
                        mostrarDatos();
                    }catch (SQLException ex){
                        JOptionPane.showMessageDialog(null, "no pa");
                    }
                   
                }
            });
        }
        
        //Metodo para mostrar los datos en la tabla
        public void mostrarDatos(){
            DefaultTableModel mod=new DefaultTableModel();
      
            mod.addColumn("Modelo");
            mod.addColumn("Id");
            mod.addColumn("S.O");
            mod.addColumn("Marca");
            datos.setModel(mod);
            String sql = "SELECT * FROM reparacion";
            String []dat=new String [4];
        try{
            Statement st=conectar.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
            dat[0]=rs.getString(1);
            dat[1]=rs.getString(2);
            dat[2]=rs.getString(3);
            dat[3]=rs.getString(4);
            mod.addRow(dat);
            }
            datos.setModel(mod);
        }catch(SQLException ex){
        } 
        }
    }
    
    //Clase del Panel de computadoras disponibles
    public class lamina1 extends JPanel {

        Connection conectar = null;//Objeto para la conexion a la base de datos
        JTable datos = new JTable();//Tabla donde se mostrarán los datos
        
        lamina1() {
            //Aqui se hace la coneccion a la base de datos
            try {
                Class.forName("org.gjt.mm.mysql.Driver");
                conectar = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/computadoras", "root", "");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error de conexion de la base de datos");
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "no pa");
            }
            
            mostrarDatos();//Metodo para mostrar los datos en la tabla
            
            //Añadir JButton's, JLabel y TextField's
            JLabel titulo = new JLabel("Computadoras Disponibles");
            JLabel modelo = new JLabel("Modelo");
            JLabel id = new JLabel("Id");
            JLabel so = new JLabel("S.O");
            JLabel marca = new JLabel("Marca");
            TextField inModelo = new TextField();
            TextField inId = new TextField();
            TextField inSo = new TextField();
            TextField inMarca = new TextField();
            JButton ingresar = new JButton("Ingresar");
            setBounds(0, 50, 720, 500);
            setBackground(new Color(26, 188, 156));
            setLayout(null);
            titulo.setBounds(0, 0, 300, 32);
            modelo.setBounds(0, 80, 60, 30);
            id.setBounds(0, 130, 60, 30);
            so.setBounds(0, 180, 60, 30);
            marca.setBounds(0, 230, 60, 30);
            inModelo.setBounds(80, 84, 200, 22);
            inId.setBounds(80, 134, 200, 22);
            inSo.setBounds(80, 184, 200, 22);
            inMarca.setBounds(80, 234, 200, 22);
            ingresar.setBounds(127, 280, 105, 32);
            datos.setBounds(330, 70, 330, 200);
            titulo.setFont(new java.awt.Font("Tahoma", 0, 25));
            modelo.setFont(new java.awt.Font("Tahoma", 0, 17));
            id.setFont(new java.awt.Font("Tahoma", 0, 17));
            so.setFont(new java.awt.Font("Tahoma", 0, 17));
            marca.setFont(new java.awt.Font("Tahoma", 0, 17));
            add(titulo);
            add(modelo);
            add(id);
            add(so);
            add(marca);
            add(inModelo);
            add(inId);
            add(inSo);
            add(inMarca);
            add(ingresar);
            add(datos);
            
            //Boton para ingresar los datos la base de datos
            ingresar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   try {
                        PreparedStatement pst = conectar.prepareStatement("INSERT INTO disponibles(modelo,id,so,marca) VALUES(?,?,?,?)");
                        pst.setString(1, inModelo.getText());
                        pst.setString(2, inId.getText());
                        pst.setString(3, inSo.getText());
                        pst.setString(4, inMarca.getText());
                        int a = pst.executeUpdate();
                        if (a > 0) {
                            JOptionPane.showMessageDialog(null, "Registro exitoso");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al agregar");
                        }
                        mostrarDatos();
                    }catch (SQLException ex){
                        JOptionPane.showMessageDialog(null, "no pa");
                    }
                   
                }
            });
        }
        
        //Metodo para mostrar los datos en la tabla
        public void mostrarDatos(){
            DefaultTableModel mod=new DefaultTableModel();
      
            mod.addColumn("Modelo");
            mod.addColumn("Id");
            mod.addColumn("S.O");
            mod.addColumn("Marca");
            datos.setModel(mod);
            String sql = "SELECT * FROM disponibles";
            String []dat=new String [4];
        try{
            Statement st=conectar.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
            dat[0]=rs.getString(1);
            dat[1]=rs.getString(2);
            dat[2]=rs.getString(3);
            dat[3]=rs.getString(4);
            mod.addRow(dat);
            }
            datos.setModel(mod);
        }catch(SQLException ex){
        } 
        }
    }
}
