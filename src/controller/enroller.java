package controller;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;

/**
 *
 * @author axdevil
 */
public class enroller {

    public static final DPFPEnrollment enrollment = DPFPGlobal.getEnrollmentFactory().createEnrollment();

    private static DPFPTemplate template;
    private static DPFPFeatureSet featuresInscription;

    public static void processCapture(DPFPSample sample) {
        //PROCESAR HUELLA Y CREAR CARACTERISTICAS
        featuresInscription = extraction.extract(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

        if (featuresInscription != null) {
            try {
                System.out.println("Las Caracteristicas de la Huella han sido creada");
                enrollment.addFeatures(featuresInscription); //SE CREA UN NUEVO TEMPLATE EN EL ENROLLMENT
            } catch (DPFPImageQualityException ex) {
                System.err.println("Error: " + ex.getMessage());
            } finally {
                state();
                switch (enrollment.getTemplateStatus()) {
                    case TEMPLATE_STATUS_READY: //Cuando las cuatro huellas estan registradas
                        setTemplate(enrollment.getTemplate());
                        break;
                    case TEMPLATE_STATUS_FAILED:
                        enrollment.clear();
                        capture.stop();
                        state();
                        setTemplate(null);
                        capture.start();
                        break;
                }
            }
        }
    }

    public static void setTemplate(DPFPTemplate Template) {
        template = Template;
        byte[] templateByte = template.serialize(); //ESTE DATO ES EL QUE SE GUARDA EN BASE DE DATOS
    }

    private static void state() {
        msj("Faltan " + enrollment.getFeaturesNeeded() + " huellas para guardar el template ");
    }

    private static void msj(String msj) {
        System.out.println(msj);
    }
}
