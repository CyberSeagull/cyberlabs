const mongoose = require('mongoose');
const connectURI = process.env.DB_URI;
mongoose.connect(connectURI, {});
