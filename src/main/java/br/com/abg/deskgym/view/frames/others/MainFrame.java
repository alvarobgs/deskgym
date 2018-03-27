package br.com.abg.deskgym.view.frames.others;


import br.com.abg.deskgym.dto.PaymentNearEndDTO;
import br.com.abg.deskgym.entity.*;
import br.com.abg.deskgym.enums.Gender;
import br.com.abg.deskgym.enums.Operation;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import br.com.abg.deskgym.service.ClassPaymentService;
import br.com.abg.deskgym.service.EmployeePaymentService;
import br.com.abg.deskgym.service.EmployeeService;
import br.com.abg.deskgym.service.ExperimentalClassScheduleService;
import br.com.abg.deskgym.service.KeyService;
import br.com.abg.deskgym.service.StudentStatusChangeService;
import br.com.abg.deskgym.service.impl.StudentStatusChangeServiceImpl;
import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.service.StudentService;
import br.com.abg.deskgym.service.SystemConfigurationService;
import br.com.abg.deskgym.service.impl.ClassPaymentServiceImpl;
import br.com.abg.deskgym.service.impl.EmployeePaymentServiceImpl;
import br.com.abg.deskgym.service.impl.EmployeeServiceImpl;
import br.com.abg.deskgym.service.impl.ExperimentalClassScheduleServiceImpl;
import br.com.abg.deskgym.service.impl.KeyServiceImpl;
import br.com.abg.deskgym.service.impl.StudentServiceImpl;
import br.com.abg.deskgym.service.impl.SystemConfigurationServiceImpl;
import br.com.abg.deskgym.session.Constant;
import br.com.abg.deskgym.session.LoggedUser;
import br.com.abg.deskgym.utils.DateUtil;
import br.com.abg.deskgym.utils.Validator;
import br.com.abg.deskgym.view.frames.AbstractFrame;
import br.com.abg.deskgym.view.frames.employee.EditEmployeeFrame;
import br.com.abg.deskgym.view.frames.employee.EmployeePaymentFrame;
import br.com.abg.deskgym.view.frames.employee.NewEmployeeFrame;
import br.com.abg.deskgym.view.frames.management.MonetaryMovementFrame;
import br.com.abg.deskgym.view.frames.management.PlanManagementFrame;
import br.com.abg.deskgym.view.frames.management.SystemConfigurationFrame;
import br.com.abg.deskgym.view.frames.student.ClassPaymentFrame;
import br.com.abg.deskgym.view.frames.student.EditStudentFrame;
import br.com.abg.deskgym.view.frames.student.NewStudentFrame;
import br.com.abg.deskgym.view.models.BirthDayListModel;
import br.com.abg.deskgym.view.models.ScheduleClassTableModel;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

/**
 * Frame principal.
 */
public class MainFrame extends AbstractFrame {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = -7436090549721331173L;

	/**
     * Serviço de funcionário.
     */
    private transient EmployeeService employeeService;
    
    /**
     * Serviço de aluno.
     */
    private transient StudentService studentService;

	/**
	 * Serviço de mudança de status de aluno.
	 */
	private transient StudentStatusChangeService studentStatusChangeService;
    
    /**
     * Serviço de mensalidade.
     */
    private transient ClassPaymentService classPaymentService;
    
    /**
     * Serviço de agendamento de aula experimental.
     */
    private transient ExperimentalClassScheduleService experimentalClassScheduleService;
    
    /**
     * Serviço de configurações do sistema.
     */
    private transient SystemConfigurationService systemConfigurationService;
    
    /**
     * Serviço de chaves.
     */
    private transient KeyService keyService;
 
    /**
     * Funcionário logado.
     */
    @Getter
    private Employee employee;

	/**
	 * Aula experimental.
	 */
	@Getter
	@Setter
	private ExperimentalClassSchedule experimentalClassSchedule;
    
    /**
     * Texto do painel livre.
     */
    @Getter
    @Setter
    private String freePanelText;

	/**
	 * Texto do painel livre do funcionário.
	 */
	@Getter
	@Setter
	private String employeePanelText;

	/**
	 * Modelo de tabela de agendamento de aula experimental.
	 */
	private ScheduleClassTableModel scheduleTableModel;

	/**
	 * Modelo da listagem de aniversariantes.
	 */
	private BirthDayListModel bdayListModel;
     
