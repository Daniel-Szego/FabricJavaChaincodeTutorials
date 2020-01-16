package org.hyperledger.fabric.example.HelloChaincode;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.List;

import org.hyperledger.fabric.contract.ClientIdentity;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.json.JSONException;


public class JavaChaincodeAssetWithAccess extends ChaincodeBase {

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
            if (func.equals("buildNewRoom")) {
                return buildNewRoom(stub, params);
            }

            return newErrorResponse("Invalid invoke function name. Expecting one of: [\"getHouse\", \"setHouse\"]");
        } catch (Throwable e) {
            return newErrorResponse(e);
        }
    }

    protected boolean isUserInRole(ChaincodeStub stub, String role) {
       try {

           ClientIdentity ident = new ClientIdentity(stub);
           return ident.assertAttributeValue("AppRole", role);

       } catch (IOException | CertificateException ex) {
           ex.printStackTrace();
           return false;
       }
    }

    private Response getHouse(ChaincodeStub stub, List<String> args) {
        if (args.size() != 1) {
            return newErrorResponse("Incorrect number of arguments. Expecting 1 the House Id");
        }
        if (isUserInRole(stub, AppRoleEnum.VISITOR.toString()) ||
                isUserInRole(stub, AppRoleEnum.BUYER.toString()) ||
                isUserInRole(stub, AppRoleEnum.OWNER.toString()) ||
                isUserInRole(stub, AppRoleEnum.BUILDER.toString())) {

            String houseId = args.get(0);
            String houseJSONString = stub.getStringState(houseId);
            AssetHouse house = AssetHouse.createAssetHouse(houseJSONString);

            return newSuccessResponse("House : ", house.toJSON().toString().getBytes());
        }
        else {
            return newErrorResponse("Invalid access for the resouce");
        }
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

        if (isUserInRole(stub, AppRoleEnum.OWNER.toString())) {

            stub.putStringState(houseId, house.toJSON());
            return newSuccessResponse("House object has been succesfully saved", house.toJSON().getBytes());
        } else {
            return newErrorResponse("Invalid access for the resouce: only owner has access to setHouse");
        }

    }

    private Response buildNewRoom(ChaincodeStub stub, List<String> args) {
        if (args.size() != 2) {
            return newErrorResponse("Incorrect number of arguments. Expecting 2 the House Id and the new number of rooms");
        }

        String houseId = args.get(0);
        Integer newNumberOfRooms = Integer.parseInt(args.get(1));

        if (isUserInRole(stub, AppRoleEnum.BUILDER.toString())) {


            String houseJSONString = stub.getStringState(houseId);
            AssetHouse house = AssetHouse.createAssetHouse(houseJSONString);

            house.nrOfRooms = newNumberOfRooms;
            stub.putStringState(houseId, house.toJSON());

            return newSuccessResponse("House : ", house.toJSON().toString().getBytes());
        } else {
            return newErrorResponse("Invalid access for the resouce: only builder has access to buildNewRoom");
        }
    }

    public static void main(String[] args) {
        new org.hyperledger.fabric.example.HelloChaincode.JavaChaincodeAssetWithAccess().start(args);
    }

}