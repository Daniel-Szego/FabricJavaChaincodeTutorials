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

        return newSuccessResponse("Chaincode has been successfully initialized");
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
                return setHouse(stub, params);
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

        String houseId = args.get(0);
        String houseJSONString = stub.getStringState(houseId);
        AssetHouse house = AssetHouse.createAssetHouse(houseJSONString);

        return newSuccessResponse("House : ", house.toJSON().toString().getBytes());
    }

    private Response setHouse(ChaincodeStub stub, List<String> args) {

        if (args.size() != 6) {
            return newErrorResponse("Incorrect number of arguments. Expecting 6");
        }

        String houseId = args.get(0);
        Integer nrOfRooms = Integer.parseInt(args.get(1));
        String addressCountry = args.get(2);
        String addressCity = args.get(3);
        String addressStreet = args.get(4);
        Integer streetNr = Integer.parseInt(args.get(5));

        AssetHouse house = new AssetHouse();
        house.houseId = houseId;
        house.nrOfRooms = nrOfRooms;
        house.addressCountry = addressCountry;
        house.addressCity = addressCity;
        house.addressStreet = addressStreet;
        house.streetNr = streetNr;

        stub.putStringState(houseId,house.toJSON());

        return newSuccessResponse("House object has been succesfully saved", house.toJSON().getBytes());
    }

    public static void main(String[] args) {
        new org.hyperledger.fabric.example.HelloChaincode.JavaChaincodeAsset().start(args);
    }

}