    /**
     * Construtor.
     */
    public MainFrame(){
		this.employee = new LoggedUser().getInstancedEmployee();
		this.scheduleTableModel = new ScheduleClassTableModel();
		this.bdayListModel = new BirthDayListModel();
	    this.experimentalClassSchedule = new ExperimentalClassSchedule();
        initComponents();

        jTable1.setModel(this.scheduleTableModel);
		/*jTable2.setModel(this.scheduleTableModel);
		jTable3.setModel(this.scheduleTableModel);
		jTable4.setModel(this.scheduleTableModel);
		jTable5.setModel(this.scheduleTableModel);
		jTable6.setModel(this.scheduleTableModel);
		jTable7.setModel(this.scheduleTableModel);*/

		this.systemConfigurationService = new SystemConfigurationServiceImpl();
		this.employeeService = new EmployeeServiceImpl();
		this.keyService = new KeyServiceImpl();
		this.studentService = new StudentServiceImpl();
		this.classPaymentService = new ClassPaymentServiceImpl();
		this.experimentalClassScheduleService = new ExperimentalClassScheduleServiceImpl();
		this.studentStatusChangeService = new StudentStatusChangeServiceImpl();

		jDateChooser1.setDate(new Date());

		this.setTitle("Deskgym - versão 1.0 (Logado como : " + this.employee.getName() + ")");
        setLook(this);
        center(this);
        super.setIcon();

		if (!this.systemConfigurationService.alreadyLoggedInToday()) {
			this.backupDataBase();
			this.verifyClassPayments();
			this.updateStudentsStatus();
		}

		this.loadStoredKeys();
		this.loadPanelText();
		this.loadScheduleClasses(new Date());
		this.enableFrames();
		this.verifyActiveStudentsBirthday();
		this.loadStudentsNotActives();

		jMenuBar1.setEnabled(true);

		this.setVisible(true);
    }
    
    /**
     * Carrega as chaves.
     */
    private void loadStoredKeys() {
		final List<Key> keys = this.keyService.listByGender(Gender.UNDEFINED);
        
        for (final Key key : keys) {
            switch (key.getNumber()) {
                case 1:
                    keyOneTextField.setText(key.getStudentName());
                    break;
                case 2:
                    keyTwoTextField.setText(key.getStudentName());
                    break;
                case 3:
                    keyThreeTextField.setText(key.getStudentName());
                    break;
                case 4:
                    keyFourTextField.setText(key.getStudentName());
                	break;
                case 5:
                    keyFiveTextField.setText(key.getStudentName());
                    break;
                case 6:
                    keySixTextField.setText(key.getStudentName());
                    break;
                case 7:
                    keySevenTextField.setText(key.getStudentName());
                    break;
                case 8:
                    keyEightTextField.setText(key.getStudentName());
                    break;
                case 9:
                    keyNineTextField.setText(key.getStudentName());
                    break;
                case 10:
                    keyTenTextField.setText(key.getStudentName());
                    break;
                case 11:
                    keyElevenTextField.setText(key.getStudentName());
                    break;
                case 12:
                    keyTwelveTextField.setText(key.getStudentName());
                    break;
                case 13:
                    keyThirteenTextField.setText(key.getStudentName());
                    break;
                case 14:
                    keyFourteenTextField.setText(key.getStudentName());
                    break;
                case 15:
                    keyFifteenTextField.setText(key.getStudentName());
                    break;
                case 16:
                    keySixteenTextField.setText(key.getStudentName());
                    break;
                case 17:
                    keySeventeenTextField.setText(key.getStudentName());
                    break;
                case 18:
                    keyEighteenTextField.setText(key.getStudentName());
                    break;
                case 19:
                    keyNineteenTextField.setText(key.getStudentName());
                    break;
                case 20:
                    keyTwentyTextField.setText(key.getStudentName());
                    break; 
            }
        }
    }
    
    /**
     * Carrega o texto do painel livre.
     */
    private void loadPanelText() {
		try {
			this.freePanelText = this.systemConfigurationService.getFreePanelText();
			final EmployeePanelText employeePanelText = this.employeeService.getEmployeePanelText(this.employee.getId());
			if (!Validator.isEmpty(employeePanelText)) {
                this.employeePanelText = employeePanelText.getText();
            }
		} catch (final FieldInvalidException e) {
			logger.save(e.getMessage(), e.getClass().toString(), Constant.MAIN_FRAME, "loadPanelText");
		}

    }
    
    /**
     * Carrega as as aulas agendadas.
     */
    private void loadScheduleClasses(final Date date) {
		final List<ExperimentalClassSchedule> classes = this.experimentalClassScheduleService.getScheduleClassesByDate(date);
        if (!Validator.isEmpty(classes)) {
			classes.sort(ExperimentalClassSchedule.timeCompare);
			this.scheduleTableModel.refresh(classes);
        } else {
//            jTextArea6.setText(Messages.NOTHING_FOUND);
        }
    }
    
    /**
     * Realiza o backup do banco de dados.
     */
    private void backupDataBase() {
    	try {
			this.systemConfigurationService.backupDataBase();
		} catch (final IOException | InterruptedException e) {
			super.showErrorDialog(this, Messages.BACKUP_ERROR);
			logger.save(e.getMessage(), e.getClass().toString(), Constant.MAIN_FRAME, "backupDataBase");
		}
	}
    
    /**
     * Verifica as mensalidades que venceram hoje
     */
    private void verifyClassPayments() {
		final List<PaymentNearEndDTO> paymentsNearEnd = this.classPaymentService.verifyClassPayments();
//		this.modelPaymentsNearEnd.refresh(paymentsNearEnd);
    }
    
