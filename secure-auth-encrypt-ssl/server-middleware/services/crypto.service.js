const CryptoJS = require('crypto-js');
const { mapValues } = require('lodash');

class CryptoService {
  secretKey = process.env.CRYPTO_SECRET;

  encryptValue(value) {
    if (!value) return null;
    return CryptoJS.AES.encrypt(value, this.secretKey, { mode: CryptoJS.mode.CBC }).toString();
  }

  decryptValue(value) {
    if (!value) return null;
    return CryptoJS.AES.decrypt(value, this.secretKey).toString(CryptoJS.enc.Utf8);
  }

  encryptObject(object, fields = []) {
    return mapValues(object, (value, key) => {
      return fields.includes(key) ? this.encryptValue(value) : value;
    });
  }

  decryptObject(object, fields = []) {
    return mapValues(object, (value, key) => {
      return fields.includes(key) ? this.decryptValue(value) : value;
    });
  }
}

module.exports = {
  CryptoService
};
