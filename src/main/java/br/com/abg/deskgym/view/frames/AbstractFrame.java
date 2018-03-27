package br.com.abg.deskgym.view.frames;

import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.utils.Logger;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;

/**
 * Frame abstrato.
 * 
 * @author Alvaro
 */
public abstract class AbstractFrame extends javax.swing.JFrame {
    
    /**
     * String para o caminho do local do arquivo.
     */
    protected static final transient String DESKTOP_PATH = System.getProperty("user.home") + "/Desktop";
    
    /**
     * Nome do filtro para formatos de arquivos.
     */
    protected static final transient String FILE_FILTER_NAME = "Arquivos de imagem (.jpeg)";
    
    /**
     * Filtro para formatos de arquivos.
     */
    protected static final transient String FILE_FILTER_TYPE = "jpg";
    
    /**
     * Logger.
     */
    protected static transient Logger logger = new Logger();
    
    /**
     * Atribui o look nimbus.
	 *
	 * @param frame
     */
    protected static void setLook(JFrame frame){
        for (final UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(laf.getName())) {
                try {
                    UIManager.setLookAndFeel(laf.getClassName());
                } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    logger.save(ex.getMessage());
                }
            }
        }
        SwingUtilities.updateComponentTreeUI(frame);
    }
    
    /**
     * Centraliza o frame.
	 *
	 * @param frame
     */
    protected static void center(final JFrame frame) {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(
                (screenSize.width - frame.getWidth()) / 2,
                (screenSize.height - frame.getHeight()) / 2
        );
    }
    
    /**
     * Seta o ícone no header.
     */
    protected void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo.png")));
    }
    
    /**
     * Verifica se a tecla pressionada foi o ENTER
	 *
	 * @param evt
	 *
	 * @return
     */
    protected boolean isEnterPressed(final java.awt.event.KeyEvent evt) {
	return evt.getKeyCode() == 10;
    }

    /**
     * Verifica se o usuário selecionou confirmar.
     *
     * @param parent
     * @param message
     *
     * @return
     */
    protected boolean showConfirmDialog(final JFrame parent, final String message) {
        return JOptionPane.showConfirmDialog(parent, message, Messages.WARNING, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

	/**
	 * Abre um dialog de aviso ao usuário com uma mensagem de atenção.
	 *
	 * @param parent
	 * @param message
	 */
	protected void showWarningDialog(final JFrame parent, final String message) {
		JOptionPane.showMessageDialog(parent, message, Messages.WARNING, JOptionPane.WARNING_MESSAGE);
	}

    /**
     * Abre um dialog de aviso ao usuário com uma mensagem de erro.
     *
     * @param parent
     * @param message
     */
    protected void showErrorDialog(final JFrame parent, final String message) {
        JOptionPane.showMessageDialog(parent, message, Messages.ERROR, JOptionPane.ERROR_MESSAGE);
    }

	/**
	 * Abre um dialog de aviso ao usuário com uma mensagem de sucesso.
	 *
	 * @param parent
	 * @param message
	 */
	protected void showSuccessDialog(final JFrame parent, final String message) {
		JOptionPane.showMessageDialog(parent, message, Messages.SUCCESS, JOptionPane.INFORMATION_MESSAGE);
	}
}
