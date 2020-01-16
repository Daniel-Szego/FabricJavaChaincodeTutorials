package org.hyperledger.fabric.example.HelloChaincode;

import org.assertj.core.api.AssertionsForClassTypes;
import org.hyperledger.fabric.shim.Chaincode;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class JavaChaincodeAssetWithAccessTest {

    @Mock
    private ChaincodeStub chaincodeStub;
    private JavaChaincodeAssetWithAccess helloJavaChaincode = new JavaChaincodeAssetWithAccess();

    @Test
    public void testGetHouse() {
        //given
        given(chaincodeStub.getStringState("houseID123")).willReturn("{houseId:houseID123,nrOfRooms:2,addressCountry:demoSCountry,addressCity:demoCity,addressStreet:demoStreet,streetNr:22}");

        //given
        String functionName = "getHouse";
        given(chaincodeStub.getParameters()).willReturn(Arrays.asList("houseID123"));
        given(chaincodeStub.getFunction()).willReturn(functionName);

        //when
        Chaincode.Response result = helloJavaChaincode.invoke(chaincodeStub);

        //then
        assertThat(result.getStatusCode()).isEqualTo(200);
        assertThat(result.getStringPayload()).contains("houseID123");
        assertThat(result.getStringPayload()).contains("demoCity");
        assertThat(result.getStringPayload()).contains("demoSCountry");
        assertThat(result.getStringPayload()).contains("demoStreet");
    }

    @Test
    public void testSetHouse() {
        //given

        //given
        String functionName = "setHouse";
        given(chaincodeStub.getFunction()).willReturn(functionName);
        given(chaincodeStub.getParameters()).willReturn(Arrays.asList("houseID123","2","testCountry","testCity","testStreet","4"));

        //when
        Chaincode.Response result = helloJavaChaincode.invoke(chaincodeStub);

        //then
        assertThat(result.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void testBuildNewRoom() {
        //given
        given(chaincodeStub.getStringState("houseID123")).willReturn("{houseId:houseID123,nrOfRooms:2,addressCountry:demoSCountry,addressCity:demoCity,addressStreet:demoStreet,streetNr:22}");

        //given
        String functionName = "buildNewRoom";
        given(chaincodeStub.getFunction()).willReturn(functionName);
        given(chaincodeStub.getParameters()).willReturn(Arrays.asList("houseID123","4"));

        //when
        Chaincode.Response result = helloJavaChaincode.invoke(chaincodeStub);

        //then
        assertThat(result.getStatusCode()).isEqualTo(200);
    }

}
