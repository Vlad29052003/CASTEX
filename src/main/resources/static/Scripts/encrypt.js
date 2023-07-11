const RSA = new JSEncrypt({ default_key_size: 2048 });
let publicKey = "";

async function fetchPublicKey() {
    try {
        const response = await fetch(server + '/get-public-key'); // Replace with your server endpoint
        return await response.text();
    } catch (error) {
        throw error;
    }
}

async function fetchAndAssignPublicKey() {
    try {
        publicKey = await fetchPublicKey();
        RSA.setPublicKey(publicKey);
    } catch (error) {
        console.error('Failed to fetch public key:', error);
        throw error;
    }
}

function encrypt(data) {
    return RSA.encrypt(data);
}

function decrypt(data) {
    return RSA.decrypt(data);
}

function generateRSAKeyPair() {
    let encryptor = new JSEncrypt({ default_key_size: 2048 });
    encryptor.getKey();
    let publicKey = encryptor.getPublicKey();
    let privateKey = encryptor.getPrivateKey();
    RSA.setPrivateKey(privateKey);

    return publicKey;
}
