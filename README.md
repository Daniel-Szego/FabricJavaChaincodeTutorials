Chaincode examples for Java

1. HelloJavaChaincode: simplest Hello World java chaincode 

- copy the gradle files and the scr files apart from test to the chaincode/01_HelloJavaChaincode directory
- start a Hyperledger Fabric network (like fabric-samples/basic-network): pay attention if the cli drive mappings are valid
- run on the cli container
   docker exec -it cli /bin/bash
- install chaincode
   peer chaincode install -l java -n mycc -v v1 -p /opt/gopath/src/github.com/01_HelloJavaChaincode
- initiate chaincode
   peer chaincode instantiate -o orderer.example.com:7050 -C mychannel -n mycc  -v v1 -c '{"Args":["init","HelloWorld"]}' -P 'OR ("Org1MSP.member")'
- query hello world message
   peer chaincode query -C mychannel -n mycc -c '{"Args":["getHelloWorld"]}'
- change the hello world message
   peer chaincode invoke -o orderer.example.com:7050 --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C mychannel -n mycc --peerAddresses peer0.org1.example.com:7051 --tlsRootCertFiles /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt -c '{"Args":["setHelloWorldMessage", "HalloWelt"]}'
- query the changed hello world message

2. JavaChaincodeAsset: admiistrating a house on the blockchain

- copy the gradle files and the scr files apart from test to the chaincode/02_JavaChaincodeAsset directory
- start a Hyperledger Fabric network (like fabric-samples/basic-network): pay attention if the cli drive mappings are valid
- run on the cli container
   docker exec -it cli /bin/bash
- install chaincode
   peer chaincode install -l java -n mycc -v v1 -p /opt/gopath/src/github.com/02_JavaChaincodeAsset
- initiate chaincode
   peer chaincode instantiate -o orderer.example.com:7050 -C mychannel -n mycc  -v v1 -c '{"Args":[]}' -P 'OR ("Org1MSP.member")'
- create new house
   peer chaincode invoke -o orderer.example.com:7050 --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C mychannel -n mycc --peerAddresses peer0.org1.example.com:7051 --tlsRootCertFiles /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt -c '{"Args":["setHouse","houseID123","2","testCountry","testCity","testStreet","4"]}'
- read the house
   peer chaincode query -C mychannel -n mycc -c '{"Args":["getHouse","houseID123"]}'
- update the number of rooms for the house
   peer chaincode invoke -o orderer.example.com:7050 --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C mychannel -n mycc --peerAddresses peer0.org1.example.com:7051 --tlsRootCertFiles /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt -c '{"Args":["buildNewRoom","houseID123","4"]}'
- read the modfied house
   peer chaincode query -C mychannel -n mycc -c '{"Args":["getHouse","houseID123"]}'







