package org.openmrs.api.example;

import org.openmrs.Patient;
import org.openmrs.util.DatabaseUpdateException;
import org.openmrs.util.InputRequiredException;
import org.openmrs.util.OpenmrsUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Properties;

import static org.openmrs.api.context.Context.*;
import static org.openmrs.api.context.Context.closeSession;
import static org.openmrs.util.OpenmrsUtil.loadProperties;

public class Example {
    public static void main(String[] args) throws FileNotFoundException, InputRequiredException, DatabaseUpdateException {
        File propsFile = new File(OpenmrsUtil.getApplicationDataDirectory(), "openmrs-runtime.properties");
        Properties props = new Properties();
        loadProperties(props, new FileInputStream(propsFile));
        startup("jdbc:mysql://localhost:3306/openmrs?autoReconnect=true", "openmrs", "password", props);
        try {
            openSession();
            authenticate("admin", "password");
            List<Patient> patients = getPatientService().getPatients("John");
            for (Patient patient : patients) {
                System.out.println("Found patient with name " + patient.getPersonName() + " and uuid: " + patient.getUuid());
            }
        } finally {
            closeSession();
        }
    }
}