    /**
     * Atualiza o status dos alunos.
     */
    private void updateStudentsStatus() {
		try {
			this.studentService.checkAndUpdateStudentStatus();
		} catch (final FieldInvalidException | CustomMessageException e) {
			super.showWarningDialog(this, e.getMessage());
			logger.save(e.getMessage(), e.getClass().toString(), Constant.MAIN_FRAME, "updateStudentsStatus");
		} catch (final ObjectBeingEditedException e) {
			super.showErrorDialog(this, "Aluno " + Messages.BEING_EDITED);
			logger.save(e.getMessage(), e.getClass().toString(), Constant.MAIN_FRAME, "updateStudentsStatus");
		} catch (final ObjectNotFoundException e) {
			super.showErrorDialog(this, "Aluno " + Messages.NOT_FOUND);
			logger.save(e.getMessage(), e.getClass().toString(), Constant.MAIN_FRAME, "updateStudentsStatus");
		} catch (final IOException e) {
			super.showErrorDialog(this, Messages.UNKNOWN_ERROR);
			logger.save(e.getMessage(), e.getClass().toString(), Constant.MAIN_FRAME, "updateStudentsStatus");
		} catch (final ObjectExistsException e) {
			super.showErrorDialog(this, "" + Messages.ALREADY_EXISTS);
			logger.save(e.getMessage(), e.getClass().toString(), Constant.MAIN_FRAME, "updateStudentsStatus");
		}
	}

	/**
	 * Carrega os alunos que foram inativados ou marcados como inadimplentes ou pendentes hoje.
	 */
	private void loadStudentsNotActives() {
		final List<StudentStatusChange> status = this.studentStatusChangeService.listNotActivesByDate(new Date());
//		this.inactiveModel.refresh(status);
    }
    
    /**
     * Verifica os aniversariantes do mês.
     */
    private void verifyActiveStudentsBirthday() {
        final List<Student> birthdayStudents = this.studentService.listActiveStudentsByBirthday();
        if (!Validator.isEmpty(birthdayStudents)) {
	    birthdayStudents.sort(Student.birthDayComparator);
	    this.bdayListModel.refresh(birthdayStudents);
        }
    }
    
    /**
     * Habilita os frames de acordo com as permissões do usuário logado.
     */
    private void enableFrames() {
        jMenuItem1.setEnabled(true);
        jMenuItem2.setEnabled(true);
        jMenuItem3.setEnabled(true);
        jMenuItem4.setEnabled(true);
        jMenuItem5.setEnabled(true);
        jMenuItem7.setEnabled(true);
        jMenuItem8.setEnabled(true);
        jMenuItem9.setEnabled(true);
        jMenuItem10.setEnabled(true);
        jMenuItem11.setEnabled(true);
        jMenuItem12.setEnabled(true);
        jMenuItem13.setEnabled(true);
        jMenuItem14.setEnabled(true);
        jMenuItem15.setEnabled(true);

	final List<Permission> permissions = this.employee.getUser().getPermissions().stream().filter(p -> p.getOperation().equals(Operation.ACCESS)).collect(Collectors.toList());
	
	for (final Permission permission : permissions) {
	    switch (permission.getPath()) {
			case Constant.NEW_STUDENT_FRAME:
				jMenuItem1.setEnabled(true);
				break;

			case Constant.EDIT_STUDENT_FRAME:
				jMenuItem2.setEnabled(true);
				break;

			case Constant.CLASS_PAYMENT_FRAME:
				jMenuItem3.setEnabled(true);
				break;

			case Constant.NEW_EMPLOYEE_FRAME:
				jMenuItem4.setEnabled(true);
				break;

			case Constant.EDIT_EMPLOYEE_FRAME:
				jMenuItem5.setEnabled(true);
				break;

			case Constant.EMPLOYEE_PAYMENT_FRAME:
				jMenuItem17.setEnabled(true);
				break;

			case Constant.SHOPPING_SALES_FRAME:
				jMenuItem8.setEnabled(true);
				break;

			case Constant.SHOPPING_MANAGEMENT_FRAME:
				jMenuItem7.setEnabled(true);
				break;

			case Constant.MONETARY_MOVEMENT_FRAME:
				jMenuItem9.setEnabled(true);
				break;

			case Constant.PLAN_MANAGEMENT_FRAME:
				jMenuItem10.setEnabled(true);
				break;

			case Constant.PERIOD_BALANCE_FRAME:
				jMenuItem11.setEnabled(true);
				break;

			case Constant.STUDENTS_STATISTICTS_FRAME:
				jMenuItem12.setEnabled(true);
				break;

			case Constant.ANUAL_BALANCE_FRAME:
				jMenuItem13.setEnabled(true);
				break;

			case Constant.SYSTEM_LOG_FRAME:
				jMenuItem15.setEnabled(true);
				break;

			case Constant.SYSTEM_CONFIGURATION_FRAME:
				jMenuItem18.setEnabled(true);
				break;
			}
		}
    }
    
