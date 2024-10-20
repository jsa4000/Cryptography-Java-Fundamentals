---
title: Symmetric
tags:
  - symmetric
---
# Symmetric Encryption

**Symmetric encryption** schemes use **the same symmetric key** (or password) to **encrypt** data and **decrypt** the encrypted data back to its original form:

![](.gitbook/assets/symmetric-encryption.png)

Symmetric encryption usually combines several crypto algorithms into an **symmetric encryption scheme**, e.g. AES-256-CTR-HMAC-SHA256. The **encryption scheme** (cipher construction) may include: password to **key derivation** algorithm (with certain parameters) + **symmetric cipher** algorithm (with certain parameters) + **cipher block mode** algorithm + **message authentication** (MAC) algorithm. This means that **the above shown diagram is simplified** and does not fully represent the process.

## Secret Keys

The **secret key** used to **cipher** (encrypt) and **decipher** (decrypt) data is typically of size 128, 192 or 256 bits and is sometimes referred as "_**encryption key**_" or "_**shared key**_", because both sending and receiving parties should know it.

Most applications use a [**password-to-key-derivation**](https://github.com/svetlin-nakov/practical-cryptography-for-developers/tree/34666e7576d7427e08bf3940a69214b0cda94676/content/part-1-blockchain-networks-concepts/blockchain-cryptography/blockchain-cryptography-overview/hmac-and-key-derivation.html) scheme to extract a **secret key** from certain **password**, because users tend to remember passwords easier than binary data. Additionally, **message authentication** is often incorporated along with the encryption to provide **integrity** and **authenticity** (this encryption approach is known as "[**authenticated encryption**](https://en.wikipedia.org/wiki/Authenticated\_encryption)").

How does a **private key** look like? Let's start from a simple example of **256-bit secret key**, encoded as [**hex string**](https://en.wikipedia.org/wiki/Hexadecimal):

```
02c324648931b89e3e8a0fc42c96e8e3be2e42812986573a40d46563bceaf75110
```

In many systems (e.g. public blockchains, PGP, OpenSSL and others) secret keys are encoded as [**base58**](https://en.wikipedia.org/wiki/Base58) or [**base64**](https://en.wikipedia.org/wiki/Base64) for shorter string representation.

For example, the above key looks like this in **base58**:

```
pbPRqYDxnKZfs8j4KKiqYmx6nzipAjTJf1oCD1WKgy99
```

The same key looks like this in **base64**:

```
AsMkZIkxuJ4+ig/ELJbo474uQoEphlc6QNRlY7zq91EQ
```

In **decimal** system, the above key is the following integer number:

```
319849484316084980661994213716306415989897600164422912728298459349458028548368
```

## Modern Symmetric Encryption Algorithms

Widely used in modern cryptography **symmetric encryption algorithms** (ciphers) are: [**AES**](https://en.wikipedia.org/wiki/Advanced\_Encryption\_Standard) (AES-128, AES-192, AES-256), [**ChaCha20**](https://legacy.gitbook.com/book/svetlin-nakov/practical-blockchain-for-developers-the-big-book/edit), [**Twofish**](https://en.wikipedia.org/wiki/Twofish), [**IDEA**](https://en.wikipedia.org/wiki/International\_Data\_Encryption\_Algorithm), [**Serpent**](https://en.wikipedia.org/wiki/Serpent\_\(cipher\)), [**Camelia**](https://en.wikipedia.org/wiki/Camellia\_\(cipher\)) and others. Most of them are **block ciphers** (encrypt data by blocks of fixed size, e.g. 128 bits), while others are **stream ciphers** (encrypt data byte by byte as a stream). Block ciphers can be turned into stream ciphers by using a technique called "**cipher block mode**".

We shall give more details and code examples using the **AES** and **ChaCha20** algorithms a bit later.

## Symmetric Encryption - Online Demo

In order to better understand the idea behind the symmetric encryption, you can play with some **online symmetric encryption tool** to encrypt and decrypt a sample message by sample secret key (or password). You can play a bit with this site: [**https://aesencryption.net**](https://aesencryption.net).

![](.gitbook/assets/aesencryption.net.png)

It demonstrates how we can encrypt and decrypt messages, using the **AES cipher** (with certain settings, more precisely AES-128-CBC) and certain password-to-key-derivation function. In the above example if we encrypt the text "_**secret msg**_" by the password "_**p@ss**_", we will get the [base64-encoded](https://en.wikipedia.org/wiki/Base64) binary data "_**jVJwOBmH+qMqHdg22KwMyg==**_" as output. After decryption with the same secret key we get back the original text "_**secret msg**_".

Note that the above encrypted text is dependent to many algorithm parameters and settings, so if you encrypt the same at another "_AES live example_" Web site, the result most likely will be different.

Also note that the above mentioned Web site uses bad practices, old algorithms and weak cipher settings, so don't follow the code examples there.
