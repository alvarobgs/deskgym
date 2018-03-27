package br.com.abg.deskgym.entity;

import br.com.abg.deskgym.utils.ImageConverter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade para a foto
 * 
 * @author alvaro
 */
@Entity
@Table(name = "picture")
public class Picture extends AbstractEntity implements Serializable {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = 5614533911434151053L;

    /**
     * Foto.
     */
    @Getter
    @Setter
    @Lob
    @NotEmpty
    @Column(name = "picture_stream")
    private byte[] pictureStream;
    
    /**
     * Foto em memória.
     */
    @Getter
    @Setter
    @Transient
    private transient BufferedImage bufferedPicture;
    
    /**
     * Converte a imagem do buffer para array de bytes.
     * 
     * @throws IOException
     */
    public void convertBufferToBytes() throws IOException {
	this.pictureStream = ImageConverter.convertBufferToBytes(this.bufferedPicture);
    }
    
    /**
     * Converte a imagem do array de bytes para o buffer.
     * 
     * @throws IOException
     */
    public void convertBytesToBuffer() throws IOException {
        this.bufferedPicture = ImageConverter.convertBytesToBuffer(this.pictureStream);
    }
}
