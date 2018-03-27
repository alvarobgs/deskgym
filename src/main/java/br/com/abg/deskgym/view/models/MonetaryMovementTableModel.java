package br.com.abg.deskgym.view.models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import br.com.abg.deskgym.entity.MonetaryMovement;

import lombok.Getter;

/**
 * Modelo para tabela com objeto movimentação financeira.
 * 
 * @author Alvaro
 */
public class MonetaryMovementTableModel extends AbstractTableModel {

	/**
	 * Lista de movimentações financeiras.
	 */
	@Getter
    private List<MonetaryMovement> monetaryMovements;

	/**
	 * Nome das colunas.
	 */
    private String[] columns = {"Nome", "Valor", "Descrição", "Data", ""};

    @Override
    public int getRowCount() {
	return this.monetaryMovements.size();
    }

    @Override
    public int getColumnCount() {
	return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
		final MonetaryMovement monetaryMovement = this.monetaryMovements.get(rowIndex);
			switch (columnIndex) {
				case 0: return monetaryMovement.getName();
				case 1: return monetaryMovement.getValue();
				case 2: return monetaryMovement.getDescription();
				case 3: return monetaryMovement.getDate();
				default: return "";
	    }
    }

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return false;
	}

	/**
     * Retorna a movimentação financeira selecionada.
     */
    public MonetaryMovement getSelectedMonetaryMovement(int rowIndex) {
	return this.monetaryMovements.get(rowIndex);
    }

    /**
     * Adiciona um objeto na tabela
     *
     * @param monetaryMovement movimentação financeira a ser adicionada.
     */
    public void addRow(final MonetaryMovement monetaryMovement){
        this.monetaryMovements.add(monetaryMovement);
        this.fireTableDataChanged();
    }

    /**
     * Adiciona uma lista de uma só vez.
     *
     * @param monetaryMovements lista de planos a serem adicionados.
     */
    public void addRows(List<MonetaryMovement> monetaryMovements) {
		this.monetaryMovements.addAll(monetaryMovements);
		this.fireTableDataChanged();
    }

	/**
	 * Remove um registro da tabela.
	 *
	 * @param monetaryMovement a ser removido.
	 */
	public void removeRow(final MonetaryMovement monetaryMovement) {
		if (this.monetaryMovements.remove(monetaryMovement)) {
			this.fireTableDataChanged();
		}
	}

	/**
	 * Limpa e atualiza a lista.
	 *
	 * @param monetaryMovements
	 */
	public void refresh(final List<MonetaryMovement> monetaryMovements) {
		this.monetaryMovements = monetaryMovements;
		this.fireTableDataChanged();
	}

	/**
	 * Atualiza um registro na tabela.
	 *
	 * @param oldMonetaryMovement
	 * @param newMonetaryMovement
	 */
	public void updateRow(final MonetaryMovement oldMonetaryMovement, final MonetaryMovement newMonetaryMovement) {
		final int index = this.monetaryMovements.indexOf(oldMonetaryMovement);

		if (index >= 0) {
			this.monetaryMovements.set(index, newMonetaryMovement);
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
		this.monetaryMovements = new ArrayList<>();
		this.fireTableDataChanged();
    }

    /**
     * Construtor.
     */
    public MonetaryMovementTableModel() {
	this.monetaryMovements = new ArrayList<>();
    }    
}
