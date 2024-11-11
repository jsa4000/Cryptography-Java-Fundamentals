---
title: Source
tags: [Java, Cryptography]
---
# Source

## Hashing

### SHA-256

```java title="AES Mode"
--8<-- "src/test/java/org/example/cryptography/hashing/HashTest.java:crypto-values"
```

```java title="AES Encrypt"
--8<-- "src/test/java/org/example/cryptography/hashing/HashTest.java:hash"
```

1. Compute the **Hash** directly using `SHA-256` algorithm.
2. Apply a _**Salt**_ to data **before** computing the **Hash**.
3. Compute the **Hash** using `SHA-256` algorithm.

### PBKDF2

```java title="AES Mode"
--8<-- "src/test/java/org/example/cryptography/hashing/PBKDF2Test.java:crypto-values"
```

```java title="AES Encrypt"
--8<-- "src/test/java/org/example/cryptography/hashing/PBKDF2Test.java:hash"
```

### HMAC

```java title="AES Mode"
--8<-- "src/test/java/org/example/cryptography/hashing/HMACTest.java:crypto-values"
```

```java title="AES Encrypt"
--8<-- "src/test/java/org/example/cryptography/hashing/HMACTest.java:hash"
```

## Symmetric Key

```java title="Symmetric Key"
--8<-- "src/test/java/org/example/cryptography/Utils/CryptoUtils.java:generate-symmetric-key-values"
--8<-- "src/test/java/org/example/cryptography/Utils/CryptoUtils.java:generate-symmetric-key"
--8<-- "src/test/java/org/example/cryptography/Utils/CryptoUtils.java:generate-symmetric-iv"
```

### AES/EBC Mode

```java title="AES Mode"
--8<-- "src/test/java/org/example/cryptography/symmetric/AESECBTest.java:crypto-values"
```

```java title="AES Encrypt"
--8<-- "src/test/java/org/example/cryptography/symmetric/AESECBTest.java:encrypt"
```

```java title="AES Decrypt"
--8<-- "src/test/java/org/example/cryptography/symmetric/AESECBTest.java:decrypt"
```

### AES/CBC Mode

```java title="AES Mode"
--8<-- "src/test/java/org/example/cryptography/symmetric/AESCBCTest.java:crypto-values"
```

```java title="AES Encrypt"
--8<-- "src/test/java/org/example/cryptography/symmetric/AESCBCTest.java:encrypt"
```

```java title="AES Decrypt"
--8<-- "src/test/java/org/example/cryptography/symmetric/AESCBCTest.java:decrypt"
```

### AES/GCM Mode

```java title="AES Mode"
--8<-- "src/test/java/org/example/cryptography/symmetric/AESGCMTest.java:crypto-values"
```

```java title="AES Encrypt"
--8<-- "src/test/java/org/example/cryptography/symmetric/AESGCMTest.java:encrypt"
```

```java title="AES Decrypt"
--8<-- "src/test/java/org/example/cryptography/symmetric/AESGCMTest.java:decrypt"
```

## Asymmetric

### Encryption/Decryption

#### Generate Key Pair

```java title="Asymmetric Key Pair"
--8<-- "src/test/java/org/example/cryptography/Utils/CryptoUtils.java:generate-asymmetric-key-values"
--8<-- "src/test/java/org/example/cryptography/Utils/CryptoUtils.java:generate-asymmetric-key"
```

1. Get the Key Generator `RSA` in Java crypto service provider (by default is `SunJCE`).
2. Initialize the Key Generator with the requested key size.

#### Default Mode

```java title="RSA Mode"
--8<-- "src/test/java/org/example/cryptography/asymmetric/RSAEncryptTest.java:crypto-values"
```

```java title="RSA Encrypt"
--8<-- "src/test/java/org/example/cryptography/asymmetric/RSAEncryptTest.java:encrypt"
```

```java title="RSA Decrypt"
--8<-- "src/test/java/org/example/cryptography/asymmetric/RSAEncryptTest.java:decrypt"
```

#### OAP Mode

```java title="RSA Mode"
--8<-- "src/test/java/org/example/cryptography/asymmetric/RSAOAEPEncryptTest.java:crypto-values"
```

```java title="RSA Encrypt"
--8<-- "src/test/java/org/example/cryptography/asymmetric/RSAOAEPEncryptTest.java:encrypt"
```

```java title="RSA Decrypt"
--8<-- "src/test/java/org/example/cryptography/asymmetric/RSAOAEPEncryptTest.java:decrypt"
```

### Digital Signature

```java title="RSA Mode"
--8<-- "src/test/java/org/example/cryptography/asymmetric/RSASignTest.java:crypto-values"
```

```java title="RSA Sign"
--8<-- "src/test/java/org/example/cryptography/asymmetric/RSASignTest.java:sign"
```

```java title="RSA Validate"
--8<-- "src/test/java/org/example/cryptography/asymmetric/RSASignTest.java:validate"
```
