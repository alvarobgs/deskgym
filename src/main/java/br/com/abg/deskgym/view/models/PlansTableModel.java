package br.com.abg.deskgym.view.models;

import br.com.abg.deskgym.entity.Plan;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo para tabela com objeto planos
 *
 * @see http://www.informit.com/articles/article.aspx?p=333472&seqNum=2
 * @see https://stackoverflow.com/questions/27250401/creating-a-jtable-from-abstracttablemodel-in-netbeans
 * @see http://www.guj.com.br/t/resolvido-atualizar-jframe-principal-com-o-fechamento-de-outro-jframe/235629/10
 * 
 * @author Alvaro
 */
public class PlansTableModel extends AbstractTableModel {

	/**
	 * Lista de planos.
	 */
	private List<Plan> plans;

	/**
	 * Nome das colunas.
	 */
    private String[] columns = {"Nome", "Preço", "Duração", ""};

    @Override
    public int getRowCount() {
	return this.plans.size();
    }

    @Override
    public int getColumnCount() {
	return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
		final Plan plan = this.plans.get(rowIndex);
			switch (columnIndex) {
				case 0: return plan.getName();
				case 1: return plan.getPrice();
				case 2: return plan.getDuration();
				default: return "";
	    }
    }

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return false;
	}

	/**
     * Retorna o plano selecionado.
     */
    public Plan getSelectedPlan(int rowIndex) {
	return this.plans.get(rowIndex);
    }
    
    /**
     * Adiciona um objeto na tabela
     * 
     * @param plan plano a ser adicionado.
     */
    public void addRow(final Plan plan){
        this.plans.add(plan);
        this.fireTableDataChanged();
    }
    
    /**
     * Adiciona uma lista de uma só vez.
     * 
     * @param plans lista de planos a serem adicionados.
     */
    public void addRows(final List<Plan> plans) {
		this.plans.addAll(plans);
		this.fireTableDataChanged();
    }

	/**
	 * Limpa e atualiza a lista.
	 *
	 * @param plans
	 */
	public void refresh(final List<Plan> plans) {
    	this.plans = plans;
    	this.fireTableDataChanged();
	}

	/**
	 * Remove um registro da tabela.
	 *
	 * @param plan plano a ser removido.
	 */
	public void removeRow(final Plan plan) {
		if (this.plans.remove(plan)) {
			this.fireTableDataChanged();
		}
	}

	/**
	 * Atualiza um registro na tabela.
	 *
	 * @param oldPlan
	 * @param newPlan
	 */
	public void updateRow(final Plan oldPlan, final Plan newPlan) {
		final int index = this.plans.indexOf(oldPlan);

		if (index >= 0) {
			this.plans.set(index, newPlan);
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
		this.plans = new ArrayList<>();
		this.fireTableDataChanged();
    }
    
    /**
     * Construtor.
     */
    public PlansTableModel() {
	this.plans = new ArrayList<>();
    }    
}
