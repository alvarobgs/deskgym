package br.com.abg.deskgym.view.models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import br.com.abg.deskgym.entity.EmployeePayment;

import lombok.Getter;

/**
 * Modelo para tabela com objeto pagamento de funcionário.
 * 
 * @author Alvaro
 */
public class EmployeePaymentTableModel extends AbstractTableModel {

	/**
	 * Lista de pagamentos.
	 */
	@Getter
    private List<EmployeePayment> employeePayments;

	/**
	 * Nome das colunas.
	 */
    private String[] columns = {"Valor", "Pagamento", ""};

    @Override
    public int getRowCount() {
	return this.employeePayments.size();
    }

    @Override
    public int getColumnCount() {
	return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
		final EmployeePayment employeePayment = this.employeePayments.get(rowIndex);
			switch (columnIndex) {
				case 0: return employeePayment.getValue();
				case 1: return employeePayment.getPaymentDate();
				default: return "";
	    }
    }

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return false;
	}

	/**
     * Retorna o pagamento selecionado.
     */
    public EmployeePayment getSelectedClassPayment(int rowIndex) {
	return this.employeePayments.get(rowIndex);
    }

    /**
     * Adiciona um objeto na tabela
     *
     * @param employeePayment movimentação financeira a ser adicionada.
     */
    public void addRow(final EmployeePayment employeePayment){
        this.employeePayments.add(employeePayment);
        this.fireTableDataChanged();
    }

    /**
     * Adiciona uma lista de uma só vez.
     *
     * @param employeePayments lista de planos a serem adicionados.
     */
    public void addRows(List<EmployeePayment> employeePayments) {
		this.employeePayments.addAll(employeePayments);
		this.fireTableDataChanged();
    }

	/**
	 * Remove um registro da tabela.
	 *
	 * @param employeePayment a ser removido.
	 */
	public void removeRow(final EmployeePayment employeePayment) {
		if (this.employeePayments.remove(employeePayment)) {
			this.fireTableDataChanged();
		}
	}

	/**
	 * Limpa e atualiza a lista.
	 *
	 * @param employeePayments
	 */
	public void refresh(final List<EmployeePayment> employeePayments) {
		this.employeePayments = employeePayments;
		this.fireTableDataChanged();
	}

	/**
	 * Atualiza um registro na tabela.
	 *
	 * @param oldEmployeePayment
	 * @param newEmployeePayment
	 */
	public void updateRow(final EmployeePayment oldEmployeePayment, final EmployeePayment newEmployeePayment) {
		final int index = this.employeePayments.indexOf(oldEmployeePayment);

		if (index >= 0) {
			this.employeePayments.set(index, newEmployeePayment);
			this.fireTableDataChanged();
		}
	}

	/**
	 * Retorna o nome da coluna.
	 *
	 * @param index
	 *
	 * @return
	 */
    public String getColumnName(final int index){
        return this.columns[index];
    }

    /**
     * Limpa os dados da tabela.
     */
    public void clearTable() {
		this.employeePayments = new ArrayList<>();
		this.fireTableDataChanged();
    }

    /**
     * Construtor.
     */
    public EmployeePaymentTableModel() {
	this.employeePayments = new ArrayList<>();
    }    
}
