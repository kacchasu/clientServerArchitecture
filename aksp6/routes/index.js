const express = require('express');
const{ graphqlHTTP } = require('express-graphql');
const schema = require('./schema/schema.js');
const app = express();
const port = 1234;


app.use('/graphql',
    graphqlHTTP({
      schema: schema,
      graphiql: true,
    }))
app.listen(port);
