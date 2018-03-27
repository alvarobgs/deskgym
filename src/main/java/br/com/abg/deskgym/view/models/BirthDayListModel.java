package br.com.abg.deskgym.view.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import br.com.abg.deskgym.entity.ClassPayment;
import br.com.abg.deskgym.entity.Student;
import br.com.abg.deskgym.utils.DateUtil;

import lombok.Getter;

/**
 * Modelo para lista com objetos do tipo aluno.
 * 
 * @author Alvaro
 */
public class BirthDayListModel extends AbstractListModel {

	/**
	 * Lista de alunos aniversariantes do mês.
	 */
	@Getter
    private List<Student> students;

    /**
     * Construtor.
     */
    public BirthDayListModel() {
	this.students = new ArrayList<>();
    }

	@Override
	public int getSize() {
		return students.size();
	}

	@Override
	public String getElementAt(final int index) {
    	final Student student = this.students.get(index);
    	final String birthday = DateUtil.convertDateToString(student.getBirthday(), true, true);
    	final String age = String.valueOf(DateUtil.getInterval(student.getBirthday(), new Date()));

		return student.getName() + " (" + age + ") - " + birthday;
	}

	/**
	 * Adiciona uma linha.
	 *
	 * @param student a ser adicionado
	 */
	public void addRow(final Student student) {
		this.students.add(student);
		this.fireContentsChanged(this.students, 0, this.students.indexOf(student));
	}

	/**
	 * Adiciona várias linhas.
	 *
	 * @param students a serem adicionados
	 */
	public void addRows(final List<Student> students) {
    	this.students.addAll(students);
		this.fireContentsChanged(this.students, 0, this.students.size()-1);
	}

	/**
	 * Remove um registro.
	 *
	 * @param student a ser removido.
	 */
	public void removeRow(final Student student) {
		final int index = this.students.indexOf(student);

		if (index > 0 && this.students.remove(student)) {
			this.fireContentsChanged(students, 0, index);
		}
	}

	/**
	 * Limpa e atualiza a lista.
	 *
	 * @param students
	 */
	public void refresh(final List<Student> students) {
		this.students = students;
		this.fireContentsChanged(this.students, 0, this.students.size()-1);
	}
}
