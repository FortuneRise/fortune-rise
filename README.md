# FortuneRise

FortuneRise is an online gambling app designed for individuals who enjoy testing their luck and increasing their fortune. The app offers a seamless user experience focused on roulette, with opportunities for users to deposit money in multiple currencies, track their game and transaction history, and receive notifications and promotions. FortuneRise combines simplicity with functionality, allowing players to enjoy their gaming experience while keeping an eye on their progress.

## Features:
- **Roulette**: The app offers an engaging roulette game where players can place bets and spin the wheel to win big.
- **Multi-Currency Deposits**: Players can deposit money in a variety of currencies, providing flexibility for international users.
- **Game and Transaction History**: Users can view their detailed game and transaction history, keeping track of past plays and financial activities.
- **Notifications and Promotions**: The app features a notification system that keeps players informed about promotions, bonuses, and other relevant updates to enhance their experience.

## Installation

1. **API Key**  
   You need an API key for this service: [Exchangerate API](https://www.exchangerate-api.com/).  

2. **Create `.env` file**  
   In the root directory, create a `.env` file with the following entry:
   ```dotenv
   API_KEY=<your_exchangerate_api_key>

3. **Connect to AKS Kubernetes Cluster**
  Ensure you're connected to your AKS (Azure Kubernetes Service) cluster.

4. **Run the commands**
  Execute the following commands to create the necessary Kubernetes resources:

    kubectl create configmap wallet-config --from-env-file=.env --dry-run=client -o yaml > wallet-config.yaml
    kubectl apply -f ./kubernetes

    Access the Frontend
    The frontend will be available at the IP address provided by AKS.

For more details or assistance, feel free to reach out!

## Microservices
1. **User microservice**
   This microservice is used to create new user accounts, and view existing ones.
   It implements three endpoints:
      - **GET /users** which returns the list of all users.
      - **GET /users/{userId}** which returns a user with specified id.
      - **POST / users** which adds a new user.

      All of the above methods use the folowing JSON object for transfering data.
     ```dotenv

     {
        "id": IdNumber,
        "name": "YourName",
       "surname": "YourSurname",
       "username": "YourUsername"
     }

2. **Wallet microservice**
      This microservice is used to create new wallets and update existing ones with transactions. All walets store their current amount in USD.
      It implements three endpoints:
      - **GET /wallets/{userId}** which returns a wallet of the specified user.
      - **POST /wallets/{userId}** which is used to create a new wallet fo a user with the specified id. This                                        endpoint shouldn't be called outside of the user microservice.
      - **PUT /wallets/{userId}** which updates the wallet with the amount in USD corresponding to the amount in the specified currency. It also calls the history microservice which saves the transaction.
   
   The GET and POST methods both return the following JSON object.
         ```dotenv
            {
                 "balance": currentBalance
            }
         ```


   The PUT method requires the following JSON object
            ```dotenv
   
           {
             "amount": Amount,
             "currency" : "SomeCurrency" 
           }

3. **Notification microservice**
     This miroservice is used to create notifications for users.
      It implements three endpoints:
      - **GET /notifications/{userId}** which returns all the notifications for a specified user.
      - **PUT /notifications/{userId}/{notification}** which marks the specified notification as read.
      - **POST /notifications/{userId}** which creates a new notification for a user.

      All of the above methods use the folowing JSON object for transfering data.
     ```dotenv
     {
        "id": IdNUmber,
        "read": boolRead,
        "content": "YourContent",
        "date": DateOfCreation
     }
     ```
4. **Game microservice**
      This microservice is used to play the roulette.
      It implements only one endpoint:
      -**POST /games/{userId}** which is used to create a new game. It also cals history microservice to save the game and wallet microservice to handle the transaction for winning or losing
      This method accepts a list of JSON objects.
      ```dotenv
      {
         "type": "Type of the bet"
         "betAmount": AmountToBet
         "field": [fieldNumbers]
      }
      ```
   Where `type` is one of `STRAIGHT, SPLIT, STREET, CORNER, SIX_LINE, COLOR, PARITY, COLUMN, DOZEN, HIGH_LOW`.
   And the fields attribute is a list of chosen field or in cases of:
- `COLOR:`: 0 represents `RED` and 1 represents `BLACK`.
-  `PARITY`: 0 represents `EVEN` and 1 represents `ODD`.
-  `COLUMN`: 0 represents `First Column`, 1 represents `Second Column` and 2 represents `Third Column`.
-  `DOZEN`: 0 represents `First Twelve`, 1 represents `Second Twelve` and 2 represents `Third Twelve`.
-  `HIGH_LOW`: 0 represents `First Eighteen` and 1 represents `Second Eighteen`.
  The method returns the following JSON object.
    ```dotenv
      {
        "gameId": GameId,
        "payout": PayoutAmount,
        "roll": GameRoll,
        "date": DatePlayed,
        "userId": UserWhoPlayed,
        "bets": null
       }
    ```

   Where the `bets` field is always `null`.
4. **History microservice**
   This microservice is used to store games and transactions.
   It implements the following methods:
   -**GET /history/games/{userId}** which returns all the games of a specified user
   -**GET /history/games/{gameId}/bets** which returns a list of all bets that were made in a specifieed game
   -**POST /history/games/{userId}** which adds a game to a specified user
      All of the above methods use the following JSON objects.
      ```dotenv
      {
        "gameId": GameId,
        "payout": PayoutAmount,
        "roll": GameRoll,
        "date": DatePlayed,
        "userId": UserWhoPlayed,
        "bets": ListOfBets
       }
    ```
      Where `bets` is a list of the following JSON object
      ```dotenv
      {
         "type": "Type of the bet"
         "betAmount": AmountToBet
         "field": [fieldNumbers]
      }
      ```
   -**GET /history/transactions/{userid}** which returns all of the users transactions
   -**POST /history/transactions/{userid}** which adds a transaction to a specified user
   Both methods use the following JSON object
   ```dotenv
   {
        "id": TransactionId,
        "amount": TransactionAmount,
        "walletId": WalletId,
        "currency": null,
        "userId": UserId
    }
   ```
   Where `currency` is always null because the transactions are made in USD

6.**Promotions microservice**
   This microservice is used to add, apply and use promotions
   It implements six endpoints:
   -**GET /promotions** which returns all promotions
   -**GET /promotions/{userId}/{triggerScenario}** which returns all promotions of a user based on the trigger scenario which can be one of `all, deposit, bet`.
   -**POST /promotions** which creates a new promotion
   -**POST /promotions/{userId}/{promotionId}** which adds a specified promotion to the specified user
   -**PUT /promotions/{userId}/{promotionId}** which uses the promotion and removes it from user
   All of the above methods use the following JSON object
   ```dotenv
      {
        "id": PromotionId;
        "triggerScenario": TriggerScenario;
        "type": TypeOfPromotion;
        "parameters": ListOfParameters;
      }

   ```
   
   **POST /promotions/{userId}/{promotionId}/verify** which in case of a `deposit` promotion verifies that the promotion is eligible
   This method uses
   ```dotenv
   {
        "id": TransactionId,
        "amount": TransactionAmount,
        "walletId": WalletId,
        "currency": null,
        "userId": UserId
    }
   ```
   
   
  
   
 
