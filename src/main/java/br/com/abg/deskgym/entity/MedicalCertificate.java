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
 * Entidade para o atestado médico.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "medical_certificate")
public class MedicalCertificate extends AbstractEntity implements Serializable {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = -5468299008223175239L;

    /**
     * Atestado médico.
     */
    @Getter
    @Setter
    @Lob
    @NotEmpty
    @Column(name = "medical_certificate_stream")
    private byte[] medicalCertificateStream;
    
    /**
     * Atestado em buffer.
     */
    @Getter
    @Setter
    @Transient
    private transient BufferedImage bufferedMedicalCertificate;
        
    /**
     * Converte a imagem do buffer para array de bytes.
     * 
     * @throws IOException
     */
    public void convertBufferToBytes() throws IOException {
        this.medicalCertificateStream = ImageConverter.convertBufferToBytes(this.bufferedMedicalCertificate);
    }
    
    /**
     * Converte a imagem do array de bytes para o buffer.
     * 
     * @throws IOException
     */
    public void convertBytesToBuffer() throws IOException {
        this.bufferedMedicalCertificate = ImageConverter.convertBytesToBuffer(this.medicalCertificateStream);
    }
}