    /**
     * Salva uma chave no banco.
     * 
     * @param studentName nome do aluno.
     * @param index índice da chave.
     * @param gender sexo.
     */
    private void saveKeyEntry(final String studentName, final int index, final Gender gender) {
        try {
            if (!Validator.isEmpty(studentName)) {
                final Key key = new Key(studentName, index, gender);
                this.keyService.saveOrUpdateKey(key);
            }
        } catch (final CustomMessageException e) {
			super.showWarningDialog(this, e.getMessage());
			logger.save(e.getMessage(), e.getClass().toString(), Constant.MAIN_FRAME, "saveKeyEntry");
        } catch (final ObjectExistsException e) {
			super.showErrorDialog(this, "Chave " + Messages.ALREADY_EXISTS);
			logger.save(e.getMessage(), e.getClass().toString(), Constant.MAIN_FRAME, "saveKeyEntry");
        } catch (final ObjectBeingEditedException e) {
			super.showErrorDialog(this, "Chave " + Messages.BEING_EDITED);
			logger.save(e.getMessage(), e.getClass().toString(), Constant.MAIN_FRAME, "saveKeyEntry");
        }
    }
    
    /**
     * Salva o texto do painel livre.
     */
    private void saveFreePanelText() {
		try {
			this.systemConfigurationService.updateFreePanelText(jTextArea1.getText().trim());
			super.showSuccessDialog(this, Messages.EDITED_SUCCESSFULLY);
		} catch (final CustomMessageException e) {
			super.showWarningDialog(this, e.getMessage());
			logger.save(e.getMessage(), e.getClass().toString(), Constant.MAIN_FRAME, "saveFreePanelText");
		} catch (final ObjectBeingEditedException e) {
			super.showErrorDialog(this, "Texto do painel livre " + Messages.BEING_EDITED);
			logger.save(e.getMessage(), e.getClass().toString(), Constant.MAIN_FRAME, "saveFreePanelText");
		}
	}
    
    /**
     * Salva o texto do painel exclusivo do funcionário.
     */
    private void saveEmployeePanelText() {
    	try {
			if (!Validator.isEmpty(new LoggedUser().getEmployee())) {
				this.employeeService.updateEmployeePanelText(new LoggedUser().getEmployee().getId(), this.employeePanelText);
				super.showSuccessDialog(this, Messages.EDITED_SUCCESSFULLY);
			}
		} catch (final FieldInvalidException e) {
			super.showWarningDialog(this, e.getMessage());
			logger.save(e.getMessage(), e.getClass().toString(), Constant.MAIN_FRAME, "saveFreePanelText");
		} catch (final ObjectBeingEditedException e) {
			super.showErrorDialog(this, "Texto do painel do funcionário " + Messages.BEING_EDITED);
			logger.save(e.getMessage(), e.getClass().toString(), Constant.MAIN_FRAME, "saveFreePanelText");
		}

	}
    
