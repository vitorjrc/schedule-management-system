package schedulemanager.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.function.Consumer; // This is the type we are going to use for callback methods. A Consumer<T> is a function that receives one parameter of type T and returns nothing.
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


/**
 * View class - The only one that knows which buttons and fields exist. It knows nothing about neither controller nor model.
 */
public class View extends javax.swing.JFrame {
    
    private RegistrationArea registrationDialog;
    private LoginArea loginDialog;
    
    private ArrayList<Consumer<ArrayList<String>>> registeredListeners = new ArrayList<Consumer<ArrayList<String>>>(); // Example array of callbacks to call when a register happens
    private ArrayList<Consumer<ArrayList<String>>> swapOffer = new ArrayList<Consumer<ArrayList<String>>>();
    private ArrayList<Consumer<ArrayList<String>>> IO = new ArrayList<Consumer<ArrayList<String>>>();
    private ArrayList<Consumer<ArrayList<String>>> enrollStudent = new ArrayList<Consumer<ArrayList<String>>>();
    private ArrayList<Consumer<ArrayList<String>>> createTeacher = new ArrayList<Consumer<ArrayList<String>>>();
    private ArrayList<Consumer<ArrayList<String>>> removeStudentFromShift = new ArrayList<Consumer<ArrayList<String>>>();
    private ArrayList<Consumer<ArrayList<String>>> createShift = new ArrayList<Consumer<ArrayList<String>>>();
    private ArrayList<Consumer<ArrayList<String>>> createCourse = new ArrayList<Consumer<ArrayList<String>>>();
    private ArrayList<Consumer<ArrayList<String>>> cancelOffer = new ArrayList<Consumer<ArrayList<String>>>();
    private ArrayList<Consumer<ArrayList<String>>> acceptOffer = new ArrayList<Consumer<ArrayList<String>>>();
    private ArrayList<Consumer<ArrayList<String>>> lockSwaps = new ArrayList<Consumer<ArrayList<String>>>();
    
    //construtor da view
    public View() {
        
        initComponents();
        
        loginDialog = new LoginArea(View.this, true);
        registrationDialog = new RegistrationArea(View.this);
        
        // background - White
        getContentPane().setBackground(Color.WHITE);
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        

        
        // set JFrame to center of screen
        this.setLocationRelativeTo(null);
        
        // icon
        this.setIcon();
        
        // columns cant be dragged
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.getTableHeader();
        ((DefaultTableCellRenderer)jTable1.getTableHeader().getDefaultRenderer())
            .setHorizontalAlignment(JLabel.LEFT);
        
        
        // disabling tabs
        jTabbedPane1.remove(jPanel1);
        jTabbedPane1.remove(jPanel2);
        jTabbedPane1.remove(jPanel3);
        jTabbedPane1.remove(jPanel6);
        jTabbedPane1.remove(jPanel9);
        jTabbedPane1.remove(jPanel5);
        jButton4.setEnabled(false);
        
        // disabling teacher's header
        jLabel49.setVisible(false);
        jLabel53.setVisible(false);
    }
    
    // Example method that receives a callback from the controller
    // The controller calls this method by passing its own method,
    // which we call when an event happens (in this case, when the user presses login)
    //
    
    public void onRegister(Consumer<ArrayList<String>> callback) {
        
        registeredListeners.add(0, callback);
    }
    
    public void checkedCourse(Consumer<ArrayList<String>> callback) {
        
        swapOffer.add(0, callback);
    }
    
    public void checkedOfferedShift(Consumer<ArrayList<String>> callback) {
        
        swapOffer.add(1, callback);
    }
    
    public void swapOffer(Consumer<ArrayList<String>> callback) {
        
        swapOffer.add(2, callback);
    }
    
    public void saveButton(Consumer<ArrayList<String>> callback) {
        
        IO.add(0, callback);
    }
    
    public void showCourseStudents(Consumer<ArrayList<String>> callback) {
        
        enrollStudent.add(0, callback);
    }
    
    public void StudentShifts(Consumer<ArrayList<String>> callback) {
        
        enrollStudent.add(1, callback);
    }
    
    public void possibleStudentShifts(Consumer<ArrayList<String>> callback) {
        
        enrollStudent.add(2, callback);
    }
    
