import CryptoJS from 'crypto-js';

const mongoose = require('mongoose');
const { CryptoService } = require('../services/crypto.service');
const Schema = mongoose.Schema;
const userEncryptFields = [
  'email',
  'phone',
  'nickname',
  'phonePrefix',
  'passwordHash',
];
const generatedFields = {};
for (const fieldName of userEncryptFields) {
  generatedFields[fieldName + '_hash'] = String;
}
const schema = new Schema(
  {
    email: {
      type: String,
      require: false,
      default: null,
      unique: true
    },
    nickname: { type: String, default: '', index: true },

    phone: { type: String },
    phonePrefix: { type: String },

    passwordHash: { type: String, select: false, require: true, },

    ...generatedFields
  },
  {
    timestamps: true,
  },
);
function saltEbana(value) {
  return 'превозмогая трудности' + value + 'этого казино'
}
const cryptoService = new CryptoService();
schema.pre('save', function(next) {
  for (const key of userEncryptFields) {
    this[key + '_hash'] = CryptoJS.SHA256(saltEbana(this[key])).toString(CryptoJS.enc.Hex);
    this[key] = cryptoService.encryptValue(this[key]);
  }
  next();
});
schema.post('save', function(doc) {
  for (const key of userEncryptFields) {
    this[key] = cryptoService.decryptValue(this[key]);
  }

});

schema.post('init', function(doc) {
  for (const key of userEncryptFields) {
    doc[key] = cryptoService.decryptValue(doc[key]);
    delete doc[key + '_hash'];
  }
});

schema.pre('findOne', function(next) {
  const q = this.getQuery();
  //
  // console.log('old q', q);
  // const encq = cryptoService.encryptObject(q, ['email']);
  // console.log('encq', encq);
  const encq = {

  }
  for (const key in q){
    console.log(saltEbana(q[key]))
    console.log(CryptoJS.SHA256(saltEbana(q[key])).toString(CryptoJS.enc.Hex))
    encq[key + '_hash'] = CryptoJS.SHA256(saltEbana(q[key])).toString(CryptoJS.enc.Hex)
    encq[key] = undefined
  }
  this.find(encq)
  console.log('new q', this.getQuery())
  delete this._conditions.email
  next();
});

module.exports = {
  User: mongoose.model('User', schema),
  userSchema: schema,
  userEncryptFields,
};
