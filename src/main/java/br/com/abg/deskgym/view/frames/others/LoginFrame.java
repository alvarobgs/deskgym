package br.com.abg.deskgym.view.frames.others;


import br.com.abg.deskgym.entity.User;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.IncorrectPasswordException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import br.com.abg.deskgym.service.LoginService;
import br.com.abg.deskgym.service.SystemConfigurationService;
import br.com.abg.deskgym.service.UserService;
import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.utils.DateUtil;
import br.com.abg.deskgym.service.impl.LoginServiceImpl;
import br.com.abg.deskgym.service.impl.SystemConfigurationServiceImpl;
import br.com.abg.deskgym.service.impl.UserServiceImpl;
import br.com.abg.deskgym.session.Constant;
import br.com.abg.deskgym.utils.Validator;
import br.com.abg.deskgym.view.frames.AbstractFrame;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import java.util.Date;
import java.util.List;

public class LoginFrame extends AbstractFrame {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 1220093291631627207L;

	/**
     * Serviço de usuário
     */
    private transient UserService userService;
    
    /**
     * Serviço de login.
     */
    private transient LoginService loginService;
    
    /**
     * Serviço de configurações do sistema.
     */
    private transient SystemConfigurationService systemConfigurationService;

    /**
     * Construtor.
     */
    public LoginFrame() {
		initComponents();

		this.systemConfigurationService = new SystemConfigurationServiceImpl();
		this.loginService = new LoginServiceImpl();
		this.userService = new UserServiceImpl();
	
        this.setTitle("LOGIN");
        setLook(this);
        center(this);
        super.setIcon();
	
		try {
			this.systemConfigurationService.verifyFirstSystemAccess();
			this.loadUsers();
			this.setVisible(true);
			jPasswordField1.requestFocus();
		} catch (final ObjectExistsException | CustomMessageException | IOException | FieldInvalidException | ObjectNotFoundException e) {
			logger.save(e.getMessage(), e.getClass().toString(), Constant.LOGIN_FRAME, "Construtor");
            super.showErrorDialog(this, Messages.SYSTEM_START_ERROR + " " + Messages.CONTACT_ADMIN);
            System.exit(1);
		}
    }
    
    /**
     * Carrega o nome dos usuários dos funcionários ativos na combo para login.
     */
    private void loadUsers(){
		final List<User> users = this.userService.listActiveEmployeeUsers();

		for (final User user : users) {
			jComboBox1.addItem(user.getUserName());
		}
    }
    
    /**
     * Realiza o login.
     */
    private void logIn() {
		try {
			if(validateLastLogin()) {
				final String selectedUserName = (String) jComboBox1.getSelectedItem();
				final String typedPassword = new String(jPasswordField1.getPassword());

				if (Validator.isEmpty(selectedUserName)) {
					throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "nome de usuário");
				}
				if (Validator.isEmpty(typedPassword)) {
					throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY_FEMALE, "senha");
				}
				this.loginService.logIn(selectedUserName, typedPassword);

				new MainFrame();
				this.dispose();
			}
		} catch (final NoSuchAlgorithmException | UnsupportedEncodingException | CustomMessageException | ObjectBeingEditedException e) {
			logger.save(e.getMessage(), e.getClass().toString(), Constant.LOGIN_FRAME, "logIn");
		} catch (final IncorrectPasswordException | FieldInvalidException e) {
            super.showWarningDialog(this, e.getMessage());
		}
    }
    
    /**
     * Valida se o relógio do computador está correto.
     *
     * @return
     */
    private boolean validateLastLogin() {
        if(!systemConfigurationService.validateLastLogin()) {
			final String today = DateUtil.convertDateToString(new Date(), true, true);

            if (!super.showConfirmDialog(this, Messages.LOGIN_DATE_CONFIRM + today + "?")) {
                super.showWarningDialog(this, Messages.LOGIN_DATE_CONFIRM_ERROR);
				return false;
	    	}
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/key_48x48.png"))); // NOI18N
        jLabel1.setText(" LOGIN");

        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPasswordField1KeyPressed(evt);
            }
        });

        jLabel2.setText("USUÁRIO");

        jLabel3.setText("SENHA");

        jLabel4.setText("Digite a senha e aperte ENTER");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
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
     * Evento de apertar uma tecla no campo de senha.
     *
     * @param evt
     */
    private void jPasswordField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField1KeyPressed
        if (super.isEnterPressed(evt)) {
	    	this.logIn();
		}
    }//GEN-LAST:event_jPasswordField1KeyPressed

    /**
     * Main
     *
     * @param args
     */
    public static void main(String args[]) {
		//Thread.setDefaultUncaughtExceptionHandler(new RuntimeUncaughtExceptionHandler());
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    // End of variables declaration//GEN-END:variables
}
