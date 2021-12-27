const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');
const { omit } = require('lodash');
const { User } = require('../models/user.model');
const { CryptoService } = require('../services/crypto.service');

class AuthService {

  constructor() {
    this.cryptoService = new CryptoService()
  }

  formatPublicUser(model) {
    return omit(model.toObject(), ['passwordHash']);
  }

  async createHashPassword(password, saltRounds = 10) {
    const passwordSalt = await bcrypt.genSalt(saltRounds);
    const passwordHash = await bcrypt.hash(password, passwordSalt);
    return {
      passwordHash,
    };
  }

  async registerUser(registerForm) {
    const { email, password, nickname, phone, prefix } = registerForm;
    const user = await User.create({
      email, nickname, phone, prefix,
      ...(await this.createHashPassword(password)),
    })
      .then(this.formatPublicUser);

    return { user };
  }

  async findUserById() {

  }

  async loginUser(userForm) {
    const { email, password } = userForm;
    const user = await User
      .findOne({ email })
      .select('+passwordHash');

    if (user) {
      if (await bcrypt.compare(password, user.passwordHash)) {
        return { user: this.formatPublicUser(user), accessToken: await this.authUser(user) };
      } else {
        throw new Error('InvalidPassword');
      }
    } else {
      throw new Error('NotFound');
    }
  }

  async authUser(user) {
    const { _id, email, phone } = user;
    return await jwt.sign({ _id, email, phone }, process.env.JWT_SECRET);
  }

  async getUserByToken(accessToken) {
    const tokenData = await jwt.decode(accessToken, process.env.JWT_SECRET);
    // console.log(accessToken, tokenData);
    return { user: await User.findById(tokenData).then(u => u && this.formatPublicUser(u))};
  }

  async findUserBy(findCondition) {
  }

  async getAllUsers() {
    return await User.find().then(u => u.map(this.formatPublicUser));
  }
}

module.exports = { AuthService };
