const RSAEncrypt = new JSEncrypt();
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
        RSAEncrypt.setPublicKey(publicKey);
    } catch (error) {
        console.error('Failed to fetch public key:', error);
        throw error;
    }
}

function encrypt(data) {
    return RSAEncrypt.encrypt(data);
}

function generateRSAKeyPair() {
    let encrypt = new JSEncrypt({ default_key_size: 2048 });
    encrypt.getKey();
    let publicKey = encrypt.getPublicKey();
    let privateKey = encrypt.getPrivateKey();
    return {
        publicKey: publicKey,
        privateKey: privateKey
    };
}
