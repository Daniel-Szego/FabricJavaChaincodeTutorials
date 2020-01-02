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
public class JavaChaincodeAssetTest {

    @Mock
    private ChaincodeStub chaincodeStub;
    private JavaChaincodeAsset helloJavaChaincode = new JavaChaincodeAsset();

    @Test
    public void testGetMessage() {
        //given
        given(chaincodeStub.getStringState("msgKeyId123")).willReturn("Hello World !");

        //given
        String functionName = "getHelloWorld";
        given(chaincodeStub.getFunction()).willReturn(functionName);

        //when
        Chaincode.Response result = helloJavaChaincode.invoke(chaincodeStub);

        //then
        assertThat(result.getStatusCode()).isEqualTo(200);
        assertThat(result.getStringPayload()).contains("Hello World ");
    }

    @Test
    public void testSetMessage() {
        //given
        given(chaincodeStub.getStringState("msgKeyId123")).willReturn("Hello World !");

        //given
        String functionName = "setHelloWorldMessage";
        given(chaincodeStub.getFunction()).willReturn(functionName);
        given(chaincodeStub.getParameters()).willReturn(Arrays.asList("Hallo Welt"));

        //when
        Chaincode.Response result = helloJavaChaincode.invoke(chaincodeStub);

        //then
        assertThat(result.getStatusCode()).isEqualTo(200);
    }

}
