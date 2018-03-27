package br.com.abg.deskgym.view.models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import br.com.abg.deskgym.entity.ClassPayment;

import lombok.Getter;

/**
 * Modelo para tabela com objeto pagamento de mensalidade.
 * 
 * @author Alvaro
 */
public class ClassPaymentTableModel extends AbstractTableModel {

	/**
	 * Lista de mensalidades.
	 */
	@Getter
    private List<ClassPayment> classPayments;

	/**
	 * Nome das colunas.
	 */
    private String[] columns = {"Plano", "Valor", "Pagamento", "Validade", ""};

    @Override
    public int getRowCount() {
	return this.classPayments.size();
    }

    @Override
    public int getColumnCount() {
	return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
		final ClassPayment classPayment = this.classPayments.get(rowIndex);
			switch (columnIndex) {
				case 0: return classPayment.getName();
				case 1: return classPayment.getPrice();
				case 2: return classPayment.getPaymentDate();
				case 3: return classPayment.getValidityDate();
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
    public ClassPayment getSelectedClassPayment(int rowIndex) {
	return this.classPayments.get(rowIndex);
    }

    /**
     * Adiciona um objeto na tabela
     *
     * @param classPayment movimentação financeira a ser adicionada.
     */
    public void addRow(final ClassPayment classPayment){
        this.classPayments.add(classPayment);
        this.fireTableDataChanged();
    }

    /**
     * Adiciona uma lista de uma só vez.
     *
     * @param classPayments lista de planos a serem adicionados.
     */
    public void addRows(List<ClassPayment> classPayments) {
		this.classPayments.addAll(classPayments);
		this.fireTableDataChanged();
    }

	/**
	 * Remove um registro da tabela.
	 *
	 * @param classPayment a ser removido.
	 */
	public void removeRow(final ClassPayment classPayment) {
		if (this.classPayments.remove(classPayment)) {
			this.fireTableDataChanged();
		}
	}

	/**
	 * Limpa e atualiza a lista.
	 *
	 * @param classPayments
	 */
	public void refresh(final List<ClassPayment> classPayments) {
		this.classPayments = classPayments;
		this.fireTableDataChanged();
	}

	/**
	 * Atualiza um registro na tabela.
	 *
	 * @param oldClassPayment
	 * @param newClassPayment
	 */
	public void updateRow(final ClassPayment oldClassPayment, final ClassPayment newClassPayment) {
		final int index = this.classPayments.indexOf(oldClassPayment);

		if (index >= 0) {
			this.classPayments.set(index, newClassPayment);
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
		this.classPayments = new ArrayList<>();
		this.fireTableDataChanged();
    }

    /**
     * Construtor.
     */
    public ClassPaymentTableModel() {
	this.classPayments = new ArrayList<>();
    }    
}
