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
    }

    @Override
    public Response invoke(ChaincodeStub stub) {
        try {

            // getting function name
            String func = stub.getFunction();

            // getting function parameters
            List<String> params = stub.getParameters();

            if (func.equals("getHouse")) {
                return getHouse(stub, params);
            }
            if (func.equals("setHouse")) {
                return setHelloWorldMessage(stub, params);
            }

            return newErrorResponse("Invalid invoke function name. Expecting one of: [\"getHouse\", \"setHouse\"]");
        } catch (Throwable e) {
            return newErrorResponse(e);
        }
    }

    private Response getHouse(ChaincodeStub stub, List<String> args) {
        if (args.size() != 1) {
            return newErrorResponse("Incorrect number of arguments. Expecting 1 the House Id");
        }

        String helloWorldMessage = stub.getStringState(MESSAGEKEYID);

        return newSuccessResponse(helloWorldMessage, ByteString.copyFrom(helloWorldMessage, UTF_8).toByteArray());
    }

    private Response setHouse(ChaincodeStub stub, List<String> args) {

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