package br.com.abg.deskgym.view.models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import br.com.abg.deskgym.entity.ClassPayment;
import br.com.abg.deskgym.entity.ExperimentalClassSchedule;
import br.com.abg.deskgym.utils.DateUtil;

import lombok.Getter;

/**
 * Modelo para tabela com objeto agendamento de aula experimental.
 * 
 * @author Alvaro
 */
public class ScheduleClassTableModel extends AbstractTableModel {

	/**
	 * Lista de mensalidades.
	 */
	@Getter
    private List<ExperimentalClassSchedule> schedules;

	/**
	 * Nome das colunas.
	 */
    private String[] columns = {"Nome", "Celular", "Horário", ""};

    @Override
    public int getRowCount() {
	return this.schedules.size();
    }

    @Override
    public int getColumnCount() {
	return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
		final ExperimentalClassSchedule schedule = this.schedules.get(rowIndex);
			switch (columnIndex) {
				case 0: return schedule.getStudentName();
				case 1: return schedule.getCellPhone();
				case 2: return DateUtil.getTime(schedule.getDate());
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
    public ExperimentalClassSchedule getSelectedClassPayment(int rowIndex) {
	return this.schedules.get(rowIndex);
    }

    /**
     * Adiciona um objeto na tabela
     *
     * @param schedule agendamento a ser adicionado.
     */
    public void addRow(final ExperimentalClassSchedule schedule){
        this.schedules.add(schedule);
        this.fireTableDataChanged();
    }

    /**
     * Adiciona uma lista de uma só vez.
     *
     * @param schedules lista de planos a serem adicionados.
     */
    public void addRows(List<ExperimentalClassSchedule> schedules) {
		this.schedules.addAll(schedules);
		this.fireTableDataChanged();
    }

	/**
	 * Remove um registro da tabela.
	 *
	 * @param schedule a ser removido.
	 */
	public void removeRow(final ExperimentalClassSchedule schedule) {
		if (this.schedules.remove(schedule)) {
			this.fireTableDataChanged();
		}
	}

	/**
	 * Limpa e atualiza a lista.
	 *
	 * @param schedules
	 */
	public void refresh(final List<ExperimentalClassSchedule> schedules) {
		this.schedules = schedules;
		this.fireTableDataChanged();
	}

	/**
	 * Atualiza um registro na tabela.
	 *
	 * @param oldSchedule
	 * @param newSchedule
	 */
	public void updateRow(final ExperimentalClassSchedule oldSchedule, final ExperimentalClassSchedule newSchedule) {
		final int index = this.schedules.indexOf(oldSchedule);

		if (index >= 0) {
			this.schedules.set(index, newSchedule);
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
		this.schedules = new ArrayList<>();
		this.fireTableDataChanged();
    }

    /**
     * Construtor.
     */
    public ScheduleClassTableModel() {
	this.schedules = new ArrayList<>();
    }    
}
