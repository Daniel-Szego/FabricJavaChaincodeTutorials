package org.hyperledger.fabric.example.HelloChaincode;

import java.util.List;

import com.google.protobuf.ByteString;
import io.netty.handler.ssl.OpenSsl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JavaChaincodeAsset extends ChaincodeBase {

    private static final String MESSAGEKEYID = "msgKeyId123";

    @Override
    public Response init(ChaincodeStub stub) {
        try {

            String func = stub.getFunction();

            if (!func.equals("init")) {
                return newErrorResponse("function other than init is not supported");
            }
            List<String> args = stub.getParameters();
            if (args.size() != 1) {
                newErrorResponse("Incorrect number of arguments. Expecting 1");
            }

            // Initialize the chaincode
            String helloWorldMessage = args.get(0);

            stub.putStringState(MESSAGEKEYID, helloWorldMessage);

            return newSuccessResponse();
        } catch (Throwable e) {
            return newErrorResponse(e);
        }
    }

    @Override
    public Response invoke(ChaincodeStub stub) {
        try {

            // getting function name
            String func = stub.getFunction();

            // getting function parameters
            List<String> params = stub.getParameters();

            if (func.equals("getHelloWorld")) {
                return getHelloWorld(stub, params);
            }
            if (func.equals("setHelloWorldMessage")) {
                return setHelloWorldMessage(stub, params);
            }

            return newErrorResponse("Invalid invoke function name. Expecting one of: [\"getHelloWorld\", \"setHelloWorldMessage\"]");
        } catch (Throwable e) {
            return newErrorResponse(e);
        }
    }

    private Response getHelloWorld(ChaincodeStub stub, List<String> args) {
        if (args.size() != 0) {
            return newErrorResponse("Incorrect number of arguments. Expecting 0");
        }

        String helloWorldMessage = stub.getStringState(MESSAGEKEYID);

        return newSuccessResponse(helloWorldMessage, ByteString.copyFrom(helloWorldMessage, UTF_8).toByteArray());
    }

    private Response setHelloWorldMessage(ChaincodeStub stub, List<String> args) {

        if (args.size() != 1) {
            return newErrorResponse("Incorrect number of arguments. Expecting 1");
        }

        String newMessage = args.get(0);

        stub.putStringState(MESSAGEKEYID, newMessage);

        return newSuccessResponse("Hello World message sucessfully set", newMessage.getBytes());
    }

    public static void main(String[] args) {
        new org.hyperledger.fabric.example.HelloChaincode.JavaChaincodeAsset().start(args);
    }

}