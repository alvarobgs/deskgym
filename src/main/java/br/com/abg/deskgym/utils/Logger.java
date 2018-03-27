package br.com.abg.deskgym.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Implementação para o serviço de log.
 * 
 * @author Alvaro
 */
public class Logger implements Serializable {

    /**
     * Caminho da pasta
     */
    private final String FOLDER = "logs";
    
    /**
     * Salva com base na mensagem da exceção.
	 *
	 * @param message
     */
    public final void save(final String message) {
		try {
			this.saveInFile(message, null, null, null);
		} catch (final IOException e) {
			return;
		}
    }
    
    /**
     * Salva com todos os parâmetros.
	 *
	 * @param className
	 * @param exception
	 * @param message
	 * @param methodName
     */
    public final void save(final String message, final String exception, final String className, final String methodName) {
		try {
			this.saveInFile(message, exception, className, methodName);
		} catch (final IOException e) {
			return;
		}
    }
    
    /**
     * Salva no arquivo.
	 *
	 * @param methodName
	 * @param message
	 * @param exception
	 * @param className
     */
    private void saveInFile(final String message, final String exception, final String className, final String methodName) throws IOException {
	    this.checkFolder();
	    final Calendar calendar = Calendar.getInstance();
	    final String fileName = this.generateFileName(calendar);
	    this.checkTodayLogFile(fileName);

	    final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
	    bufferedWriter.append("=================================== ");
	    bufferedWriter.append(DateUtil.getTime(calendar.getTime()));
	    bufferedWriter.append(" ===================================");
	    bufferedWriter.newLine();
	    bufferedWriter.append(message);

	    if (!Validator.isEmpty(exception)) {
			bufferedWriter.newLine();
			bufferedWriter.append(exception);
	    }
	    if (!Validator.isEmpty(className)) {
			bufferedWriter.newLine();
			bufferedWriter.append(className);
	    }
	    if (!Validator.isEmpty(methodName)) {
			bufferedWriter.newLine();
			bufferedWriter.append(methodName);
	    }

	    bufferedWriter.newLine();
	    bufferedWriter.newLine();
	    bufferedWriter.close();
    }
    
    /**
     * Verifica se a pasta para o log existe.
     */
    private void checkFolder() {
		final File file = new File(FOLDER);
		if (!file.exists()) {
			file.mkdir();
		}
    }
    
    /**
     * Verifica se o arquivo de log existe, e cria o mesmo.
	 *
	 * @param fileName
	 *
	 * @throws IOException
     */
 	private void checkTodayLogFile(final String fileName) throws IOException {
		final File file = new File(fileName);
		file.createNewFile();
    }
     
    /**
     * Gera o nome do arquivo.
	 *
	 * @param calendar
	 *
	 * @return
     */
    private String generateFileName(final Calendar calendar) {
		final StringBuilder fileName = new StringBuilder();
		fileName.append(FOLDER);
		fileName.append("/");
		fileName.append(calendar.get(Calendar.DAY_OF_MONTH));
		fileName.append("-");
		fileName.append(calendar.get(Calendar.MONTH)+1);
		fileName.append("-");
		fileName.append(calendar.get(Calendar.YEAR));
		fileName.append(".txt");

		return fileName.toString();
    }
}
