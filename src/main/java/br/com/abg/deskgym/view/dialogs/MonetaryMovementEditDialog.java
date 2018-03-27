/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.abg.deskgym.view.dialogs;

import java.awt.*;

import javax.swing.*;

import br.com.abg.deskgym.entity.MonetaryMovement;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.service.MonetaryMovementService;
import br.com.abg.deskgym.service.impl.MonetaryMovementServiceImpl;
import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.utils.Converter;
import br.com.abg.deskgym.utils.Logger;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Alvaro
 */
public class MonetaryMovementEditDialog extends javax.swing.JDialog {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 6198753023886633396L;

	/**
	 * Nome do dialog.
	 */
	private static final String MONETARY_MOVEMENT_EDIT_DIALOG = "MonetaryMovementEditDialog";

	/**
	 * Logger.
	 */
	private static transient Logger logger;

	/**
     * Serviço de movimentação financeira
     */
    private MonetaryMovementService monetaryMovementService;
    
    /**
     * Movimentação financeira sendo editada.
     */
    @Getter
    @Setter
    private MonetaryMovement monetaryMovement;
    
    /**
     * Valor em string
     */
    private String valueString;

    /**
     * Construtor padrão
     */
    public MonetaryMovementEditDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.init();
    }
    
    /**
     * Construtor sobrescrito
     */
    public MonetaryMovementEditDialog(java.awt.Frame parent, boolean modal, MonetaryMovement monetaryMovement) {
        super(parent, modal);
        this.monetaryMovement = monetaryMovement;
        this.setDataOnFields();
        this.init();
    }

	/**
	 * Inicia o dialog
	 */
	private void init() {
		this.monetaryMovementService = new MonetaryMovementServiceImpl();
		this.logger = new Logger();
		initComponents();
		this.center(this);
		this.setVisible(true);
	}
    
    /**
     * Inicializa os valores nos campos.
     */
    private void setDataOnFields() {
    	this.valueString = this.monetaryMovement.getValue().toString();
//    	jDateChooser1.setDate(this.monetaryMovement.getDate());
    }
    
    /**
     * Salvar edição.
     */
    private void save() {
		try {
			int confirm = JOptionPane.showConfirmDialog(null, Messages.CONFIRM_SAVE_CHANGES, Messages.WARNING, JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				this.monetaryMovement.setValue(Converter.convertMoneyString(this.valueString));
				this.monetaryMovementService.update(this.monetaryMovement);
			}
		} catch (final FieldInvalidException | CustomMessageException | NumberFormatException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), Messages.WARNING, JOptionPane.WARNING_MESSAGE);
			logger.save(e.getMessage(), e.getClass().toString(), MONETARY_MOVEMENT_EDIT_DIALOG, "save");
		} catch (final ObjectBeingEditedException e) {
			JOptionPane.showMessageDialog(this, "Ganho/Gasto " + Messages.BEING_EDITED, Messages.ERROR, JOptionPane.ERROR_MESSAGE);
			logger.save(e.getMessage(), e.getClass().toString(), MONETARY_MOVEMENT_EDIT_DIALOG, "save");
		}
    }
    
    /**
     * Cancela a edição.
     */
    private void cancel() {
        this.monetaryMovement = null;
//        this.dispose();
		//FIXME verificar maneira correta de fechar o dialog para retornar ao principal
    }

	/**
	 * Centraliza o dialog.
	 */
	private void center(final JDialog dialog) {
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation(
				(screenSize.width - dialog.getWidth()) / 2,
				(screenSize.height - dialog.getHeight()) / 2
		);
	}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calculator_48x48.png"))); // NOI18N
        jLabel1.setText("EDITAR MOVIMENTAÇÃO");

        jLabel2.setText("NOME");

        jLabel3.setText("VALOR");

        jLabel4.setText("DURAÇÃO");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn_small/save.png"))); // NOI18N
        jButton1.setText("SALVAR");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn_small/error.png"))); // NOI18N
        jButton2.setText("CANCELAR");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jLabel5.setText("DESCRIÇÃO");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jFormattedTextField1)
                            .addComponent(jTextField1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Ação do botão salvar.
     * 
     * @param evt 
     */
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        this.save();
    }//GEN-LAST:event_jButton1MouseClicked

    /**
     * Ação do botão cancelar.
     * 
     * @param evt 
     */
    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        this.cancel();
    }//GEN-LAST:event_jButton2MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MonetaryMovementEditDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MonetaryMovementEditDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MonetaryMovementEditDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MonetaryMovementEditDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MonetaryMovementEditDialog dialog = new MonetaryMovementEditDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}