    /**
     * Adiciona novo registro da agenda.
     */
    private void createNewScheduleClass() {
        try {
        	this.experimentalClassSchedule.setDate(DateUtil.setTimeToDate(jDateChooser2.getDate(), ""));
            this.experimentalClassScheduleService.save(this.experimentalClassSchedule);
			this.experimentalClassSchedule = new ExperimentalClassSchedule();
			super.showSuccessDialog(this, Messages.SAVE_SUCCESSFULLY);
			if (jDateChooser1.getDate().equals(jDateChooser2.getDate())) {
				this.loadScheduleClasses(jDateChooser1.getDate());
			}
        } catch (final ObjectExistsException e) {
			super.showErrorDialog(this, "Aula experimental " + Messages.ALREADY_EXISTS);
			logger.save(e.getMessage(), e.getClass().toString(), Constant.MAIN_FRAME, "createNewScheduleClass");
        } catch (final CustomMessageException e) {
			super.showWarningDialog(this, e.getMessage());
			logger.save(e.getMessage(), e.getClass().toString(), Constant.MAIN_FRAME, "createNewScheduleClass");
		}
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jMenuItem14 = new javax.swing.JMenuItem();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel17 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        keyOneTextField = new javax.swing.JTextField();
        keyTwoTextField = new javax.swing.JTextField();
        keyThreeTextField = new javax.swing.JTextField();
        keyFourTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        keyFiveTextField = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        keyNineTextField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        keyTenTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        keySixTextField = new javax.swing.JTextField();
        keySevenTextField = new javax.swing.JTextField();
        keyEightTextField = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        keyFourteenTextField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        keyElevenTextField = new javax.swing.JTextField();
        keyTwelveTextField = new javax.swing.JTextField();
        keyThirteenTextField = new javax.swing.JTextField();
        keyFifteenTextField = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        keyTwentyTextField = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        keyNineteenTextField = new javax.swing.JTextField();
        keySeventeenTextField = new javax.swing.JTextField();
        keySixteenTextField = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        keyEighteenTextField = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();

        jMenuItem14.setForeground(new java.awt.Color(255, 51, 51));
        jMenuItem14.setText("jMenuItem14");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Alunos com mensalidade atrasada");

        jScrollPane6.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("MENS. ATRASADAS", jPanel6);

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Alunos com mensalidade próximas do vencimento");

        jScrollPane7.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("MENS. PROX. VENC.", jPanel7);

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Alunos com alteração de status hoje");

        jScrollPane5.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("ALUNOS INAT.", jPanel8);

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel4.setText("Aulas marcadas para o dia");

        jScrollPane4.setViewportView(jTable1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("NOME");

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("CELULAR");

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("HORÁRIO");

        jLabel27.setText("DATA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel27)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel25)
                .addGap(18, 18, 18)
                .addComponent(jLabel27)
                .addGap(28, 28, 28)
                .addComponent(jLabel26)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn_small/save.png"))); // NOI18N
        jButton3.setText("SALVAR");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(jTextField2)
                    .addComponent(jTextField3)
                    .addComponent(jTextField1)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(249, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jButton3))
        );

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn_small/garbage.png"))); // NOI18N
        jButton4.setText("REMOVER SELECIONADO");

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Agendamento de aula experimental");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("AULA EXP.", jPanel9);

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Aniversariantes do mês");

        jScrollPane8.setViewportView(jList1);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane8)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE))
                .addGap(8, 8, 8))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("ANIVER.", jPanel16);

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Encomendas a receber");

        jScrollPane3.setViewportView(jTable5);

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Pagamentos a receber");

        jScrollPane9.setViewportView(jTable6);

        jLabel36.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Produtos à entregar");

        jScrollPane10.setViewportView(jTable7);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("LOJA", jPanel17);

        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${freePanelText}"), jTextArea1, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn_small/save.png"))); // NOI18N
        jButton1.setText("SALVAR");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        jTabbedPane2.addTab("PAINEL LIVRE DE RECADOS", jPanel10);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${employeePanelText}"), jTextArea2, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(jTextArea2);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn_small/save.png"))); // NOI18N
        jButton2.setText("SALVAR");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        jTabbedPane2.addTab("PAINEL EXCLUSIVO DO FUNCIONÁRIO LOGADO", jPanel1);

        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel1.setText("01");

        jLabel5.setText("02");

        jLabel6.setText("03");

        jLabel7.setText("04");

        keyOneTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyOneTextFieldKeyPressed(evt);
            }
        });

        keyTwoTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyTwoTextFieldKeyPressed(evt);
            }
        });

        keyThreeTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyThreeTextFieldKeyPressed(evt);
            }
        });

        keyFourTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyFourTextFieldKeyPressed(evt);
            }
        });

        jLabel8.setText("05");

        keyFiveTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyFiveTextFieldKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyFourTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyFiveTextField))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyOneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyTwoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyThreeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(keyOneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(keyTwoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keyThreeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keyFourTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keyFiveTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        keyNineTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyNineTextFieldKeyPressed(evt);
            }
        });

        jLabel13.setText("10");

        keyTenTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyTenTextFieldKeyPressed(evt);
            }
        });

        jLabel9.setText("06");

        jLabel10.setText("07");

        jLabel11.setText("08");

        jLabel12.setText("09");

        keySixTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keySixTextFieldKeyPressed(evt);
            }
        });

        keySevenTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keySevenTextFieldKeyPressed(evt);
            }
        });

        keyEightTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyEightTextFieldKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyNineTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyTenTextField))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keySixTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keySevenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyEightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(keySixTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(keySevenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keyEightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keyNineTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keyTenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        keyFourteenTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyFourteenTextFieldKeyPressed(evt);
            }
        });

        jLabel14.setText("11");

        jLabel15.setText("12");

        jLabel16.setText("13");

        jLabel17.setText("14");

        keyElevenTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyElevenTextFieldKeyPressed(evt);
            }
        });

        keyTwelveTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyTwelveTextFieldKeyPressed(evt);
            }
        });

        keyThirteenTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyThirteenTextFieldKeyPressed(evt);
            }
        });

        keyFifteenTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyFifteenTextFieldKeyPressed(evt);
            }
        });

        jLabel18.setText("15");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyFourteenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyFifteenTextField))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyElevenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyTwelveTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyThirteenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(keyElevenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(keyTwelveTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keyThirteenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keyFourteenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keyFifteenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        keyTwentyTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyTwentyTextFieldKeyPressed(evt);
            }
        });

        jLabel24.setText("20");

        keyNineteenTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyNineteenTextFieldKeyPressed(evt);
            }
        });

        keySeventeenTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keySeventeenTextFieldKeyPressed(evt);
            }
        });

        keySixteenTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keySixteenTextFieldKeyPressed(evt);
            }
        });

        jLabel23.setText("19");

        jLabel22.setText("18");

        jLabel20.setText("17");

        jLabel19.setText("16");

        keyEighteenTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyEighteenTextFieldKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyNineteenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyTwentyTextField))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keySixteenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keySeventeenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyEighteenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(keySixteenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(keySeventeenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keyEighteenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keyNineteenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keyTwentyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("MAPA DE CHAVES", jPanel11);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane3)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTabbedPane2)
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTabbedPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jMenuBar1.setEnabled(false);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/profile_24x24.png"))); // NOI18N
        jMenu1.setText("Alunos");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new-student_48x48.png"))); // NOI18N
        jMenuItem1.setText("Novo...");
        jMenuItem1.setEnabled(false);
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem1MousePressed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search_48x48.png"))); // NOI18N
        jMenuItem2.setText("Pesquisar/Editar");
        jMenuItem2.setEnabled(false);
        jMenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem2MousePressed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/plain_48x48.png"))); // NOI18N
        jMenuItem3.setText("Pagamento de Mensalidade");
        jMenuItem3.setEnabled(false);
        jMenuItem3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem3MousePressed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/screen_24x24.png"))); // NOI18N
        jMenu2.setText("Funcionários");

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/employee_48x48.png"))); // NOI18N
        jMenuItem4.setText("Novo...");
        jMenuItem4.setEnabled(false);
        jMenuItem4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem4MousePressed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search-blue_48x48.png"))); // NOI18N
        jMenuItem5.setText("Pesquisar/Editar");
        jMenuItem5.setEnabled(false);
        jMenuItem5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem5MousePressed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/money-coin_48x48.png"))); // NOI18N
        jMenuItem17.setText("Pagamento de Funcionário");
        jMenuItem17.setEnabled(false);
        jMenuItem17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem17MousePressed(evt);
            }
        });
        jMenu2.add(jMenuItem17);

        jMenuBar1.add(jMenu2);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/picture_24x24.png"))); // NOI18N
        jMenu5.setText("Loja");

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/notepad_48x48.png"))); // NOI18N
        jMenuItem8.setText("Registrar Venda");
        jMenuItem8.setEnabled(false);
        jMenuItem8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem8MousePressed(evt);
            }
        });
        jMenu5.add(jMenuItem8);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/package_48x48.png"))); // NOI18N
        jMenuItem7.setText("Gerenciar Estoque");
        jMenuItem7.setEnabled(false);
        jMenuItem7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem7MousePressed(evt);
            }
        });
        jMenu5.add(jMenuItem7);

        jMenuBar1.add(jMenu5);

        jMenu8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bar-chart_24x24.png"))); // NOI18N
        jMenu8.setText("Despesas/Ganhos");

        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calculator_48x48.png"))); // NOI18N
        jMenuItem9.setText("Despesas e Receitas");
        jMenuItem9.setEnabled(false);
        jMenuItem9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem9MousePressed(evt);
            }
        });
        jMenu8.add(jMenuItem9);

        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/plans_48x48.png"))); // NOI18N
        jMenuItem10.setText("Gerenciar Planos");
        jMenuItem10.setEnabled(false);
        jMenuItem10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem10MousePressed(evt);
            }
        });
        jMenu8.add(jMenuItem10);

        jMenuBar1.add(jMenu8);

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/line-chart_24x24.png"))); // NOI18N
        jMenu6.setText("Relatórios");

        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/graph_48x48.png"))); // NOI18N
        jMenuItem11.setText("Balanço por Período");
        jMenuItem11.setEnabled(false);
        jMenuItem11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem11MousePressed(evt);
            }
        });
        jMenu6.add(jMenuItem11);

        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/percentage_48x48.png"))); // NOI18N
        jMenuItem12.setText("Estatísticas de Alunos");
        jMenuItem12.setEnabled(false);
        jMenuItem12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem12MousePressed(evt);
            }
        });
        jMenu6.add(jMenuItem12);

        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/square_48x48.png"))); // NOI18N
        jMenuItem13.setText("Balanço Anual");
        jMenuItem13.setEnabled(false);
        jMenuItem13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem13MousePressed(evt);
            }
        });
        jMenu6.add(jMenuItem13);

        jMenuItem15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/newspapper_48x48.png"))); // NOI18N
        jMenuItem15.setText("Logs do Sistema");
        jMenuItem15.setEnabled(false);
        jMenuItem15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem15MousePressed(evt);
            }
        });
        jMenu6.add(jMenuItem15);

        jMenuBar1.add(jMenu6);

        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/comment_24x24.png"))); // NOI18N
        jMenu7.setText("Outros");

        jMenuItem18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/configuration_48x48.png"))); // NOI18N
        jMenuItem18.setText("Configurações");
        jMenuItem18.setEnabled(false);
        jMenuItem18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem18MousePressed(evt);
            }
        });
        jMenu7.add(jMenuItem18);

        jMenuItem19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/infomartion_48x48.png"))); // NOI18N
        jMenuItem19.setText("Informações");
        jMenuItem19.setEnabled(false);
        jMenuItem19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem19MousePressed(evt);
            }
        });
        jMenu7.add(jMenuItem19);

        jMenuBar1.add(jMenu7);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Botão para abrir cadastro de aluno.
     */
    private void jMenuItem1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MousePressed
       if (jMenuItem1.isEnabled()) {
            new NewStudentFrame();
       }
    }//GEN-LAST:event_jMenuItem1MousePressed
    
    /**
     * Botão para abrir a pesquisa e edição de aluno.
     */
    private void jMenuItem2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem2MousePressed
        if (jMenuItem2.isEnabled()) {
	    	new EditStudentFrame();
       }   
    }//GEN-LAST:event_jMenuItem2MousePressed
    
    /**
     * Botão para abrir o pagamento de mensalidade.
     */
    private void jMenuItem3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem3MousePressed
       if (jMenuItem3.isEnabled()) {
       		new ClassPaymentFrame();
       }
    }//GEN-LAST:event_jMenuItem3MousePressed
    
    /**
     * Cadastro de gastos e ganhos.
     */
    private void jMenuItem9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem9MousePressed
       if (jMenuItem9.isEnabled()) {
	   new MonetaryMovementFrame();
       }
    }//GEN-LAST:event_jMenuItem9MousePressed
    
    /**
     * Botão para abrir o frame de gerenciamento de planos.
     * @param evt 
     */
    private void jMenuItem10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem10MousePressed
       if (jMenuItem10.isEnabled()) {
           new PlanManagementFrame();
       }
    }//GEN-LAST:event_jMenuItem10MousePressed
    
    /**
     * Botão para abrir cadastro de funcionário.
     * 
     * @param evt
     */
    private void jMenuItem4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem4MousePressed
       if (jMenuItem4.isEnabled()) {
            new NewEmployeeFrame();
       }
    }//GEN-LAST:event_jMenuItem4MousePressed
    
    /**
     * Botão para abrir a pesquisa e edição de funcionário.
     * 
     * @param evt
     */
    private void jMenuItem5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem5MousePressed
       if (jMenuItem5.isEnabled()) {
	   		new EditEmployeeFrame();
       }
    }//GEN-LAST:event_jMenuItem5MousePressed
    
    /**
     * Botão para abrir as configurações do sistema.
     * 
     * @param evt
     */
    private void jMenuItem18MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem18MousePressed
       if (jMenuItem18.isEnabled()) {
           new SystemConfigurationFrame();
       }
    }//GEN-LAST:event_jMenuItem18MousePressed
    
    /**
     * Créditos.
     * 
     * @param evt
     */
    private void jMenuItem19MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem19MousePressed
        new CreditsFrame();
    }//GEN-LAST:event_jMenuItem19MousePressed
    
    /**
     * Botão para abrir o pagamento de funcionários.
     * 
     * @param evt
     */
    private void jMenuItem17MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem17MousePressed
       if (jMenuItem17.isEnabled()) {
           new EmployeePaymentFrame();
       }
    }//GEN-LAST:event_jMenuItem17MousePressed
    
   
    //REGISTRAR VENDA
    private void jMenuItem8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem8MousePressed
       if (jMenuItem8.isEnabled()) {
//            JFrame j = new JFrameRegistrarVenda();
//            j.pack();
//            j.setVisible(true);         
       }
    }//GEN-LAST:event_jMenuItem8MousePressed
    
    //GERENCIAR ESTOQUE
    private void jMenuItem7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem7MousePressed
       if (jMenuItem7.isEnabled()) {
//            JFrame j = new JFrameGerenciarEstoque();
//            j.pack();
//            j.setVisible(true);         
       }
    }//GEN-LAST:event_jMenuItem7MousePressed
    
    //BALANÇO POR PERÍODO
    private void jMenuItem11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem11MousePressed
       if (jMenuItem11.isEnabled()) {
//            JFrame j = new JFrameBalancoPeriodo();
//            j.pack();
//            j.setVisible(true);        
       }
    }//GEN-LAST:event_jMenuItem11MousePressed
    
    //BALANÇO DE ALUNOS
    private void jMenuItem12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem12MousePressed
       if (jMenuItem12.isEnabled()) {
//            JFrame j = new JFrameEstatisticasAlunos();
//            j.pack();
//            j.setVisible(true);        
       }
    }//GEN-LAST:event_jMenuItem12MousePressed
    
    //BALANÇO ANUAL
    private void jMenuItem13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem13MousePressed
       if (jMenuItem13.isEnabled()) {
//            JFrame j = new JFrameBalancoAnual();
//            j.pack();
//            j.setVisible(true);          
       }
    }//GEN-LAST:event_jMenuItem13MousePressed
    
    //LOG DO SISTEMA
    private void jMenuItem15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem15MousePressed
       if (jMenuItem15.isEnabled()) {
//            JFrame j = new JFrameLogSistema();
//            j.pack();
//            j.setVisible(true);         
       }
    }//GEN-LAST:event_jMenuItem15MousePressed
    
   
    /**
     * Tecla pressionada no campo da chave 1.
     */
    private void keyOneTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyOneTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keyOneTextField.getText(), 1, Gender.UNDEFINED);
        }     
    }//GEN-LAST:event_keyOneTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 2.
     */
    private void keyTwoTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyTwoTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keyTwoTextField.getText(), 2, Gender.UNDEFINED);
        }   
    }//GEN-LAST:event_keyTwoTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 3.
     */
    private void keyThreeTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyThreeTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keyThreeTextField.getText(), 3, Gender.UNDEFINED);
        }   
    }//GEN-LAST:event_keyThreeTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 4.
     */
    private void keyFourTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyFourTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keyFourTextField.getText(), 4, Gender.UNDEFINED);
        }   
    }//GEN-LAST:event_keyFourTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 5.
     */
    private void keyFiveTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyFiveTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keyFiveTextField.getText(), 5, Gender.UNDEFINED);
        }    
    }//GEN-LAST:event_keyFiveTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 6.
     */
    private void keySixTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keySixTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keySixTextField.getText(), 6, Gender.UNDEFINED);
        }    
    }//GEN-LAST:event_keySixTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 7.
     */
    private void keySevenTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keySevenTextFieldKeyPressed
       if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keySevenTextField.getText(), 7, Gender.UNDEFINED);
       }   
    }//GEN-LAST:event_keySevenTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 8.
     */
    private void keyEightTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyEightTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keyEightTextField.getText(), 8, Gender.UNDEFINED);
        }   
    }//GEN-LAST:event_keyEightTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 9.
     */
    private void keyNineTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyNineTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keyNineTextField.getText(), 9, Gender.UNDEFINED);
        }   
    }//GEN-LAST:event_keyNineTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 10.
     */
    private void keyTenTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyTenTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keyNineTextField.getText(), 9, Gender.UNDEFINED);
        }   
    }//GEN-LAST:event_keyTenTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 11.
     */
    private void keyElevenTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyElevenTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keyElevenTextField.getText(), 11, Gender.UNDEFINED);
        }   
    }//GEN-LAST:event_keyElevenTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 12.
     */
    private void keyTwelveTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyTwelveTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keyTwelveTextField.getText(), 12, Gender.UNDEFINED);
        }    
    }//GEN-LAST:event_keyTwelveTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 13.
     */
    private void keyThirteenTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyThirteenTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keyThirteenTextField.getText(), 13, Gender.UNDEFINED);
        }    
    }//GEN-LAST:event_keyThirteenTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 14.
     */
    private void keyFourteenTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyFourteenTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keyFourteenTextField.getText(), 14, Gender.UNDEFINED);
        }    
    }//GEN-LAST:event_keyFourteenTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 15.
     */
    private void keyFifteenTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyFifteenTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keyFifteenTextField.getText(), 15, Gender.UNDEFINED);
        }   
    }//GEN-LAST:event_keyFifteenTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 16.
     */
    private void keySixteenTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keySixteenTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keySixteenTextField.getText(), 16, Gender.UNDEFINED);
        } 
    }//GEN-LAST:event_keySixteenTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 17.
     */
    private void keySeventeenTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keySeventeenTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keySeventeenTextField.getText(), 17, Gender.UNDEFINED);
        }    
    }//GEN-LAST:event_keySeventeenTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 18.
     */
    private void keyEighteenTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyEighteenTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keyEighteenTextField.getText(), 18, Gender.UNDEFINED);
        }    
    }//GEN-LAST:event_keyEighteenTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 19.
     */
    private void keyNineteenTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyNineteenTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keyNineteenTextField.getText(), 19, Gender.UNDEFINED);
        }   
    }//GEN-LAST:event_keyNineteenTextFieldKeyPressed

    /**
     * Tecla pressionada no campo da chave 20.
     */
    private void keyTwentyTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyTwentyTextFieldKeyPressed
        if (super.isEnterPressed(evt)) {
            this.saveKeyEntry(keyTwentyTextField.getText(), 20, Gender.UNDEFINED);
        }    
    }//GEN-LAST:event_keyTwentyTextFieldKeyPressed

    /**
     * Botão adiciona novo agendamento de aula experimental
     * @param evt 
     */
    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        this.createNewScheduleClass();
    }//GEN-LAST:event_jButton3MouseClicked

    /**
     * Salvar texto do painel livre.
     * @param evt 
     */
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        this.saveFreePanelText();
    }//GEN-LAST:event_jButton1MouseClicked

    /**
     * Salvar texto do painel do funcionário.
     * @param evt 
     */
    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        this.saveEmployeePanelText();
    }//GEN-LAST:event_jButton2MouseClicked
    
    //MAIN
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
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
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField keyEightTextField;
    private javax.swing.JTextField keyEighteenTextField;
    private javax.swing.JTextField keyElevenTextField;
    private javax.swing.JTextField keyFifteenTextField;
    private javax.swing.JTextField keyFiveTextField;
    private javax.swing.JTextField keyFourTextField;
    private javax.swing.JTextField keyFourteenTextField;
    private javax.swing.JTextField keyNineTextField;
    private javax.swing.JTextField keyNineteenTextField;
    private javax.swing.JTextField keyOneTextField;
    private javax.swing.JTextField keySevenTextField;
    private javax.swing.JTextField keySeventeenTextField;
    private javax.swing.JTextField keySixTextField;
    private javax.swing.JTextField keySixteenTextField;
    private javax.swing.JTextField keyTenTextField;
    private javax.swing.JTextField keyThirteenTextField;
    private javax.swing.JTextField keyThreeTextField;
    private javax.swing.JTextField keyTwelveTextField;
    private javax.swing.JTextField keyTwentyTextField;
    private javax.swing.JTextField keyTwoTextField;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
