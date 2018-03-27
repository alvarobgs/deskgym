package br.com.abg.deskgym.exceptions;

import br.com.abg.deskgym.utils.Logger;
import br.com.abg.deskgym.utils.Validator;
import javax.swing.JOptionPane;

/**
 * Exceção para capturar todas as Runtime Exceptions.
 * 
 * @author Alvaro
 */
public class RuntimeUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    /**
     * Log.
     */
    private Logger logger;
    
    @Override
    public void uncaughtException(Thread t, Throwable e) {
		this.logger = new Logger();

		final StringBuilder message = new StringBuilder();

		if (!Validator.isEmpty(t)) {
			message.append(t.toString());
			message.append(" \n");
		}

		if (!Validator.isEmpty(e)) {
				message.append(e.toString());
				message.append(" \n");
				message.append(e.getMessage());
				message.append(" \n");
				message.append(e.getLocalizedMessage());
				message.append(" \n");
			if (!Validator.isEmpty(e.getCause())) {
				message.append(e.getCause().toString());
				message.append(" \n");
			}
		}
		this.logger.save(message.toString());
//		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "UM ERRO FATAL OCORREU!\nPor favor, contacte o administrador do sistema.\nRecomenda-se reiniciar o sistema e esperar para reexecutar esta ação assim que o problema for corrigido.", "ERRO FATAL", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
    }
}