    public void enrollButton(Consumer<ArrayList<String>> callback) {
        
        enrollStudent.add(3, callback);
    }
    
    public void createTeacher(Consumer<ArrayList<String>> callback) {
        
        createTeacher.add(0, callback);
    }
    
    public void shiftsOfStudent(Consumer<ArrayList<String>> callback) {
        
        removeStudentFromShift.add(0, callback);
    }
    
    public void getStudentToRemove(Consumer<ArrayList<String>> callback) {
        
        removeStudentFromShift.add(1, callback);
    }
    
    public void removeButton(Consumer<ArrayList<String>> callback) {
        
        removeStudentFromShift.add(2, callback);
    }
    
    public void createShift(Consumer<ArrayList<String>> callback) {
        
        createShift.add(0, callback);
    }
    
    public void createCourse(Consumer<ArrayList<String>> callback) {
        
        createCourse.add(0, callback);
    }
    
    public void Logout(Consumer<ArrayList<String>> callback) {
        
        registeredListeners.add(1, callback);
    }
    
    public void cancelOffer(Consumer<ArrayList<String>> callback) {
        
        cancelOffer.add(0, callback);
    }
    
    public void acceptOffer(Consumer<ArrayList<String>> callback) {
        
        acceptOffer.add(0, callback);
    }
    
    public void lockSwaps(Consumer<ArrayList<String>> callback) {
        
        lockSwaps.add(0, callback);
    }
    
    public void unlockSwaps(Consumer<ArrayList<String>> callback) {
        
        lockSwaps.add(1, callback);
    }
    
    public void studentInterface() {
        
        // show "Logado como: ...."
        jLabel1.setVisible(true);
        
        // disable borders
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
        jScrollPane2.setBorder(BorderFactory.createEmptyBorder());
        jScrollPane3.setBorder(BorderFactory.createEmptyBorder());
        jScrollPane4.setBorder(BorderFactory.createEmptyBorder());
        
        // resetting panels
        jPanel7.removeAll();
        jPanel8.removeAll();
        jPanel4.removeAll();
        
        // show student tabs
        jTabbedPane1.addTab("Minha Área", jPanel1);
        jTabbedPane1.addTab("Minhas Trocas", jPanel2);
        jTabbedPane1.addTab("Ver Lista de Trocas Pendentes", jPanel3);
        jTabbedPane1.remove(jPanel10);
       
        
    }
    
    public void adminInterface() {
        // show "Logado como: ...."
        jLabel1.setVisible(true);
        
        // show admin tabs
        jTabbedPane1.addTab("Criar UCs e Turnos", jPanel6);
        jTabbedPane1.addTab("Gestão", jPanel9);
        jTabbedPane1.addTab("Criar Docentes", jPanel5);
        jTabbedPane1.remove(jPanel10);
        
        // show "Logado como Administrador"
        jLabel1.setText("Administrador");
        
        jButton2.setVisible(true);
        jButton5.setVisible(true);
        
    }
    
