# FortuneRise

FortuneRise is an online gambling app designed for those seeking to increase their fortune.

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
