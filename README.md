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
