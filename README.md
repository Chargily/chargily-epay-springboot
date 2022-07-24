
# Chargily ePay Gateway Spring Boot


![Chargily ePay Gateway](https://raw.githubusercontent.com/Chargily/epay-gateway-php/main/assets/banner-1544x500.png "Chargily ePay Gateway")


Integrate ePayment gateway with Chargily easily.
- Currently support payment by **CIB / EDAHABIA** cards and soon by **Visa / Mastercard**
- This is a **Spring Boot jar**, If you are using another programing language [Browse here](https://github.com/Chargily/) or look to [API documentation](https://github.com/Chargily/epay-gateway-php/blob/master/README_API.md)


# Requirements
1. JDK 11 or later.
3. Spring Boot Version 2.5 or later
4. Get your API Key/Secret from [ePay by Chargily](https://epay.chargily.com.dz) dashboard for free

# Installation
1. Via Maven (Recomended)  
it will be published into mvn central soon.

2. Manually  
downlaod the jar file and add it as a library into your project
[ePay by Chargily](https://drive.google.com/file/d/1q7oGl1JonunyD7dr6VHWO79KrKn8otPs/view?usp=sharing)


# Quick start
Create an Invoice Object
```java

        Invoice invoice = new Invoice("client","client_email","invoice_number",
                500.0/*amount*/,25.0/*discount*/,"back_url","webhook_url", PaymentMode/*enum EDAHABIA,CIB*/,
                "comment");

```
Instantiate a ChargilyService via a constructor 
```java
ChargilyService chargilyService = new ChargilyService();
```
OR inject it into your class 
```java
public class example{

@Autowired
private ChargilyService chargilyService;

/*
  your code

*/
}
```
Start a payment
```java

ResponseEntity<ChargilyResponse> response = chargilyService.createPayment(invoiceObj,"APIKEY");
response.getBody().getCheckout_url();
```
Validate a signature
```java
if(chargilyService.isSignatureValid("signature","APISecretKey","ResponseData")){

    /*
    your coce 
    */
}
```


# Contribution tips
1. Make a fork of this repo.
2. Take a tour to our [API documentation here](http://dev.codingdz.com/python-chargily-epay/)
3. Get your API Key/Secret from [ePay by Chargily](https://epay.chargily.com) dashboard for free.
4. Start developing.
5. Finished? Push and merge.

