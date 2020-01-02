#!/bin/bash
#
# Copyright Interticket
#
# INSTALL:
# 1. copy the gradle files and the scr files apart from test to the chaincode/01_HelloJavaChaincode directory
# 2. run on the cli container, with either
#    docker exec cli scripts/balancetrackerinit.sh
# 3. or login to the cli container and execute the commands manually
#    docker exec -it cli /bin/bash

echo
echo "#####################################################"
echo "##### installing chaincode #########"
echo "#####################################################"
echo

peer chaincode install -l java -n mycc -v v1 -p /opt/gopath/src/github.com/01_HelloJavaChaincode

sleep 25

echo
echo "#####################################################"
echo "##### initializing chaincode #########"
echo "#####################################################"
echo

peer chaincode instantiate -o orderer.example.com:7050 -C mychannel -n mycc  -v v1 -c '{"Args":["init","HelloWorld"]}' -P 'OR ("Org1MSP.member")'

sleep 120

echo
echo "#####################################################"
echo "##### test getHelloWorld #########"
echo "#####################################################"
echo

peer chaincode query -C mychannel -n mycc -c '{"Args":["getHelloWorld"]}'

sleep 5


echo
echo "#####################################################"
echo "##### test setHelloWorldMessage #########"
echo "#####################################################"
echo
echo "Test setHelloWorldMessag"
echo

peer chaincode invoke -o orderer.example.com:7050 --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C mychannel -n mycc --peerAddresses peer0.org1.example.com:7051 --tlsRootCertFiles /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt -c '{"Args":["setHelloWorldMessage", "HalloWelt"]}'

echo "Test getHelloWorld"

peer chaincode query -C mychannel -n mycc -c '{"Args":["getHelloWorld"]}'


