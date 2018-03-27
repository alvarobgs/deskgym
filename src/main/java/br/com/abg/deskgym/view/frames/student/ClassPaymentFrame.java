package br.com.abg.deskgym.view.frames.student;


import br.com.abg.deskgym.entity.ClassPayment;
import br.com.abg.deskgym.entity.ExtraTaxPayment;
import br.com.abg.deskgym.entity.Plan;
import br.com.abg.deskgym.entity.Student;
import br.com.abg.deskgym.entity.SystemConfiguration;
import br.com.abg.deskgym.enums.PaymentMethod;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import br.com.abg.deskgym.service.ClassPaymentService;
import br.com.abg.deskgym.service.PlanService;
import br.com.abg.deskgym.service.StudentService;
import br.com.abg.deskgym.service.SystemConfigurationService;
import br.com.abg.deskgym.service.impl.ClassPaymentServiceImpl;
import br.com.abg.deskgym.service.impl.PlanServiceImpl;
import br.com.abg.deskgym.service.impl.StudentServiceImpl;
import br.com.abg.deskgym.service.impl.SystemConfigurationServiceImpl;
import br.com.abg.deskgym.session.Constant;
import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.utils.Converter;
import br.com.abg.deskgym.utils.DateUtil;
import br.com.abg.deskgym.utils.Validator;
import br.com.abg.deskgym.view.dialogs.ExtraTaxDialog;
import br.com.abg.deskgym.view.frames.AbstractFrame;
import br.com.abg.deskgym.view.models.PlansTableModel;

import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.swing.*;

import lombok.Getter;
import lombok.Setter;

public class ClassPaymentFrame extends AbstractFrame {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 3656356568013778044L;

	/**
     * Serviço de aluno.
     */
    private StudentService studentService;

    /**
     * Serviço de pagamento de mensalidade.
     */
    private ClassPaymentService classPaymentService;
    
    /**
     * Serviço de planos.
     */
    private PlanService planService;

	/**
	 * Serviço de configurações do sistema.
	 */
	private SystemConfigurationService systemConfigurationService;
    
    /**
     * Boolean para indicar se a janela deve ser fechada após algum pagamento ser realizado.
     */
    private boolean closeAfterPayment;
    
    /**
     * Filtro de nome.
     */
    @Getter
    @Setter
    private String filter;
    
    /**
     * Aula experimental.
     */
    @Getter
    @Setter
    private boolean experimentalClass;
    
    /**
     * Se a taxa de matrícula é obrigatória.
     */
    boolean requiredRegistrationTax;
    
    /**
     * Se a aula experimental pode ser habilitada.
     */
    boolean enableExperimentalClass;

	/**
	 * Configurações do sistema.
	 */
	private SystemConfiguration systemConfiguration;

	/**
     * Aluno.
     */
    @Getter
    @Setter
    private Student student;
    
    /**
     * Taxa extra.
     */
    private ExtraTaxPayment extraTaxPayment;

	/**
	 * Modelo da tabela de planos.
	 */
	private PlansTableModel plansTableModel;

	/**
     * Construtor
     */
    public ClassPaymentFrame() {
		this.student = new Student();
        this.init();
		this.closeAfterPayment = false;
    }
    
    /**
     * Construtor com aluno já instanciado.
     */
    public ClassPaymentFrame(final Student student) {
		this.student = student;
        this.init();

        this.closeAfterPayment = true;
		this.fillPlansTable();
    }
    
    /**
     * Inicia o frame.
     */
    public void init() {
        initComponents();
        this.requiredRegistrationTax = false;
        this.enableExperimentalClass = false;
       
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Pagamento de Mensalidade");
		setLook(this);
        center(this);
        super.setIcon();
	
        jTextField1.requestFocus();

		this.experimentalClass = false;
        this.classPaymentService = new ClassPaymentServiceImpl();
        this.studentService = new StudentServiceImpl();
        this.planService = new PlanServiceImpl();
        this.systemConfigurationService = new SystemConfigurationServiceImpl();

        this.systemConfiguration = this.systemConfigurationService.getSystemConfiguration();

		this.fillPaymentMethodCombo();

		jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(400); 
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);

