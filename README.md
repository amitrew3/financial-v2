# walletsApp
gRPC, spring boot, hibernate

# How to run application
1. Fetch application
2. Build it
3. Create schema in mysql data base from file "walletsDBschema.sql"
4. In file "application.properties" change data base properties for your local data base properties
5. Run class "Application" this class will start server 
6. Run class "GrpcClient" this class will start client with default settings:
            countOfUserSessions = 1;
            countOfRequestsPerUser = 3;
            countOfRoundsPerUser = 3;
 If you want to run client with your own settings run it with cli.
 Where 1 param - countOfUserSessions; 2 param - countOfRequestsPerUser; 3 param - countOfRoundsPerUser;
 
Explanation of important choices in solution
1. To acces DB we use hibernate with transactions
2. Server and Client - it is monolit application
3. grpc proto files, repositiries, services and client actions are organized by interfaces "Deposit", "Withdraw", "Balance"


