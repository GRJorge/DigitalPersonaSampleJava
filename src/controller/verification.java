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
 * @author Jorge
 */
public class verification {
    private static final DPFPVerification verification = DPFPGlobal.getVerificationFactory().createVerification();
    
    public static boolean verify(DPFPSample sample, DPFPTemplate storedTemplate) {
        DPFPFeatureSet features = extraction.extract(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

        if (features != null) {
            DPFPVerificationResult result = verification.verify(features, storedTemplate);

            if (result.isVerified()) {
                // La huella coincide con el template almacenado
                System.out.println("Huella verificada con éxito.");
                return true;
            } else {
                // La huella no coincide con el template almacenado
                System.out.println("La huella no coincide con el template almacenado.");
                return false;
            }
        } else {
            System.err.println("Error al extraer características de la huella.");
            return false;
        }
    }
}
