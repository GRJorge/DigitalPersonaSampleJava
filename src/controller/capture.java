package controller;

import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.DPFPCapturePriority;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusListener;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorListener;
import com.digitalpersona.onetouch.readers.DPFPReadersCollection;
import javax.swing.JOptionPane;

/**
 *
 * @author axdevil
 */
public class capture {
    
    private static final DPFPCapture capture = DPFPGlobal.getCaptureFactory().createCapture();
    private static final DPFPReadersCollection readers = DPFPGlobal.getReadersFactory().getReaders();

    //INICIALIZACION DE LA CAPTURA
    public static void start() {
        capture.setReaderSerialNumber(readers.get(0).getSerialNumber());
        capture.setPriority(DPFPCapturePriority.CAPTURE_PRIORITY_LOW);
        events();
        capture.startCapture();
    }

    //FINALIZACION DE LA CAPTURA
    public static void stop() {
        capture.stopCapture();
    }

    //SUSCRIPCION A EVENTOS DEL SENSOR
    private static void events() {
        capture.addDataListener(new DPFPDataAdapter() { //Evento de obtencion de datos
            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                enroller.processCapture(e.getSample()); //Inicia procesamiento de huella            
            }
        });

        /*capture.addDataListener(new DPFPDataListener() {
            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                System.out.println("Captura de huella 2");
            }
        });*/
        capture.addReaderStatusListener(new DPFPReaderStatusListener() {
            @Override
            public void readerConnected(DPFPReaderStatusEvent dpfprs) {
                msj("Sensor conectado");
            }
            
            @Override
            public void readerDisconnected(DPFPReaderStatusEvent dpfprs) {
                msj("Sensor desconectado");
            }
        });
        
        capture.addSensorListener(new DPFPSensorListener() {
            @Override
            public void fingerTouched(DPFPSensorEvent dpfpse) {
                msj("El dedo se coloco en el sensor");
            }
            
            @Override
            public void fingerGone(DPFPSensorEvent dpfpse) {
                msj("El dedo se retiro del sensor");
            }
            
            @Override
            public void imageAcquired(DPFPSensorEvent dpfpse) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }
    
    private static void msj(String msj) {
        System.out.println(msj);
    }
}
