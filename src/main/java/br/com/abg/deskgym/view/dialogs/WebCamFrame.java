package br.com.abg.deskgym.view.dialogs;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamPicker;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import br.com.abg.deskgym.session.Messages;

public class WebCamFrame extends javax.swing.JFrame implements Runnable, Thread.UncaughtExceptionHandler, ItemListener, WebcamListener, WindowListener, WebcamDiscoveryListener {

	/**
	 * Serial UID.
	 */
    private static final long serialVersionUID = -997892153427099207L;

    private WebcamPicker selector = null;
    private Webcam webcam = null;
    private WebcamPanel painel = null;
    private JButton btnSalvar = new javax.swing.JButton();
    private WebCamFrameListener listener = null;
    private Dimension viewSize = new Dimension(320,240);
	//Sizes: QQVGA (176 x 144); QVGA (320 x 240);  VGA (640 x 480);


    public WebCamFrame(final WebCamFrameListener frameListener) throws HeadlessException {
        run();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.listener = frameListener;
		this.pack();
		this.setVisible(true);
    }
   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        //SwingUtilities.invokeLater(new JFrameWebCam());
    }

    @Override
    public void run() {
        Webcam.addDiscoveryListener(this);
        setTitle("WebCam");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        addWindowListener(this);
        selector = new WebcamPicker();
        selector.addItemListener(this);
        webcam = selector.getSelectedWebcam();
        btnSalvar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					btnSalvarMouseClicked(evt);
				} catch (IOException ex) {
					Logger.getLogger(WebCamFrame.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
        });;
        
        
        if(null == webcam) {
            JOptionPane.showMessageDialog(this,
                    "Nenhuma WebCam encontrada!", Messages.ERROR, JOptionPane.ERROR_MESSAGE);
        }
        //webcam.setViewSize(new Dimension(640,480));

        webcam.setViewSize(viewSize);
        painel = new WebcamPanel(webcam, false);
        painel.setFPSDisplayed(true);
        
        add(selector, BorderLayout.NORTH);
        add(painel, BorderLayout.CENTER);
        painel.setPreferredSize(viewSize);
        btnSalvar.setText("FOTOGRAFAR");
        add(btnSalvar, BorderLayout.SOUTH);
        
        pack();
        setVisible(true);
        center(this);
        
        Thread t = new Thread(){
            @Override
            public void run(){
                painel.start();
            }
        };
        
        t.setName("Thread Painel");
        t.setDaemon(true);
        t.setUncaughtExceptionHandler(this);
        t.start();
    }

    private static void center(JFrame f) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setLocation(
                (screenSize.width - f.getWidth()) / 2,
                (screenSize.height - f.getHeight()) / 2
        );
    }    
    
    private void btnSalvarMouseClicked (java.awt.event.MouseEvent evt) throws IOException {
        BufferedImage img = webcam.getImage();
        webcam.close();
        dispose();
        setVisible(false);
        listener.getPicture(img);
    }
    
    @Override
    public void webcamOpen(WebcamEvent we) {
        
    }

    @Override
    public void webcamClosed(WebcamEvent we) {
        
    }

    @Override
    public void webcamDisposed(WebcamEvent we) {
        
    }

    @Override
    public void webcamImageObtained(WebcamEvent we) {
        
    }

    @Override
    public void windowOpened(WindowEvent e) {
    
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        webcam.close();
    }

    @Override
    public void windowIconified(WindowEvent e) {
        painel.pause();
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        painel.resume();
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    
    }

    @Override
    public void webcamFound(WebcamDiscoveryEvent wde) {
        if(null != selector){
            selector.addItem(wde.getWebcam());
        }
    }

    @Override
    public void webcamGone(WebcamDiscoveryEvent wde) {
        if(null != selector){
            selector.removeItem(wde.getWebcam());
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
       if (e.getItem() != webcam) {
           if (null != webcam) {
           		painel.stop();
                remove(painel);
                webcam.removeWebcamListener(this);
                webcam.close();
               
                webcam = (Webcam) e.getItem();
                webcam.setViewSize(viewSize);
                webcam.addWebcamListener(this);
               
                painel = new WebcamPanel(webcam, false);
                painel.setFPSDisplayed(true);
                add(painel, BorderLayout.CENTER);
               
                pack();
                setVisible(true);
               
			    Thread t = new Thread(){
                    @Override
                    public void run(){
                        painel.start();
                    }
                };
        
			    t.setName("Thread Painel");
			    t.setDaemon(true);
			    t.setUncaughtExceptionHandler(this);
			    t.start();
		   }
       } 
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        JOptionPane.showMessageDialog(this, "Não foi possível localizar a WebCam!", Messages.ERROR, JOptionPane.ERROR_MESSAGE);
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
