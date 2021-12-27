const bodyParser = require('body-parser');
const app = require('express')();
app.use(bodyParser.json());
require('./db');
const { AuthService } = require('./services/auth.service');
const authService = new AuthService();
app.post('/auth/login', async (req, res) => {
  try {
    const { user, accessToken } = await authService.loginUser(req.body);
    res.json({ user, accessToken });

  } catch (err) {
    res.status(400).json({ status: false, error: err.message });
  }
});

app.post('/auth/register', async (req, res) => {
  // console.log('reg body', req.body);
  try {

    const { user } = await authService.registerUser(req.body);
    res.json({ user });
  } catch (err) {
    if (err.code === 11000 && 'email' in err.keyPattern) {
      res.status(400).json({ status: false, error: 'Email duplicate' });
    }
  }
});

app.get('/auth/me', async (req, res) => {
  try {

    const { user } = await authService.getUserByToken(req.query.access_token);
    res.json({ user });
  } catch (err) {
    res.status(400).json({ status: false, error: "Error reading token" });
  }
});

app.get('/users', async (req, res) => {
  res.json(await authService.getAllUsers());
});

module.exports = app;
