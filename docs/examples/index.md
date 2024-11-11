---
title: Examples
tags: [Java, Cryptography]
---
# Examples

## Asymmetric

### Encryption/Decryption

#### Generate Key Pair

```java title="Generate Asymmetric Key Pair"
--8<-- "src/test/java/org/example/cryptography/Utils/CryptoUtils.java:generate-asymmetric-key-values"
--8<-- "src/test/java/org/example/cryptography/Utils/CryptoUtils.java:generate-asymmetric-key"
```

1. Get the Key Generator `RSA` in Java crypto service provider (by default is `SunJCE`).
2. Initialize the Key Generator with the requested key size.

#### Default Mode

```java title="RSA Mode"
--8<-- "src/test/java/org/example/cryptography/asymmetric/RSAEncryptTest.java:rsa-encrypt-values"
```

```java title="RSA Encrypt"
--8<-- "src/test/java/org/example/cryptography/asymmetric/RSAEncryptTest.java:rsa-encrypt"
```

```java title="RSA Decrypt"
--8<-- "src/test/java/org/example/cryptography/asymmetric/RSAEncryptTest.java:rsa-decrypt"
```

#### OAP Mode

```java title="RSA Mode"
--8<-- "src/test/java/org/example/cryptography/asymmetric/RSAOAEPEncryptTest.java:rsa-encrypt-values"
```

```java title="RSA Encrypt"
--8<-- "src/test/java/org/example/cryptography/asymmetric/RSAOAEPEncryptTest.java:rsa-encrypt"
```

```java title="RSA Decrypt"
--8<-- "src/test/java/org/example/cryptography/asymmetric/RSAOAEPEncryptTest.java:rsa-decrypt"
```
