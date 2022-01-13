# Camel
The following API returns mailpiece information:
* http://localhost:30000/api/
* http://localhost:30000/api/state/{id}

The sample shows how to use camel to create an adapter that orchestrates the API and publishes a mailpiece.

## Camel K
https://camel.apache.org/camel-k/1.7.x/

### Development
```
${CAMEL_HOME}/kamel run -d camel-http -d camel-jackson --dev mailpiece-adapter.groovy -n mailpiece
```
### Build
```
docker build --tag="mailpiece-api:latest" ./mailpiece-api
```
### Run
```
export CAMEL_HOME=/Users/chiodonia/Downloads/camel-k-client-1.7.0-mac-64bit

kubectl create namespace mailpiece

kubectl create -f 'https://strimzi.io/install/latest?namespace=mailpiece' -n mailpiece
kubectl apply -f ./kafka.yaml -n mailpiece
kubectl apply -f ./mailpiece-api/mailpiece-api.yml -n mailpiece

${CAMEL_HOME}/kamel install --registry docker.io --organization chiodoni --force --registry-auth-username chiodonia --registry-auth-password xxx --build-timeout 12h --cluster-type kubernetes --build-publish-strategy=Spectrum -n mailpiece

${CAMEL_HOME}/kamel run -d camel-http -d camel-jackson mailpiece-adapter.groovy -n mailpiece
```

### Others
```
kubectl get pods -n mailpiece
${CAMEL_HOME}/kamel get -n mailpiece 
${CAMEL_HOME}/kamel log mailpiece-adapter -n mailpiece
```
