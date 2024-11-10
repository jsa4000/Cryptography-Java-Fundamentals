---
title: Asymmetric
tags:
  - asymmetric
---
# Asymmetric Encryption

Before introducing the **asymmetric key encryption** schemes and algorithms, we should first understand the concept of **public key cryptography** (asymmetric cryptography).

The [**public key cryptography**](https://en.wikipedia.org/wiki/Public-key\_cryptography) uses a different key to **encrypt** and **decrypt** data (or to sign and verify messages). Keys always come as **public + private key pairs**. **Asymmetric cryptography** deals with **encrypting** and **decrypting** messages using a public / private key, **signing** messages, **verifying** signatures and securely **exchanging keys**.

![asymmetric-encryption](../assets/asymmetric-encryption-basics.png)

Popular **public-key cryptosystems** (asymmetric crypto algorithms) like **RSA** (Rivest–Shamir–Adleman), **ECC** (elliptic curve cryptography), **Diffie-Hellman**, **ECDH**, **ECDSA** and **EdDSA**, are widely used in the modern cryptography and we shall demonstrate most of them in practice with code examples.

![asymmetric-encryption](../assets/asymmetric-encryption-algorithms.png)

**Asymmetric encryption** is slower and less efficient than symmetric encryption, but it is more secure for certain applications, such as secure communication over the internet.

![asymmetric-encryption](../assets/asymmetric-encryption-proscons.png)

## Key Pairs

**Asymmetric encryption schemes** use a pair of cryptographically related **public** and **private keys** to **encrypt** the data (by the public key) and **decrypt** the encrypted data back to its original forms (by the private key). Data **encrypted by a public key** is **decrypted** by the corresponding **private key**.

The encrypted data, obtained as result of encryption is called *ciphertext*. The *ciphertext* is a binary sequence, unreadable by humans and by design cannot be decrypted without the decryption key.

!!! note

    Public key encryption can work also in the opposite scenario: **encrypt data by a private key and decrypt it by the public key**. Thus someone can prove that he is owner of certain private key, while revealing only its corresponding public key. This approach is used by some digital signature schemes.

In some public key cryptosystems (like the Elliptic-Curve Cryptography - **ECC**), the public key can be calculated from the private key. In other cryptosystems (like **RSA**), the public key and the private key are generated together but cannot be directly calculated from each other.

Usually, a **public / private key pair** is randomly generated in a secure environment (e.g. in a hardware wallet) and the public key is revealed, while the private key is securely stored in a crypto-wallet and is protected by a password or by multi-factor authentication.

**Example** of 256-bit private key and its corresponding 256-bit public key (based on **secp256k1** curve):

```
privKey: 648fc1fa828c7f185d825c04a5b21af9e473b867eeee1acea4dbab938433e158
pubKey: 02c324648931b89e3e8a0fc42c96e8e3be2e42812986573a40d46563bceaf75110
```

### Private Keys

Message **decryption** and **signing** is done by a **private key**. The private keys are always kept **secret** by their owner, just like passwords. In the server infrastructure, private key usually stay in an encrypted and protected **keystore**. In the blockchain systems the private keys usually stay in specific software or hardware apps or devices called "**crypto wallets**", which store securely a set of private keys.

**Example** of 256-bit private key:

```
648fc1fa828c7f185d825c04a5b21af9e473b867eeee1acea4dbab938433e158
```

### Public Keys

Message **encryption** and **signature verification** is done by the **public key**. Public keys are by design public information (not a secret). It is mathematically infeasible to calculate the private key from its corresponding public key.

In many systems the **public key** is encapsulated in a **digital certificate**, which binds certain identity (e.g. person or Internet domain name) to certain public key. In blockchain systems public keys are usually published as parts of the blockchain transactions to help identify who has signed each transaction. In systems like PGP and SSH the public key is downloaded from the server once (after manual user verification) and is remembered for further use.

**Example** of 256-bit public key:

```
02c324648931b89e3e8a0fc42c96e8e3be2e42812986573a40d46563bceaf75110
```

In most blockchain systems the **blockchain address** is derived from the public key (by hashing and other transformations), so if you have someone's public key, you are assumed to have his blockchain address as well.

A certain **public key** can be connected to certain **person** or **organization** or is used anonymously. You can never know who is the owner of the private key, corresponding to certain public key, unless you have additional proof, e.g. a [**digital certificate**](https://en.wikipedia.org/wiki/Public\_key\_certificate).

## Cryptosystems

**Public key cryptosystems** provide mathematical framework and algorithms to generate public + private key pairs, to **sign**, **verify**, **encrypt** and **decrypt** messages and **exchange keys**, in a cryptographically secure way.

Well-known public-key cryptosystems are: [**RSA**](https://en.wikipedia.org/wiki/RSA\_\(cryptosystem\)), [**ECC**](https://en.wikipedia.org/wiki/Elliptic-curve\_cryptography) and [**ElGamal**](https://en.wikipedia.org/wiki/ElGamal\_encryption). Many **crypto algorithms** are based on the primitives from these cryptosystems like **RSA sign**, **RSA encrypt / decrypt**, **ECDH** key exchange and **ECDSA** and **EdDSA** signatures.

### RSA

The [**RSA public-key cryptosystem**](https://en.wikipedia.org/wiki/RSA\_\(cryptosystem\)) is based on the math of **modular exponentiations** (numbers raised to a power by modulus) and some additional assumptions, together with the computational difficulty of the [**integer factorization problem**](https://en.wikipedia.org/wiki/RSA\_problem). We shall discuss it later in details, along with examples.

### ECC

The [**elliptic-curve cryptography (ECC) public-key cryptosystem**](https://en.wikipedia.org/wiki/Elliptic-curve\_cryptography) is based on the math of the on the algebraic structure of the **elliptic curves** over finite fields and the difficulty of the [**elliptic curve discrete logarithm problem (ECDLP)**](https://en.wikipedia.org/wiki/Elliptic-curve\_cryptography#Rationale). The **ECC** usually comes together with the [**ECDSA** algorithm](https://en.wikipedia.org/wiki/Elliptic\_Curve\_Digital\_Signature\_Algorithm) (elliptic-curve digital signature algorithm). We shall discuss ECC and ECDSA in details, along with examples.

**ECC uses smaller keys**, ciphertexts and signatures than RSA and is recommended for most applications. It is mathematically proven that a **3072-bit RSA key** has similar cryptographic strength to a **256-bit ECC key**. Key generation is in ECC is significantly faster than with RSA.

Due to the above reasons most blockchain networks (like Bitcoin and Ethereum) use elliptic-curve-based cryptography (ECC) to secure the transactions.

!!! warning

    Both **RSA** and **ECC** cryptosystems are **not quantum-safe**, which means that if someone has enough powerful quantum computer, he will be able to derive the private key from given public key in just few seconds.

## Encryption/Decryption

**Asymmetric encryption** works for **small messages** only (limited by the public / private key length). To encrypt larger messages [**key encapsulation mechanisms**](https://en.wikipedia.org/wiki/Key\_encapsulation) or other techniques can be used, which encrypt asymmetrically a random secret key, then use it to symmetrically encrypt the larger messages. In practice, modern **asymmetric encryption schemes** involve using a symmetric encryption algorithm together with a public-key cryptosystem, key encapsulation and message authentication.

Popular **asymmetric encryption schemes** are: [**RSA-OAEP**](https://en.wikipedia.org/wiki/Optimal\_asymmetric\_encryption\_padding) (based on RSA and OAEP padding), [**RSAES-PKCS1-v1\_5**](https://tools.ietf.org/html/rfc3447#section-7.2) (based on RSA and PKCS#1 v1.5 padding), [**DLIES**](https://en.wikipedia.org/wiki/Integrated\_Encryption\_Scheme) (based on discrete logarithms and symmetric encryption) and [**ECIES**](https://en.wikipedia.org/wiki/Integrated\_Encryption\_Scheme) (based on elliptic curve cryptography and symmetric encryption).

### Key encapsulation mechanism (KEM)

Typically, public-key crypto systems can **encrypt messages of limited length** only and are **slower** than symmetric ciphers. For encrypting longer messages (e.g. PDF documents) usually a **public-key encryption scheme** (also known as **hybrid encryption scheme**) is used, which combines **symmetric** and **asymmetric encryption**

![asymmetric-encryption](../assets/asymmetric-encryption-usages.png)

[**Key encapsulation mechanism (KEM)**](https://en.wikipedia.org/wiki/Key\_encapsulation): encapsulate an asymmetrically-encrypted random (ephemeral) symmetric key and use symmetric algorithm for the data encryption.

* For the **encryption** a random symmetric key `sk` is generated, the message is symmetrically encrypted by `sk`, then `sk` is asymmetrically encrypted using the recipient's public key.
* For **decryption**, first the `sk` key is asymmetrically decrypted using the recipient's private key, then the ciphertext is decrypted symmetrically using `sk`.

### Asymmetric vs Symmetric

Symmetric and asymmetric encryption are two different types of encryption that are used in various applications. Symmetric is faster and more efficient, perfect for handling large amounts of data. On the other hand, asymmetric encoding offers greater security as parties do not need to exchange their secret keys.

!!! note

    Asymmetric encryption is less common than symmetric encryption, but it is growing in popularity. It is used in applications where security is critical, such as online banking and cryptocurrency. The most popular asymmetric algorithm is the RSA algorithm, and RSA 2048 is the strongest asymmetric algorithm available.

In order to decide wether using one type of another, here is a table with its main differences.

![asymmetric-encryption](../assets/asymmetric-vs-symmetric.png)

## Digital Signatures

In the context of [**digital signatures**](https://en.wikipedia.org/wiki/Digital\_signature), a message **signed by a private key** (digital signature) is later **verified** by the corresponding **public key**. Digital signatures provide message **authentication**, message **integrity** and **confidentiality**.

![asymmetric-encryption](../assets/asymmetric-digital-signatures.png)

[**Digital signatures**](digital-signatures/) in short: a message can be **signed** by certain **private key** and the obtained **signature** can be later **verified** by the corresponding **public key**. A **signed message** cannot be altered after signing. A message signature proves that certain message (e.g. blockchain transaction) is created by the owner of certain public key.

![asymmetric-encryption](../assets/asymmetric-digital-signatures-flow.png)

Digital signatures are widely used in the **finance industry** for authorizing payments. In **operating systems** OS components and device drivers are usually digitally signed to avoid injecting insecure code, trojans or viruses in the OS. In **blockchain systems**, transactions are typically signed by the owner of certain blockchain address (which corresponds to certain public key and has corresponding private key). So **a signed blockchain transaction holds a proof of authorship**: it is guaranteed mathematically that the signature is created by the holder of certain blockchain address and the transaction was not modified after the signing. This works perfectly for the scenario of **digital payments** and digital signing of documents and contracts.

![asymmetric-encryption](../assets/asymmetric-digital-signatures-usecase.png)

## Certificates
