package AssetWithAccess_Client;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Properties;


public class TestAdminConsole {

    public static void main(String[] args) {

        String caUrl = "http://localhost:7074"; // ensure that port is of CA
        String caName = "org1CA";

        Properties properties = new Properties();

        try {

            // create HFCAClient
            HFCAClient hfcaClient = HFCAClient.createNewInstance(caName, caUrl, properties);

            // add cryptoclass
            CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
            hfcaClient.setCryptoSuite(cryptoSuite);

            // set UserContext
            UserContext adminUserContext = new UserContext();
            adminUserContext.setName("admin"); // admin username
            adminUserContext.setAffiliation("org1"); // affiliation
            adminUserContext.setMspId("org1"); // org1 mspid

            // do enrolment
            Enrollment adminEnrollment = hfcaClient.enroll("admin", "xxxxxxx"); //pass admin username and password
            adminUserContext.setEnrollment(adminEnrollment);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (CryptoException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (org.hyperledger.fabric.sdk.exception.InvalidArgumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (EnrollmentException e) {
            e.printStackTrace();
        }
    }

}