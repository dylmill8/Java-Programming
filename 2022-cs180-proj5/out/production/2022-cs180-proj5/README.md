# Networking Notes

* Server
    * `ServerMain.java`
        * Main starting point of the Server
    * `Server.java`
        * The server-side of the server-client communication.
        * Passes the strings it receives from the client to the `RequestHandler`
    * `RequestHandler.java`
        * Contains the parsing logic of the "requests" that the clients make.
        * Here, the only thing you have to do is:
            * Take in the User's request
            * Run the appropriate server-side commands corresponding to the request
            * Build a String[] that contains the data that will be sent to the Client
        * I think that it would make things a lot easier if the data we send to the client
          is prefixed with a header that contains the size/number of lines of the data
          that the client is meant to receive. This is open to change.
        * Additionally, perhaps we could include some kind of indicator of success in the header,
          telling the client if their previous request had been successful.
* Client
    * `Client.java`
        * Contains the client-side of the server-client communication
    * `ClientUI.java`
        * Will hold the UserInterface that the client actually uses.
        * You will need to build requests based off the users actions and inputs.
        * To send to the server, you can just call `client.send(request)`
            * This method call returns a `String[]` with the data from the server


* Edge cases to consider
    * What if client disconnects?
        * Right now, server just closes connection, which I think makes sense.
    * What if server disconnects?
        * Right now, client crashes. What should be expected here?
