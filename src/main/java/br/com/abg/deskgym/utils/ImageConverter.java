package br.com.abg.deskgym.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;


public class ImageConverter {
   
    /**
     * Converte a imagem do buffer para array de bytes.
	 *
	 * @param bufferedImage
	 *
	 * @return
     */
    public static byte[] convertBufferToBytes(final BufferedImage bufferedImage) throws IOException {
	if (!Validator.isEmpty(bufferedImage)) {
	    byte[] byteImage;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	   
		ImageIO.write(bufferedImage, "jpg", baos);
	    baos.flush();
	    byteImage = baos.toByteArray();
	    baos.close();
            
		return byteImage;
	}
        return null;
    }
    
    /**
     * Converte a imagem do array de bytes para o buffer.
	 *
	 * @param byteImage
	 *
	 * @return
     */
    public static BufferedImage convertBytesToBuffer(final byte[] byteImage) throws IOException {
	if (!Validator.isEmpty(byteImage)) {
	    InputStream in = new ByteArrayInputStream(byteImage);
	    return ImageIO.read(in);
	}
        return null;
    } 
}
