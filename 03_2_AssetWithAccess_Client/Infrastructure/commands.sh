#!/bin/bash
#
# Copyright Interticket
#
# INSTALL:
# 1. copy the gradle files and the scr files apart from test to the chaincode/02_JavaChaincodeAsset directory
# 2. login to
#    docker exec -it ca.example.com /bin/bash
#
# 3. or login to the cli container and execute the commands manually
#    docker exec -it cli /bin/bash

fabric-ca-client enroll -u http://visitor:visitorpw@localhost:7054 --tls.certfiles /etc/hyperledger/fabric-ca-server-config/ca.org1.example.com-cert.pem

fabric-ca-client register --id.name visitor --id.secret visitorpw --id.affiliation org1 --id.attrs 'hf.Revoker=true,AppRole=VISITOR,admin=true:ecert' -u http://localhost:7054 --tls.certfiles /etc/hyperledger/fabric-ca-server-config/ca.org1.example.com-cert.pem

fabric-ca-client register --id.name buyer --id.secret buyerpw --id.affiliation org1 --id.attrs 'hf.Revoker=true,AppRole=BUYER,admin=true:ecert' -u http://localhost:7054 --tls.certfiles /etc/hyperledger/fabric-ca-server-config/ca.org1.example.com-cert.pem

fabric-ca-client register --id.name owner --id.secret ownerpw --id.affiliation org1 --id.attrs 'hf.Revoker=true,AppRole=OWNER,admin=true:ecert' -u http://localhost:7054 --tls.certfiles /etc/hyperledger/fabric-ca-server-config/ca.org1.example.com-cert.pem

fabric-ca-client register --id.name builder --id.secret builderpw --id.affiliation org1 --id.attrs 'hf.Revoker=true,AppRole=BUILDER,admin=true:ecert' -u http://localhost:7054 --tls.certfiles /etc/hyperledger/fabric-ca-server-config/ca.org1.example.com-cert.pem

echo
echo "#####################################################"
echo "##### installing chaincode #########"
echo "#####################################################"
echo

peer chaincode install -l java -n mycc -v v1 -p /opt/gopath/src/github.com/03_AssetWithAccess

sleep 25

echo
echo "#####################################################"
echo "##### initializing chaincode #########"
echo "#####################################################"
echo

peer chaincode instantiate -o orderer.example.com:7050 -C mychannel -n mycc  -v v1 -c '{"Args":[]}' -P 'OR ("Org1MSP.member")'

sleep 120

echo
echo "#####################################################"
echo "##### test setHouse #########"
echo "#####################################################"
echo
echo "Test setHouse"
echo

peer chaincode invoke -o orderer.example.com:7050 --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C mychannel -n mycc --peerAddresses peer0.org1.example.com:7051 --tlsRootCertFiles /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt -c '{"Args":["setHouse","houseID123","2","testCountry","testCity","testStreet","4"]}'

echo
echo "#####################################################"
echo "##### test getHouse #########"
echo "#####################################################"
echo

peer chaincode query -C mychannel -n mycc -c '{"Args":["getHouse","houseID123"]}'

sleep 5

echo
echo "#####################################################"
echo "##### test buildNewRoom #########"
echo "#####################################################"
echo
echo "Test buildNewRoom"
echo

peer chaincode invoke -o orderer.example.com:7050 --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C mychannel -n mycc --peerAddresses peer0.org1.example.com:7051 --tlsRootCertFiles /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt -c '{"Args":["buildNewRoom","houseID123","4"]}'

echo
echo "#####################################################"
echo "##### test getHouse again #########"
echo "#####################################################"
echo

peer chaincode query -C mychannel -n mycc -c '{"Args":["getHouse","houseID123"]}'


