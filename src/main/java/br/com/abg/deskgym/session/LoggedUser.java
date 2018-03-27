package br.com.abg.deskgym.session;

import br.com.abg.deskgym.entity.Employee;
import lombok.Getter;
import lombok.Setter;

public class LoggedUser {
    
    private static LoggedUser loggedUser;
    
    @Getter
    @Setter
    private Employee employee;
    
    /**
     * Retorna o funcionário logado.
     */
    public static synchronized LoggedUser getInstance() {
        if(loggedUser == null) {
            loggedUser = new LoggedUser();
        }
        return loggedUser;
    }
    
    /**
     * Seta o funcionário logado.
     */
    public void setInstancedEmployee(final Employee employee) {
        this.getInstance().setEmployee(employee);
    }
    
    /**
     * Retorna o funcionário logado.
     */
    public Employee getInstancedEmployee() {
        return this.getInstance().getEmployee();
    }
}
