package controller;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;

/**
 *
 * @author axdevil
 */
public class verification {
    private static final DPFPVerification verification = DPFPGlobal.getVerificationFactory().createVerification();
    
    /*
    NOTA: Si la huella se tiene almacenada en bytes se tiene que deserializar de la siguiente manera:
    
    DPFPTemplate storedTemplate = DPFPGlobal.getTemplateFactory().createTemplate(arregloDeBytesAlmacenados);
    */
    public static boolean verify(DPFPSample sample, DPFPTemplate storedTemplate) {
        //SE PROCESA LA NUEVA HUELLA PARA VERIFICACION
        DPFPFeatureSet features = extraction.extract(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

        if (features != null) {
            DPFPVerificationResult result = verification.verify(features, storedTemplate); //SE VERIFICA

            if (result.isVerified()) {
                // La huella coincide con el template storedTemplate
                System.out.println("Huella verificada con éxito.");
                return true;
            } else {
                // La huella no coincide con el template storedTemplate
                System.out.println("La huella no coincide con el template almacenado.");
                return false;
            }
        } else {
            System.err.println("Error al extraer características de la huella.");
            return false;
        }
    }
}
