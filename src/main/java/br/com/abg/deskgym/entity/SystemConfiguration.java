package br.com.abg.deskgym.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade para configurações do sistema.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "system_configuration")
public class SystemConfiguration extends AbstractEntity implements Serializable {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = -8931802137508248714L;

    /**
     * Dias de tolerância para mensalidade em atraso.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "pending_days", nullable = false)
    private int pendingDays;
    
    /**
     * Dias de tolerância até ser automaticamente inativado.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "pending_days_to_inactive", nullable = false)
    private int pendingDaysToInactive;
    
    /**
     * Minutos de inatividade para ser kickado do sistema.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "inactivity_minutes_to_logout", nullable = false)
    private int inactivityMinutesToLogout;
    
    /**
     * Se a aula experimental está habilitada.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "enable_experimental_class", nullable = false)
    private boolean enableExperimentalClass;

    /**
     * Se a taxa de matrícula está habilitada.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "enable_registration_tax", nullable = false)
    private boolean enableRegistrationTax;
    
    /**
     * Taxa do cartão de crédito.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "credit_card_tax", nullable = false, precision = 10, scale = 4)
    private BigDecimal creditCardTax;
    
    /**
     * Taxa do cartão de débito.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "debit_card_tax", nullable = false, precision = 10, scale = 4)
    private BigDecimal debitCardTax;
    
    /**
     * Valor da taxa de matrícula.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "registration_tax", nullable = false, precision = 10, scale = 2)
    private BigDecimal registrationTax;

    /**
     * Valor da hora aula do professor.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "hour_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal hourPrice;
    
    /**
     * Valor da aula experimental.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "experimental_class_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal experimentalClassPrice;
    
    /**
     * Local para backup automático.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(max = 255)
    @Column(name = "backup_path", nullable = false, length = 255)
    private String backupPath;
    
    /**
     * Local para o executável do mysql.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(max = 255)
    @Column(name = "mysql_path", nullable = false, length = 255)
    private String mysqlPath;
    
    /**
     * Texto do painel livre.
     */
    @Getter
    @Setter
    @Size(max = 10000)
    @Column(name = "free_panel_text", length = 10000)
    private String freePanelText;
    
    /**
     * Último acesso ao sistema.
     */
    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    @Column(name = "last_login")
    private Date lastLogin;
}