		this.setVisible(true);
    }
    
    /**
     * Carrega combo com os modos de pagamento.
     */
    private void fillPaymentMethodCombo(){
		for (final PaymentMethod paymentMethod : PaymentMethod.values()) {
			 jComboBox2.addItem(paymentMethod.toString());
		}
    }
    
    /**
     * Preenche a tabela com os planos disponíveis para este aluno.
     */
    private void fillPlansTable() {
    	jCheckBox1.setEnabled(this.student.isBrandNew() && this.systemConfiguration.isEnableExperimentalClass());
		this.plansTableModel.addRows(this.planService.listActivePlans());
    }
    
    /**
     * Verifica o tipo do pagamento antes de salvar.
     */
    private void savePayment() {
		try {
			if (this.isExperimentalClass()) {
				this.saveExperimentalClass();
			} else {
				if (jTable1.getSelectedRowCount() != 1) {
					super.showWarningDialog(this, Messages.SELECT_ONE_ROW_ONLY_ERROR);
					return;
				}
				this.saveClassPayment();
			}
			this.clearFields();
			this.enableFields(false);
			super.showSuccessDialog(this, Messages.SAVE_SUCCESSFULLY);

			if (this.closeAfterPayment) {
				this.closeFrame();
			}
		} catch (final IOException e) {
			super.showErrorDialog(this, Messages.UNKNOWN_ERROR);
			logger.save(e.getMessage(), e.getClass().toString(), Constant.CLASS_PAYMENT_FRAME, "savePayment");
		} catch (final ObjectNotFoundException e) {
			super.showErrorDialog(this, " " + Messages.NOT_FOUND);
			logger.save(e.getMessage(), e.getClass().toString(), Constant.CLASS_PAYMENT_FRAME, "savePayment");
		} catch (final FieldInvalidException | CustomMessageException e) {
			super.showWarningDialog(this, e.getMessage());
			logger.save(e.getMessage(), e.getClass().toString(), Constant.CLASS_PAYMENT_FRAME, "savePayment");
		} catch (final ObjectBeingEditedException e) {
			super.showErrorDialog(this, " " + Messages.BEING_EDITED);
			logger.save(e.getMessage(), e.getClass().toString(), Constant.CLASS_PAYMENT_FRAME, "savePayment");
		} catch (final ObjectExistsException e) {
			super.showErrorDialog(this, " " + Messages.ALREADY_EXISTS);
			logger.save(e.getMessage(), e.getClass().toString(), Constant.CLASS_PAYMENT_FRAME, "savePayment");
		}
    }

	/**
	 * Salva um pagamento comum.
	 */
	private void saveClassPayment() throws ObjectExistsException, FieldInvalidException, ObjectNotFoundException, IOException, ObjectBeingEditedException, CustomMessageException {
		final Plan plan = this.plansTableModel.getSelectedPlan(jTable1.getSelectedRow());
		this.verifyExtraTax();

		final Date paymentDate = jDateChooser1.getDate();
		final Date startDate = jDateChooser2.getDate();
		final Date validityDate = jDateChooser3.getDate();

		final boolean activePaymentConfirm = this.checkActivePayment();
		final boolean dateIntervalConfirm = this.checkDateInterval(startDate, validityDate, paymentDate, plan.getDuration());

		if (activePaymentConfirm && dateIntervalConfirm) {
			final ClassPayment payment = new ClassPayment();
			payment.setName(plan.getName());
			payment.setStudent(this.student);
			payment.setPlan(plan);
			payment.setStartDate(startDate);
			payment.setValidityDate(validityDate);
			payment.setPaymentDate(paymentDate);
			payment.setPaymentMethod(PaymentMethod.valueOf(String.valueOf(jComboBox1.getSelectedItem())));
			payment.setExperimentalClass(false);
			payment.setPlots(Integer.parseInt(jFormattedTextField2.getText()));
			payment.setPrice(plan.getPrice());

			if (super.showConfirmDialog(this, Messages.classPaymentConfirmMessage(payment, this.extraTaxPayment))) {
				this.classPaymentService.registerNewPayment(payment, this.extraTaxPayment, this.systemConfiguration.getCreditCardTax(), this.systemConfiguration.getDebitCardTax());
			}
		}
	}

	/**
	 * Salva a aula experimental.
	 */
	private void saveExperimentalClass() throws ObjectExistsException, FieldInvalidException, ObjectNotFoundException, IOException, ObjectBeingEditedException, CustomMessageException {
		final Date paymentDate = jDateChooser1.getDate();
		final Date startDate = jDateChooser2.getDate();

		final ClassPayment payment = new ClassPayment();
		payment.setName(Messages.EXPERIMENTAL_CLASS);
		payment.setStudent(this.student);
		payment.setStartDate(startDate);
		payment.setValidityDate(startDate);
		payment.setPaymentDate(paymentDate);
		payment.setPaymentMethod(PaymentMethod.valueOf(String.valueOf(jComboBox1.getSelectedItem())));
		payment.setExperimentalClass(true);
		payment.setPlots(Integer.parseInt(jFormattedTextField2.getText()));
		payment.setPrice(this.systemConfiguration.getExperimentalClassPrice());

		if (super.showConfirmDialog(this, Messages.classPaymentConfirmMessage(payment, null))) {
			this.classPaymentService.registerNewPayment(payment, null, this.systemConfiguration.getCreditCardTax(), this.systemConfiguration.getDebitCardTax());
		}
	}

	/**
	 * Verifica se será cobrada taxa extra.
	 *
	 * @return a taxa se tiver cobrança, null se não houver
	 */
	private void verifyExtraTax() {
		if (this.requiredRegistrationTax) {
			this.extraTaxPayment.setDescription(Messages.REGISTRATION_TAX);
			this.extraTaxPayment.setPrice(this.systemConfiguration.getRegistrationTax());
		}
	}

	/**
	 * Verifica se o aluno possui pagamento ativo e pede confirmação.
	 *
	 * @return
	 */
	private boolean checkActivePayment() {
		if (this.classPaymentService.checkActivePaymentByStudent(this.student.getId())) {
			return super.showConfirmDialog(this, "Este aluno ainda possui um plano ativo. Deseja continuar?");
		}
		return true;
	}

	/**
	 * Verifica se o intervalo de datas selecionadas está correto.
	 *
	 * @param startDate data inicial
	 * @param endDate data final
	 * @param paymentDate data do pagamento
	 * @param expectedDuration duração esperada do plano
	 *
	 * @return true se estiver tudo ok.
	 */
	private boolean checkDateInterval(final Date startDate, final Date endDate, final Date paymentDate, final int expectedDuration) {
		final int interval = DateUtil.getInterval(startDate, endDate);

		if (interval > expectedDuration + 7) {
            return super.showConfirmDialog(this, Messages.tooLargeDateInterval(endDate, interval, expectedDuration));
		} else if (interval + 7 < expectedDuration) {
            return super.showConfirmDialog(this, Messages.tooSmallDateInterval(endDate, interval, expectedDuration));
		} else if (endDate.before(paymentDate)) {
            return super.showConfirmDialog(this, Messages.END_DATE_BEFORE_PAYMENT);
		} else {
			return true;
		}
	}
    
    /**
     * Fecha o frame
     */
    private void closeFrame() {
		if (this.closeAfterPayment) {
			this.dispose();
		}
    }
    
    /**
     * Filtra os alunos.
     */
    private void search() {
        final List<String> studentNames = this.studentService.filterNameByNameAndStatus(filter, null);

        if (Validator.isEmpty(studentNames)) {
			super.showErrorDialog(this, Messages.NOTHING_FOUND);
            return;
        }

        jComboBox1.removeAllItems();
        jComboBox1.setEnabled(true);
        jComboBox1.addItem(Messages.SELECT);

        for(final String name : studentNames) {
            jComboBox1.addItem(name);
        }
    }
    
    /**
     * Realiza a alteração no modo de pagamento.
     */
    private void verifyPaymentMethodChange() {
		final String method = jComboBox2.getSelectedItem().toString();
		final String creditCard = String.valueOf(PaymentMethod.CREDIT_CARD);

		if (method.equals(creditCard)) {
			jFormattedTextField2.setEnabled(true);
			jFormattedTextField2.setText("1");
		} else {
			jFormattedTextField2.setEnabled(false);
			jFormattedTextField2.setText("");
		}
    }
    
    /**
     * Função que habilita os campos
     */
    private void enableFields(final boolean b){          
        jButton1.setEnabled(b);
        jButton2.setEnabled(b);
        
        jDateChooser1.setEnabled(b);
        jDateChooser2.setEnabled(b);
        jDateChooser3.setEnabled(b);
        
        
        jFormattedTextField2.setEnabled(b);
        
        jCheckBox1.setEnabled(b);
        
        jComboBox2.setEnabled(b);
        
        jTable1.setEnabled(b);
    }
    
    /**
     * Método que busca o aluno.
     */
    private void searchStudent() {
        try {
        	if (Validator.isEmpty(jComboBox1.getSelectedItem())) {
        		return;
			}
            final String selectedName = jComboBox1.getSelectedItem().toString();

            this.student = this.studentService.getByName(selectedName);

            if (Validator.isEmpty(this.student.getId())) {
                return;
            }

            this.clearFields();
            this.enableFields(true);
            jFormattedTextField2.setEnabled(false);
            this.setStudentData();
        } catch (final FieldInvalidException e) {
			super.showWarningDialog(this, e.getMessage());
			logger.save(e.getMessage(), e.getClass().toString(), Constant.CLASS_PAYMENT_FRAME, "searchStudent");
        }
    }
    
    /**
     * Função que limpa os campos
     */
    private void clearFields(){
        jFormattedTextField2.setText("");
    
		jLabel6.setText("-");
		jLabel8.setText("-");
        jLabel14.setText("-");
        jLabel17.setText("-");
		jLabel20.setText("-");
		jLabel22.setText("-");
        jLabel23.setText("-");
        
        jDateChooser1.setDate(null);
        jDateChooser2.setDate(null);
		jDateChooser3.setDate(null);
        
        jLabel16.setIcon(null);
	
		jComboBox2.setSelectedIndex(0);
		this.experimentalClass = false;
        
        this.plansTableModel.clearTable();
    }
    
    /**
     * Data de início do plano sofreu alteração.
     */
    private void recalculatePlanDates() {
		final Date startDate = jDateChooser2.getDate();
		final Date validityDate = jDateChooser3.getDate();

		if (!Validator.isEmpty(startDate) && jTable1.getSelectedRowCount() == 1) {
			if (Validator.isEmpty(validityDate)) {
				final int duration = this.plansTableModel.getSelectedPlan(jTable1.getSelectedRow()).getDuration();
				jDateChooser3.setDate(DateUtil.addDaysToDate(startDate, duration));
				jLabel8.setText(String.valueOf(duration));
			} else {
				jLabel8.setText(String.valueOf(DateUtil.getInterval(startDate, validityDate)));
			}
		}
    }
    
    /**
     * Seta os valores do aluno nos campos.
     */
    private void setStudentData() {
		jLabel16.setIcon(new ImageIcon(this.student.getPicture().getBufferedPicture()));
		jLabel14.setText(String.valueOf(this.student.getId()));
		jLabel17.setText(this.student.getName());
		jLabel20.setText(this.student.getCpf());
		jLabel23.setText(String.valueOf(this.student.getPaymentDay()));
		this.verifyLastPayment();
    }
    
    /**
     * Verifica a mensalidade mais recente paga.
     */
    private void verifyLastPayment() {
		final ClassPayment classPayment = this.student.getLastClassPayment();
		
		if (Validator.isEmpty(classPayment)) {
			jLabel22.setText(Messages.NOTHING_FOUND);
			jLabel22.setBackground(Color.BLACK);
			this.requiredRegistrationTax = this.systemConfiguration.isEnableRegistrationTax();
			this.enableExperimentalClass = true;
		} else {
			if (classPayment.isExperimentalClass()) {
				jLabel22.setText(classPayment.getName());
				this.requiredRegistrationTax = this.systemConfiguration.isEnableRegistrationTax();
			} else {
				final String date = DateUtil.convertDateToString(classPayment.getValidityDate(), true, true);
				if (classPayment.isOutDated()) {
				    jLabel22.setText("Expirado em " + date);
				    jLabel22.setBackground(Color.RED);
				} else {
				    jLabel22.setText("Ativo até " + date);
				    jLabel22.setBackground(Color.GREEN);
				}
	    	}
		}
		this.loadPlans();
    }
    
    /**
     * Carrega os planos e os valores de aula experimental.
     */
    private void loadPlans() {
		jCheckBox1.setEnabled(this.enableExperimentalClass && this.systemConfiguration.isEnableExperimentalClass());
		this.plansTableModel.addRows(this.planService.listActivePlans());
    }

	/**
	 * Mudança de status do checkbox da aula experimental.
	 */
	private void experimentalClassStatusChange() {
		this.experimentalClass = jCheckBox1.isSelected();
		jTable1.setEnabled(!this.experimentalClass);
		jDateChooser3.setEnabled(!this.experimentalClass);
		jButton1.setEnabled(!this.experimentalClass);
		jFormattedTextField2.setEnabled(!this.experimentalClass);

		if (this.experimentalClass) {
			jLabel6.setText(Messages.EXPERIMENTAL_CLASS);
			jLabel8.setText("1");
		} else {
			jTable1.clearSelection();
			jLabel6.setText("-");
			jLabel8.setText("-");
		}
	}

	/**
	 * Ação de selecionar um plano na tabela.
	 */
	private void selectPlanOnTable() {
		jDateChooser1.setEnabled(true);
		jDateChooser2.setEnabled(true);
		jLabel6.setText(this.plansTableModel.getSelectedPlan(jTable1.getSelectedRow()).getName());
		this.recalculatePlanDates();
	}
        
        /**
         * Chama o dialog para adicionar uma taxa extra ao pagamento.
         */
        private void addExtraTax() {
            if (this.requiredRegistrationTax) {
                super.showErrorDialog(this, "Este aluno já deverá pagar a taxa de matrícula.");
                return;
		}
            final ExtraTaxDialog etd = new ExtraTaxDialog(this, true, this.extraTaxPayment);
            this.extraTaxPayment = etd.getExtraTaxPayment();
        }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Planos"));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn_small/success.png"))); // NOI18N
        jButton2.setText("CONFIRMAR PAGAMENTO");
        jButton2.setEnabled(false);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jLabel18.setText("Modo de Pagamento:");

        jComboBox2.setEnabled(false);
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jLabel9.setText("Parcelas:");

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField2.setEnabled(false);

        jCheckBox1.setText("Aula experimental");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${experimentalClass}"), jCheckBox1, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        jCheckBox1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCheckBox1PropertyChange(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn_small/add.png"))); // NOI18N
        jButton1.setText("ADICIONAR TAXA EXTRA");
        jButton1.setEnabled(false);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jLabel11.setText("(Nome da taxa extra e valor)");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jFormattedTextField2)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jCheckBox1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        jTable1.setEnabled(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel24.setText("Início:");

        jLabel5.setText("Plano selecionado:");

        jLabel6.setText("-");

        jDateChooser2.setEnabled(false);
        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });
        jDateChooser2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDateChooser2KeyPressed(evt);
            }
        });

        jDateChooser3.setEnabled(false);
        jDateChooser3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser3PropertyChange(evt);
            }
        });
        jDateChooser3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDateChooser3KeyPressed(evt);
            }
        });

        jDateChooser1.setEnabled(false);

        jLabel2.setText("Expiração:");

        jLabel12.setText("Pagamento:");

        jLabel7.setText("Dias totais selecionados:");

        jLabel8.setText("-");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultado da busca"));

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("ID");

        jLabel14.setText("-");

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("NOME");

        jLabel17.setText("-");

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("CPF");

        jLabel20.setText("-");

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("PLANO");

        jLabel22.setText("-");

        jLabel23.setText("-");

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setText("VENC. PREF.");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
                                .addGap(0, 12, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/plain_48x48.png"))); // NOI18N
        jLabel1.setText(" PAGAMENTO DE MENSALIDADE");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar aluno"));

        jLabel4.setText("*Digite o nome do aluno e aperte ENTER");

        jComboBox1.setEnabled(false);
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel3.setText("NOME");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(89, 89, 89))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Botão realizar pagamento
     */
    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        if (jButton2.isEnabled()){
	    	this.savePayment();
        }        
    }//GEN-LAST:event_jButton2MouseClicked

    /**
     * Filtra os alunos de acordo com o nome.
     */
    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        if (super.isEnterPressed(evt)){
            this.search();
        }
    }//GEN-LAST:event_jTextField1KeyPressed
    
    /**
     * Alteração no modo de pagamento.
     */
    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        if (jComboBox2.isEnabled()){
            this.verifyPaymentMethodChange();
        }
    }//GEN-LAST:event_jComboBox2ItemStateChanged
    
    /**
     * Combobox busca alunos
     */
    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
		if (jComboBox1.getSelectedIndex() != 0) {
			this.searchStudent();
		}
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    /**
     * Ação de selecionar um plano na tabela
     */
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
		if (jTable1.isEnabled()) {
			this.selectPlanOnTable();
		}
    }//GEN-LAST:event_jTable1MouseClicked

    /**
     * Ação de escolher uma data de início do plano.
     */
    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange
        if (!Validator.isEmpty(jDateChooser2.getDate())) {
           this.recalculatePlanDates();
        }
    }//GEN-LAST:event_jDateChooser2PropertyChange

    /**
     * Ação de escolher uma data de encerramento do plano.
     */
    private void jDateChooser3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser3PropertyChange
       if (!Validator.isEmpty(jDateChooser2.getDate()) && !Validator.isEmpty(jDateChooser3.getDate())) {
            this.recalculatePlanDates();
        }
    }//GEN-LAST:event_jDateChooser3PropertyChange

    /**
     * Ação de escolher uma data de início.
     */
    private void jDateChooser2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooser2KeyPressed
        if (super.isEnterPressed(evt) && !Validator.isEmpty(jDateChooser2.getDate())) {
	    	this.recalculatePlanDates();
		}
    }//GEN-LAST:event_jDateChooser2KeyPressed

    /**
     * Ação de escolher uma data de encerramento.
     */
    private void jDateChooser3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooser3KeyPressed
        if (super.isEnterPressed(evt) && !Validator.isEmpty(jDateChooser3.getDate())) {
	    	this.recalculatePlanDates();
        }
    }//GEN-LAST:event_jDateChooser3KeyPressed

    /**
     * Mudança de estado do checkbox de aula experimental
     */
    private void jCheckBox1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCheckBox1PropertyChange
		this.experimentalClassStatusChange();
    }//GEN-LAST:event_jCheckBox1PropertyChange

    /**
     * Botão de adicionar taxa extra
     * 
     * @param evt 
     */
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        if (jButton1.isEnabled()) {
            this.addExtraTax();
        }
    }//GEN-LAST:event_jButton1MouseClicked

    /**
     * Main
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClassPaymentFrame().setVisible(true);
            }
        });
    } 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private javax.swing.JFormattedTextField jFormattedTextField2;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
