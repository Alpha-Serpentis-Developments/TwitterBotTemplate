# Twitter Bot Template

This is a template for a very basic Twitter bot that uses OAuth 2.0 to be able to send tweets on another
account's behalf.

## Requirements
- Java 17 or higher
- Twitter Developer Account
  - You can get one [here](https://developer.twitter.com)

## Setup

### SSL Certificate
This template temporarily spins up a webserver that listens for a callback from Twitter and reads the URL parameters
for a `code` parameter. This code is then used to request an access token from Twitter.

For security purposes, this webserver uses SSL and requires a certificate. You can generate a self-signed one or obtain
a certificate from another source.

If you have both `.pem` and `.key` files present, you will need to convert those to a `.p12` file using the following
command:

```shell
openssl pkcs12 -export -out [name of export].p12 -inkey [key] -in [crt]
```

You will need to specify a passphrase for the `.p12` file. This will be used for the `CERT_PASSPHRASE` environment
variable.

### Environment Variables
- `API_KEY`
  - Listed under "Keys and tokens" -> "Consumer Keys" on the Twitter Developer Portal
- `API_SECRET_KEY`
  - Listed under "Keys and tokens" -> "Consumer Keys" on the Twitter Developer Portal
- `CERT_PASSPHRASE`
  - The passphrase you used to encrypt the `.p12` file containing your SSL certificate
- `CERT_PATH`
  - The path to the `.p12` file containing your SSL certificate
- `CLIENT_ID`
  - Listed under "Keys and tokens" -> "OAuth 2.0 Client ID and Client Secret" on the Twitter Developer Portal
- `CLIENT_SECRET`
  - Listed under "Keys and tokens" -> "OAuth 2.0 Client ID and Client Secret" on the Twitter Developer Portal
- `REDIRECT_URI`
  - The redirect URI you specified under "Settings" -> "User Authentication Settings" on the Twitter Developer Portal.
  Ensure the port is added to the redirect URI in both the environment variable and on the Developer Portal.