    public void teacherInterface(String teacherName, String teacherCourse) {
        
        // show "Logado como: ...."
        jLabel1.setVisible(true);
        
        // show teacher tab
        jTabbedPane1.addTab("Gestão da UC", jPanel9);
        jTabbedPane1.remove(jPanel10);
        
        jLabel49.setVisible(true);
        jLabel53.setVisible(true);
        jLabel53.setText(teacherCourse);
        jLabel1.setText(teacherName);
        
        String[] course = new String[1];
            course[0] = teacherCourse;

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(course));
        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel(course));
        
        jButton2.setVisible(false);
        jButton5.setVisible(false);
        
    }
    
    // Method that sets an icon for the program
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.jpg")));           
    }
    

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel15 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jComboBox13 = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jTextField21 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jComboBox7 = new javax.swing.JComboBox<>();
        jComboBox8 = new javax.swing.JComboBox<>();
        jComboBox9 = new javax.swing.JComboBox<>();
        jButton8 = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        jComboBox10 = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        jComboBox11 = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        jComboBox12 = new javax.swing.JComboBox<>();
        jButton9 = new javax.swing.JButton();
        jLabel49 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logotipo_Interface.jpg"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GodSwap - Sistema de Gestão de Turnos");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Dados Pessoais:");

        jLabel4.setText("ID:");

        jLabel5.setText("Estatuto:");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logotipo_Interface.jpg"))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setText("Minhas UCs:");

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "UC", "Turno", "Sala", "Professor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setFocusable(false);
        jTable1.setGridColor(new java.awt.Color(204, 204, 204));
        jTable1.setOpaque(false);
        jTable1.setRequestFocusEnabled(false);
        jTable1.setRowSelectionAllowed(false);
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(1).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(300);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(350);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(348, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel3))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel20))
                        .addGap(72, 72, 72)
                        .addComponent(jLabel18))
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Minha Área", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Efetuar Troca:");

        jLabel7.setText("Uc pretendida:");

        jLabel8.setText("Turno pretendido:");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logotipo_Interface.jpg"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Ofertas ativas:");

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton3.setText("Realizar Pedido");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel42.setText("Turno atual:");

        jComboBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox13ActionPerformed(evt);
            }
        });

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 449, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );

        jScrollPane4.setViewportView(jPanel8);

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel43.setText("Histórico de Trocas:");

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 445, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(jPanel7);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel43)
                        .addGap(71, 71, 71))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(356, 356, 356))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel6))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel42))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel42)
                                    .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(48, 48, 48))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addGap(39, 39, 39)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel43)))
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Minhas Trocas", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("Trocas pendentes:");

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);
        jScrollPane2.setForeground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 906, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanel4);

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logotipo_Interface.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 908, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jLabel13))
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Ver Lista de Trocas Pendentes", jPanel3);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel21.setText("Criar Turno:");

        jLabel22.setText("ID:");

        jLabel23.setText("Número máximo:");

        jLabel24.setText("Docente:");

        jLabel25.setText("Unidade Curricular:");

        jButton7.setText("Criar UC");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel33.setText("Criar UC:");

        jLabel37.setText("ID:");

        jLabel38.setText("Nome:");

        jButton10.setText("Criar Turno");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel45.setText("Docente:");

        jLabel46.setText("Sala:");

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logotipo_Interface.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addComponent(jLabel21)
                            .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel39))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(93, 505, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7)
                    .addComponent(jButton10))
                .addGap(336, 336, 336))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel21)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel45)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46)
                            .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel39))
                .addGap(18, 18, 18)
                .addComponent(jButton10)
                .addGap(50, 50, 50)
                .addComponent(jLabel33)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addContainerGap(118, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Criar Turnos e UCs", jPanel6);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel44.setText("Criar Docente:");

        jLabel47.setText("Nome:");

        jLabel48.setText("ID:");

        jLabel50.setText("Password:");

        jButton12.setText("Criar Docente");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logotipo_Interface.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(370, 370, 370)
                .addComponent(jButton12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel44))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel50)
                            .addComponent(jLabel48)
                            .addComponent(jLabel47))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 400, Short.MAX_VALUE)
                .addComponent(jLabel16))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel44)
                        .addGap(25, 25, 25)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47)
                            .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel48)
                            .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel50)
                            .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12)
                .addContainerGap(336, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Criar Docente", jPanel5);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bigLogo.jpg"))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel11.setText("Por favor, efetue o login ou registe-se.");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addComponent(jLabel10))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(309, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Início", jPanel10);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel27.setText("Remover Aluno de Turno:");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel28.setText("Inscrever Aluno em Turno:");

        jLabel29.setText("Unidade Curricular:");

        jLabel30.setText("ID do Turno origem:");

        jLabel31.setText("ID do Turno destino:");

        jLabel32.setText("Aluno:");

        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });

        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });

        jComboBox9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox9ActionPerformed(evt);
            }
        });

        jButton8.setText("Inscrever Aluno");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel34.setText("Unidade Curricular:");

        jComboBox10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox10ActionPerformed(evt);
            }
        });

        jLabel35.setText("Turno:");

        jComboBox11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox11ActionPerformed(evt);
            }
        });

        jLabel36.setText("Aluno:");

        jButton9.setText("Remover Aluno");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel49.setText("A minha UC:");

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logotipo_Interface.jpg"))); // NOI18N

        jButton2.setText("Ativar trocas");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setText("Desativar Trocas");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel35)
                    .addComponent(jLabel34)
                    .addComponent(jLabel36))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9))
                .addContainerGap(620, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel29)
                                .addComponent(jLabel30)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel26))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(30, 30, 30)
                .addComponent(jButton5)
                .addGap(37, 37, 37))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel49)
                            .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel26))
                .addGap(18, 18, 18)
                .addComponent(jButton8)
                .addGap(59, 59, 59)
                .addComponent(jLabel27)
                .addGap(29, 29, 29)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton5))
                .addGap(27, 27, 27))
        );

        jTabbedPane1.addTab("Gestão do Pessoal", jPanel9);

        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel17.setText("Logado como: ");

        jButton4.setText("Logout");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jMenu1.setText("Ficheiro");

        jMenuItem1.setText("Carregar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Guardar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Autenticação");

        jMenuItem3.setText("Registo");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jLabel17)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4)))
                .addGap(12, 12, 12)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //botao login
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        loginDialog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed
    
    // Trocas - UC Pretendida
    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        
        String selectedCourse = jComboBox2.getItemAt(jComboBox2.getSelectedIndex());
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = swapOffer.get(0);
        
        sc.add(0, selectedCourse);
        
        method.accept(sc);
    }//GEN-LAST:event_jComboBox2ActionPerformed

    // botão logout
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        JOptionPane.showMessageDialog(null, "A sair...");
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = registeredListeners.get(1);
        
        method.accept(sc);
        
        jButton4.setEnabled(false);
        jButton1.setEnabled(true);
        jLabel1.setVisible(false);
        
        // disabling tabs
        jTabbedPane1.remove(jPanel1);
        jTabbedPane1.remove(jPanel2);
        jTabbedPane1.remove(jPanel3);
        jTabbedPane1.remove(jPanel6);
        jTabbedPane1.remove(jPanel9);
        jTabbedPane1.remove(jPanel5);
        jTabbedPane1.addTab("Início", jPanel10);
        
        // disabling teacher's header
        jLabel49.setVisible(false);
        jLabel53.setVisible(false);
        
    }//GEN-LAST:event_jButton4ActionPerformed
    
    // botão oferta
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        String selectedCourse = jComboBox2.getItemAt(jComboBox2.getSelectedIndex());
        String offeredShift = jComboBox13.getItemAt(jComboBox13.getSelectedIndex());
        String selectedShift = jComboBox3.getItemAt(jComboBox3.getSelectedIndex());
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = swapOffer.get(2);
        
        sc.add(0, selectedCourse);
        sc.add(1, offeredShift);
        sc.add(2, selectedShift);
        
        method.accept(sc);
    }//GEN-LAST:event_jButton3ActionPerformed

    // botão criar UC
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        
        String newID = jTextField13.getText();
        String newName = jTextField14.getText();
        String selectedTeacher = jComboBox5.getItemAt(jComboBox5.getSelectedIndex());
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = createCourse.get(0);
        
        sc.add(0, newID);
        sc.add(1, newName);
        sc.add(2, selectedTeacher);
        
        method.accept(sc);
        
        jTextField13.setText("");
        jTextField14.setText("");
    }//GEN-LAST:event_jButton7ActionPerformed

    // botao inscrever aluno em turno
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        
        String selectedCourse = jComboBox6.getItemAt(jComboBox6.getSelectedIndex());
        String selectedStudent = jComboBox9.getItemAt(jComboBox9.getSelectedIndex());
        String originShift = jComboBox7.getItemAt(jComboBox7.getSelectedIndex());
        String destinationShift = jComboBox8.getItemAt(jComboBox8.getSelectedIndex());
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = enrollStudent.get(3);
        
        sc.add(0, selectedCourse);
        sc.add(1, selectedStudent);
        sc.add(2, originShift);
        sc.add(3, destinationShift);
        
        method.accept(sc);
    }//GEN-LAST:event_jButton8ActionPerformed

    // botao remover aluno de turno
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        
        String selectedCourse = jComboBox10.getItemAt(jComboBox10.getSelectedIndex());
        String selectedShift = jComboBox11.getItemAt(jComboBox11.getSelectedIndex());
        String selectedStudent = jComboBox12.getItemAt(jComboBox12.getSelectedIndex());
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = removeStudentFromShift.get(2);
        
        sc.add(0, selectedCourse);
        sc.add(1, selectedShift);
        sc.add(2, selectedStudent);
        
        method.accept(sc);
    }//GEN-LAST:event_jButton9ActionPerformed

    // botao criar turno
    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        
        String selectedCourse = jComboBox4.getItemAt(jComboBox4.getSelectedIndex());
        String newID = jTextField11.getText();
        String newLimit = jTextField12.getText();
        String newTeacher = jTextField18.getText();
        String newClassroom = jTextField19.getText();
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = createShift.get(0);
        
        sc.add(0, selectedCourse);
        sc.add(1, newID);
        sc.add(2, newLimit);
        sc.add(3, newTeacher);
        sc.add(4, newClassroom);
        
        method.accept(sc);
        
        jTextField11.setText("");
        jTextField12.setText("");
        jTextField18.setText("");
        jTextField19.setText("");
        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jComboBox13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox13ActionPerformed
        
        String selectedCourse = jComboBox2.getItemAt(jComboBox2.getSelectedIndex());
        String selectedOfferedShift = jComboBox13.getItemAt(jComboBox13.getSelectedIndex());
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = swapOffer.get(1);
        
        sc.add(0, selectedCourse);
        sc.add(1, selectedOfferedShift);
        
        method.accept(sc);
        
    }//GEN-LAST:event_jComboBox13ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
    
        String selectedCourse = jComboBox6.getItemAt(jComboBox6.getSelectedIndex());
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = enrollStudent.get(0);
        
        sc.add(0, selectedCourse);
        
        method.accept(sc);
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
       
        String selectedCourse = jComboBox6.getItemAt(jComboBox6.getSelectedIndex());
        String selectedOriginShift = jComboBox7.getItemAt(jComboBox7.getSelectedIndex());
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = enrollStudent.get(2);
        
        sc.add(0, selectedCourse);
        sc.add(1, selectedOriginShift);
        
        method.accept(sc);
        
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox9ActionPerformed
        
        String selectedCourse = jComboBox6.getItemAt(jComboBox6.getSelectedIndex());
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = enrollStudent.get(1);
        
        sc.add(0, selectedCourse);
        
        method.accept(sc);
    }//GEN-LAST:event_jComboBox9ActionPerformed

    private void jComboBox10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox10ActionPerformed
        
        String selectedCourse = jComboBox10.getItemAt(jComboBox10.getSelectedIndex());
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = removeStudentFromShift.get(0);
        
        sc.add(0, selectedCourse);
        
        method.accept(sc);
    }//GEN-LAST:event_jComboBox10ActionPerformed

    private void jComboBox11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox11ActionPerformed
        
        String selectedCourse = jComboBox10.getItemAt(jComboBox10.getSelectedIndex());
        String selectedShift = jComboBox11.getItemAt(jComboBox11.getSelectedIndex());
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = removeStudentFromShift.get(1);
        
        sc.add(0, selectedCourse);
        sc.add(1, selectedShift);
        
        method.accept(sc);
    }//GEN-LAST:event_jComboBox11ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = IO.get(1);
        method.accept(sc);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = IO.get(0);
        method.accept(sc);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        
            
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = registeredListeners.get(0);
        method.accept(sc);
        
        registrationDialog.setVisible(true);
        this.registrationOpened();
        jButton1.setEnabled(false);  
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        
        String teacherName = jTextField20.getText();
        String teacherID = jTextField21.getText();
        String teacherPassword = jTextField22.getText();
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = createTeacher.get(0);
        
        sc.add(0, teacherName);
        sc.add(1, teacherID);
        sc.add(2, teacherPassword);
        
        method.accept(sc);
        
        jTextField20.setText("");
        jTextField21.setText("");
        jTextField22.setText("");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = lockSwaps.get(1);
        method.accept(sc);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = lockSwaps.get(0);
        method.accept(sc);
    }//GEN-LAST:event_jButton5ActionPerformed

    // method that fills fields where the list of user courses is required
    public void setCoursesList(ArrayList<String> userCourses) {
        
        String[] coursesList = new String[userCourses.size()];
        for (int i = 0; i < userCourses.size(); i++) {
            coursesList[i] = userCourses.get(i);
        }
        
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(coursesList));
    }
   
    //dados pessoais
    public void setUserData(String name, String id, String status) {
        jLabel1.setText(name);
        jLabel19.setText(id);
        jLabel20.setText(status);
    }
    
    public void showUserUCs(HashMap<String, ArrayList<String>> userInfo) { 
       
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        
        tableModel.setRowCount(0); // Remover todas as entradas da table
        String[] data = new String[4];
        
        for (Map.Entry<String, ArrayList<String>> entry: userInfo.entrySet()) {
            data[0] = entry.getKey();
            data[1] = entry.getValue().get(0);
            data[2] = entry.getValue().get(1);
            data[3] = entry.getValue().get(2); 
        
            tableModel.addRow(new Object[]{data[0], data[1], data[2], data[3]});
        }
            
        tableModel.fireTableDataChanged();
    }
    
    
    // metodo que mete a lista de turnos da UC que o user escolhe em cima (tab 2)
    public void setShiftsList(ArrayList<String> shiftsCourses) {
        
        String[] shiftsList = new String[shiftsCourses.size()];
        for (int i = 0; i < shiftsCourses.size(); i++) {
            shiftsList[i] = shiftsCourses.get(i);
        }
        
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(shiftsList));
    }
    
    public void myShifts(ArrayList<String> myShifts) {
        
        String[] shiftsList = new String[myShifts.size()];
        for (int i = 0; i < myShifts.size(); i++) {
            shiftsList[i] = myShifts.get(i);
        }
        
        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel(shiftsList));
        
    }
    
    // listening if RegistrationArea window is closed
    private void registrationOpened() {
        registrationDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                jButton1.setEnabled(true);
                
                registrationDialog.resetDialog();
            }
        });
    }   
    
    public RegistrationArea getRegistrationArea() {
        return registrationDialog;
    }
    
    public LoginArea getLoginArea() {
        return loginDialog;
    }
    
    public void showLoginError(String message) {
         JOptionPane.showMessageDialog(null, message);
    }
    
    public void showError1() {
         JOptionPane.showMessageDialog(null, "Já existe no sistema!");
    }
        
    public void showError() {
         JOptionPane.showMessageDialog(null, "Deve preencher todos os campos!");
    }
    
    public void showRegisterSuccess() {
         JOptionPane.showMessageDialog(null, "Registo Efetuado com Sucesso!");
         registrationDialog.dispose();
    }
    
    public void showSucessMessage() {
        JOptionPane.showMessageDialog(null, "Operação efetuada com sucesso");
    }
    
    public void LoginSuccess() {

        loginDialog.setVisible(false);
        jButton1.setEnabled(false);
        jButton4.setEnabled(true);
    }
    
    public void showPendingOffers(ArrayList<ArrayList<String>> pendingOffers) {
                
                jPanel4.removeAll();
                jPanel4.revalidate(); 
                jPanel4.repaint();
                
                jPanel4.setLayout(new GridLayout(20,0));
                jPanel4.setBackground(Color.white);

                for (ArrayList<String> list: pendingOffers) {
                    
                    String toBePrinted = "UC: " + list.get(0) + " do " + list.get(2) + " para o "+ list.get(3) + " do " + list.get(4);
                    JLabel lb = new JLabel(toBePrinted);
                    
                    lb.setHorizontalAlignment(JLabel.CENTER);
                    jPanel4.add(lb);
                    
                    if(list.get(6).equals("true")) {
                        
                        JButton b = new JButton("Aceitar oferta");
                        b.setPreferredSize(new Dimension(1, 1));
                        b.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                acceptOfferButtonClick(list.get(1));
                                jPanel4.remove(b);
                                jPanel4.remove(lb);
                            }
                        });
                        
                    jPanel4.add(b);
                    jPanel4.revalidate();
                    jPanel4.repaint();
                    
                    }
                    
                    jPanel4.revalidate();
                    jPanel4.repaint(); 
                    
                }   
                    jPanel4.revalidate();
                    jPanel4.repaint();
        }
    
        private void acceptOfferButtonClick(String acceptSwapID) {
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = acceptOffer.get(0);
        
        sc.add(0, acceptSwapID);
        
        method.accept(sc);
        
    }
    
    public void showActiveOffers(ArrayList<ArrayList<String>> activeOffers) {
        
        jPanel7.removeAll();
        jPanel7.revalidate(); 
        jPanel7.repaint();
        
        jPanel7.setLayout(new GridLayout(15, 2));
        jPanel7.setBackground(Color.white);
        
        for (ArrayList<String> list: activeOffers) {
            
                String toBePrinted = "UC: " + list.get(0) + " do " + list.get(1) + " para o "+ list.get(2);
                JLabel lb = new JLabel(toBePrinted);
                jPanel7.add(lb);
                
                JButton b = new JButton("Cancelar oferta");
                
                b.setPreferredSize(new Dimension(1, 1));
                
                b.addActionListener(new ActionListener() {
                    
                    public void actionPerformed(ActionEvent e) {
                        cancelOfferButtonClick(list.get(3));
                        jPanel7.remove(b);
                        jPanel7.remove(lb);
                        jPanel7.revalidate();
                        jPanel7.repaint();
                    }
                });
                
                jPanel7.add(b);
                jPanel7.revalidate();
                jPanel7.repaint();      
        }
    }
    
    private void cancelOfferButtonClick(String cancelSwapID) {
        
        ArrayList<String> sc = new ArrayList<String>(); 
        Consumer method = cancelOffer.get(0);
        
        sc.add(0, cancelSwapID);
        
        method.accept(sc);
        
    }
    
    public void showStudentOffersHistory(ArrayList<String> studentOffersHistory) {
        
        jPanel8.removeAll();
        jPanel8.revalidate(); 
        jPanel8.repaint();
        
        jPanel8.setLayout(new GridLayout(15, 2));
        jPanel8.setBackground(Color.white);
        for (String c: studentOffersHistory) {
            
            JLabel lb = new JLabel(c);
            jPanel8.add(lb);
            jPanel8.revalidate();
            jPanel8.repaint(); 
            
        }
        
        jPanel8.revalidate();
        jPanel8.repaint(); 
    }
    
    ///////////////////////////////////////////////////////////////////////////
    ///////////// ADMIN //////////////////////////////////////////////////////
    
    public void showTeachers(ArrayList<String> teachers) {
            
        String[] teachersList = new String[teachers.size()];
        for (int i = 0; i < teachers.size(); i++) {
            teachersList[i] = teachers.get(i);
        }
        
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(teachersList));
    }
    
    public void showCourses(ArrayList<String> courses) {
        
        String[] coursesList = new String[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            coursesList[i] = courses.get(i);
        }
        
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(coursesList));
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(coursesList));
        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel(coursesList));
        
    }
    
    public void showCourseStudents(ArrayList<String> students) {
        
        String[] studentsList = new String[students.size()];
        for (int i = 0; i < students.size(); i++) {
            studentsList[i] = students.get(i);
        }
        
        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel(studentsList));
        
    }
    
    // turnos da cadeira selecionada
    public void originShift(ArrayList<String> shifts) {
        
        String[] shiftsList = new String[shifts.size()];
        for (int i = 0; i < shifts.size(); i++) {
            shiftsList[i] = shifts.get(i);
        }
        
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(shiftsList));
        
    }
    
    public void destinationShift(ArrayList<String> shifts) {
        
        String[] shiftsList = new String[shifts.size()];
        for (int i = 0; i < shifts.size(); i++) {
            shiftsList[i] = shifts.get(i);
        }
        
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(shiftsList));
        
    }
    
    public void showShiftsofCourse(ArrayList<String> shifts) {
        
        String[] shiftsList = new String[shifts.size()];
        for (int i = 0; i < shifts.size(); i++) {
            shiftsList[i] = shifts.get(i);
        }
        
        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel(shiftsList));
        
    }
    
    public void showStudentToRemove(ArrayList<String> students) {
        
        String[] studentsList = new String[students.size()];
        for (int i = 0; i < students.size(); i++) {
            studentsList[i] = students.get(i);
        }
        
        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel(studentsList));
        
    }
   
    public static void start() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new View().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    // End of variables declaration//GEN-END:variables
}
